import React from "react";
import {withReducer} from "../reducer/reducer";
import {Redirect} from "react-router-dom";
import {ShopService} from "../../service/shopServise";

class AdminOrder extends React.Component{

    state={
      orderList: null
    };
    shopService = new ShopService();

    componentDidMount() {
        this.updateList();
    }

    updateList(){
        this.shopService.getAllOrdersForAdmin()
            .then((res)=>{
                console.log(res.data);
                this.setState({
                    orderList: res.data
                })
            })
    }

    renderItem = (itemList)=>{
        return itemList.map((item)=>{
            console.log(item);
            return(
                <tr key={item.id} className="table-active">
                    <th scope="row">{item.items[item.id-1].product.name} x {item.items[item.id-1].quantity}</th>
                    <td>{item.dateOfOrder[3]}:{item.dateOfOrder[4]}:{item.dateOfOrder[5]} | {item.dateOfOrder[2]}:{item.dateOfOrder[1]}:{item.dateOfOrder[0]}</td>
                </tr>
            )
        })
    };

    render(){

        const {orderList} = this.state;
        if(!orderList) return <h1>Loading...</h1>;

        const items = this.renderItem(orderList);

        return(
            <table className="table table-hover">
                <thead>
                <tr>
                    <th scope="col">Items</th>
                    <th scope="col">Data</th>
                </tr>
                </thead>
                <tbody>
                {items}
                </tbody>
            </table>
        )
    }
}

export const AdminOrderWrap = withReducer(({isAuthorized, jwt})=>{
    return isAuthorized ? <AdminOrder jwt={jwt} /> : <Redirect to="/" />;
});
