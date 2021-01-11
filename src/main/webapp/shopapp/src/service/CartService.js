import Axios from "axios";

const _apiBase = "http://localhost:8080";
export const CartService = {
    getCart: (jwt) => {
        Axios.defaults.headers.common['Content-Type']="application/json";
        Axios.defaults.headers.common['Authorization']="Bearer " + jwt;
        return Axios.get(_apiBase+"/myCart/all");
    },
    modCart: (jwt,prodId,quantity) => {
        Axios.defaults.headers.common['Content-Type']="application/json";
        Axios.defaults.headers.common['Authorization']="Bearer " + jwt;
        if(quantity < 0){
            return Axios.put(_apiBase+"/myCart/remove",{
                productId:prodId,
                quantity:Math.abs(quantity)
            });
        } else {
            return Axios.post(_apiBase+"/myCart/add",{
                productId:prodId,
                quantity:quantity
            });
        }
    },
    placeOrder: (jwt) => {
        Axios.defaults.headers.common['Content-Type']="application/json";
        Axios.defaults.headers.common['Authorization']="Bearer " + jwt;
        return Axios.post(_apiBase+"/order/place");
    },
    getOrders: (jwt) => {
        Axios.defaults.headers.common['Content-Type']="application/json";
        Axios.defaults.headers.common['Authorization']="Bearer " + jwt;
        return Axios.get(_apiBase+"/order/all");

    }
}