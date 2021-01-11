import Axios from "axios";

export class ShopService {

    _apiBase = "http://localhost:8080";

    async signin(user){
        return Axios.post(this._apiBase + "/auth/login", user);
    }

    async getUserData(username){
        return Axios.get(this._apiBase + "/user/" + username);
    }

    async registration(user){
        return Axios.post(this._apiBase + "/auth/register", user);
    }

    async getAllProducts(){
        return Axios.get(this._apiBase + "/product/all");
    }
    async getAllProductsForAdmin(jwt){
        Axios.defaults.headers.common['Content-Type']="application/json";
        Axios.defaults.headers.common['Authorization']="Bearer " + jwt;
        return Axios.get(this._apiBase + "/product/allProd");
    }

    async addToCart(product, jwt){
        Axios.defaults.headers.common['Content-Type']="application/json";
        Axios.defaults.headers.common['Authorization']="Bearer " + jwt;
        const res = await Axios.post(this._apiBase + "/myCart/add", product);
        return res;
    }

    async onDeleteItem(id, jwt){
        Axios.defaults.headers.common['Content-Type']="application/json";
        Axios.defaults.headers.common['Authorization']="Bearer " + jwt;
        const res = await Axios.delete(this._apiBase + "/product/delete/" + id);
        return res;
    }

    async onEditItem(item, jwt){
        Axios.defaults.headers.common['Content-Type']="application/json";
        Axios.defaults.headers.common['Authorization']="Bearer " + jwt;
        const res = await Axios.put(this._apiBase + "/product/edit", item);
        return res;
    }

    async onReatockeItem(item, jwt){
        Axios.defaults.headers.common['Content-Type']="application/json";
        Axios.defaults.headers.common['Authorization']="Bearer " + jwt;
        const res = await Axios.put(this._apiBase + "/product/restock", item);
        return res;
    }

    async onAddNewItem(item, jwt){
        Axios.defaults.headers.common['Content-Type']="application/json";
        Axios.defaults.headers.common['Authorization']="Bearer " + jwt;
        const res = await Axios.post(this._apiBase + "/product/addNew", item);
        return res;
    }

    async getAllUser(jwt){
        Axios.defaults.headers.common['Content-Type']="application/json";
        Axios.defaults.headers.common['Authorization']="Bearer " + jwt;
        const res = await Axios.get(this._apiBase + "/admin/getAllUsers");
        return res;
    }

    async onAddUserRole(user, jwt){
        Axios.defaults.headers.common['Content-Type']="application/json";
        Axios.defaults.headers.common['Authorization']="Bearer " + jwt;
        const res = Axios.put(this._apiBase + "/admin/addRole", user);
        return res;
    }

    async getAllOrdersForAdmin(){
        const res = Axios.get(this._apiBase + "/order/allOrders");
        return res;
    }
}
