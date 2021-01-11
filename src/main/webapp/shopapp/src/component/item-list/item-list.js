import React from "react";
import {Item} from "../item/item";
import {ShopService} from "../../service/shopServise";
import {withReducer} from "../reducer/reducer";

export default class ItemList extends React.Component {

    state = {
        itemList: null
    };
    shopService = new ShopService();

    componentDidMount() {
        this.updateList();
    }

    updateList = ()=>{
      this.shopService.getAllProducts()
          .then((res)=>{
             this.setState({
                 itemList: res.data
             })
          });
    };

    renderItem = (itemList)=>{
        const {jwt, addToCart} = this.props;
        return itemList.map((item)=>{
            return (
                <div key={item.id} className="col-xl-4">
                    <Item itemData = {item} jwt={jwt} addToCart={addToCart}/>
                </div>
            )
        });
    };

    render(){
        const {itemList} = this.state;
        if(!itemList) return <h1>Loading...</h1>;

        const items = this.renderItem(itemList);

        return(
            <div className="row">
                <div className="col-xl-12">
                    <div className="row justify-content-center">
                        <div className="col-xl-10">
                            <div className="form-group">
                                <input type="text" className="form-control" placeholder="Search" id="inputDefault" />
                            </div>
                        </div>
                        <div className="col-xl-2">
                            <button className="btn btn-primary">Search</button>
                        </div>
                    </div>
                </div>
                {items}
            </div>
        )
    }
}

export const ItemListWrap = withReducer(({jwt, addToCart})=> {
    return <ItemList jwt={jwt} addToCart={addToCart}/>;
});
