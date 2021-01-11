import React from "react";
import {withReducer} from "../reducer/reducer";
import {Redirect} from "react-router-dom";
import {ShopService} from "../../service/shopServise";

class UserList extends React.Component {

    state = {
        itemList: null
    };

    shopService = new ShopService();

    componentDidMount() {
        this.updateList();
    }


    updateList = ()=>{
        const {jwt} = this.props;
        this.shopService.getAllUser(jwt)
            .then((res)=>{
                console.log(res);
                this.setState({
                    itemList: res.data
                })
            });
    };

    onStorekeeper = (id) =>{
        const {jwt} = this.props;
        const user = {roleName: "ROLE_STOREKEEPER", userId: id};
        this.shopService.onAddUserRole(user, jwt)
            .then((res)=>{
                console.log(res);
            })
    };

    onAdmin = (id) =>{
        const {jwt} = this.props;
        const user = {roleName: "ROLE_ADMIN", userId: id};
        this.shopService.onAddUserRole(user, jwt)
            .then((res)=>{
                console.log(res);
            })
    };

    renderItem = (itemList)=>{
        return itemList.map((item)=>{
            return(
                <tr key={item.id} className="table-active">
                    <th scope="row">{item.username}</th>
                    <td>{item.email}</td>
                    <td>{item.firstName}</td>
                    <td>{item.lastName}</td>
                    <td>{item.phoneNumber}</td>
                    <td>
                        <button className="btn btn-primary btn-admin" onClick={()=>this.onStorekeeper(item.id)}>S</button>
                        <button className="btn btn-primary btn-admin" onClick={()=>this.onAdmin(item.id)}>A</button>
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
                    <th scope="col">Username</th>
                    <th scope="col">Email</th>
                    <th scope="col">Firstname</th>
                    <th scope="col">Lastname</th>
                    <th scope="col">Phone</th>
                </tr>
                </thead>
                <tbody>
                {items}
                </tbody>
            </table>
        )
    }
}

export const UserListWrap = withReducer(({ isAuthorized ,jwt})=> {
    return isAuthorized ? <UserList jwt={jwt} /> : <Redirect to="/" />
});

