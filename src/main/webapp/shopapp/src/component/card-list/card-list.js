import React, {useEffect, useState} from "react";
import {CardItem} from "../card-item/card-item";
import {withReducer} from "../reducer/reducer";
import {Redirect} from "react-router-dom";
import {CartService} from "../../service/CartService";
import {CardItemOrder} from "../card-item/card-item-order";

const CardList = (props) => {

    const [sumCost,setSumCost] = useState(0);
    const jwt = props.jwt;
    const [cart,_setCart] = useState([]);
    const [orders,setOrders] = useState([]);
    const [siteLoaded, setSiteLoaded] = useState(false);

    const addProd = id => {
        CartService.modCart(jwt,id,1)
            .then(response=>{setCart([...response.data])});
    };

    const subProd = id => {
        CartService.modCart(jwt,id,-1)
            .then(response=>{setCart([...response.data])});
    };

    const setCart = cart => {
        _setCart([...cart]);
        let sum = 0;
        cart.forEach((item)=>{
            sum += item.quantity * item.product.price;
        })
        setSumCost(sum);
    };

    useEffect(()=>{
        if(!siteLoaded){
            CartService.getCart(jwt)
                .then(response=>{setCart([...response.data]);
            CartService.getOrders(jwt)
                .then(response=>{setOrders(response.data);console.log(response.data)});
            setSiteLoaded(true)});
        }
    });

    const placeOrder = ()=>{
        CartService.placeOrder(jwt)
            .then(()=>CartService.getOrders(jwt)
                .then(response=>setOrders(response.data)));
    };

    return(
        <div>
            <table className="table table-hover">
                <thead>
                <tr>
                    <th scope="col">Product</th>
                    <th scope="col">Price</th>
                    <th scope="col">Quantity</th>
                    <th scope="col">Total Cost</th>
                </tr>
                </thead>
                <tbody>
                {cart.map((item,key)=>{
                    return <CardItem DelBtn={subProd} AddBtn={addProd} order={item} key={key}/>
                })}
                </tbody>
            </table>
            <h3 className="text-muted">Total : {sumCost}</h3>
            <button onClick={()=>{placeOrder()}} type="button" className="btn btn-primary">Place Order</button>
            <table className="table table-hover">
                <thead>
                <tr>
                    <th scope="col">Orders</th>
                </tr>
                </thead>
                <tbody>
                {orders.map((item,key)=>{
                    return <div key={item.id}>
                        <tr className="table-primary">
                            <td>Order #{item.id} {item.dateOfOrder[0]}-{item.dateOfOrder[1]}-{item.dateOfOrder[2]} {item.dateOfOrder[3]}:{item.dateOfOrder[4]}</td>
                        </tr><table className="table table-hover">
                        <thead>
                        <tr>
                            <th scope="col">Product</th>
                            <th scope="col">Price</th>
                            <th scope="col">Quantity</th>
                            <th scope="col">Total Cost</th>
                        </tr>
                        </thead>
                        <tbody>
                            {item.items.map((o,key)=>{
                                return <CardItemOrder order={o}></CardItemOrder>
                            })}
                        </tbody>
                    </table>
                    </div>
                })}
                </tbody>
            </table>
        </div>
    )
}

export {CardList};

export const CardListWrap = withReducer(({isAuthorized,jwt})=>{
    return !isAuthorized ? <Redirect to="/"/> : <CardList jwt={jwt} />
});
