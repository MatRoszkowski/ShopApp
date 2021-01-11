import React from "react";
import {withReducer} from "../reducer/reducer";
import {Redirect} from "react-router-dom";
import {ShopService} from "../../service/shopServise";

class AddItemForm extends React.Component{
    state = {
        name: null,
        price: null,
        stock: null,
        description: null
    };

    shopService = new ShopService();

    onName = (event)=>{
        this.setState({
            name: event.target.value
        })
    };
    onPrice = (event)=>{
        this.setState({
            price: event.target.value
        })
    };
    onStock = (event)=>{
        this.setState({
            stock: event.target.value
        })
    };
    onDescription = (event)=>{
        this.setState({
            description: event.target.value
        })
    };

    onAddItem= ()=>{
      const {jwt} = this.props;
      const {name, price, stock, description} = this.state;
      const item = {name, price, stock, description};
      this.shopService.onAddNewItem(item, jwt)
          .then((res)=>{
              console.log(res);
          })
    };

    render() {
        return(
            <div className="jumbotron">
                <div className="form-group">
                    <h3>Add product</h3>
                    <input type="text" className="form-control signin-form" placeholder="Name" onChange={(event)=>this.onName(event)}/>
                    <input type="text" className="form-control signin-form" placeholder="Price" onChange={(event)=>this.onPrice(event)}/>
                    <input type="text" className="form-control signin-form" placeholder="Stock count" onChange={(event)=>this.onStock(event)}/>
                    <input type="text" className="form-control signin-form" placeholder="Description" onChange={(event)=>this.onDescription(event)}/>
                </div>
                <button className="btn btn-primary" onClick={()=> this.onAddItem()}>Add</button>
            </div>
        )
    }
}

export {AddItemForm};

export const AddNewItemWrap = withReducer(({isAuthorized, jwt})=> {
    return isAuthorized ? <AddItemForm jwt={jwt} /> : <Redirect to="/" />
});
