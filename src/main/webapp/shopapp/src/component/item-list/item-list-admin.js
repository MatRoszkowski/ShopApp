import React from "react";
import "./item-list.scss";
import {ShopService} from "../../service/shopServise";
import {withReducer} from "../reducer/reducer";
import {Redirect} from "react-router-dom";

class ItemListAdmin extends React.Component{

    state = {
        itemList: null,
        editValue: null
    };

    shopService = new ShopService();

    componentDidMount() {
        this.updateList();
    }

    updateList = ()=>{
        const {jwt} = this.props;
        this.shopService.getAllProductsForAdmin(jwt)
            .then((res)=>{
                this.setState({
                    itemList: res.data
                })
            });
    };

    onDelete = (id)=>{
      const {jwt} =this.props;
        this.shopService.onDeleteItem(id, jwt)
            .then((res)=>{
               console.log(res);
               this.updateList();
            });
    };

    onEdit = (item, num=1)=>{
        item.stock = item.stock + parseInt(num);
        const {jwt} = this.props;
        console.log(item);
        this.shopService.onEditItem(item, jwt)
            .then((res)=>{
                console.log(res);
                this.updateList();
            });

    };

    onRestock = (id)=>{
        const {jwt} = this.props;
        const restock = {productId: id, quantity: 1};
        this.shopService.onReatockeItem(restock, jwt)
            .then((res)=>{
                console.log(res);
                this.updateList();
            });
    };

    onEditValue = (event)=>{
        this.setState({
            editValue: event.target.value
        })
    };

    renderItem = (itemList)=>{
       return itemList.map((item)=>{
            return(
                <tr key={item.id} className="table-active">
                    <th scope="row">{item.name}</th>
                    <td>{item.price}</td>
                    <td>{item.stock}</td>
                    <td>
                        <button className="btn btn-primary btn-admin" onClick={()=>this.onDelete(item.id)}>&times;</button>
                        <input type="number" className="form-control input-admin" onChange={(event)=> this.onEditValue(event)}/>
                        <button className="btn btn-primary btn-admin" onClick={()=>this.onEdit(item, this.state.editValue)}>edit</button>
                        <button className="btn btn-primary btn-admin" onClick={()=>this.onRestock(item.id)}>+1</button>
                    </td>
                </tr>
            )
        })
    };

    render() {
        const {itemList} = this.state;
        if(!itemList) return <h1>Loading...</h1>;

        const items = this.renderItem(itemList);

        return(
            <table className="table table-hover">
                <thead>
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Price</th>
                    <th scope="col">Stock</th>
                    <th scope="col">admin tools</th>
                </tr>
                </thead>
                <tbody>
                    {items}
                </tbody>
            </table>
        )
    }
}

export const ItemLisAdminWrap = withReducer(({ isAuthorized ,jwt})=> {
    return isAuthorized ? <ItemListAdmin jwt={jwt} /> : <Redirect to="/" />
});


