import React from "react";
import Cookies from "js-cookie";
import {ShopService} from "../../service/shopServise";

const {Provider, Consumer} = React.createContext({isAuthorized: false});

class ReducerProvider extends React.Component{
    state = {
        isAuthorized: false,
        jwt: null
    };

    shopService = new ShopService();

    componentDidMount() {
        if(Cookies.get("user_shop"))
            this.onAuth(Cookies.get("user_shop"));
    }

    onAuth = (jwt) =>{
        this.setState({
            isAuthorized: true,
            jwt
        });
        Cookies.set("user_shop", jwt);
    };

    onLogout=()=>{
        Cookies.remove("user_shop");
        this.setState({
            isAuthorized: false
        })
    };

    addToCart = (id)=>{
        const {jwt} = this.state;
        const product = {productId:id, quantity:1};
        this.shopService.addToCart(product, jwt)
            .then((res)=>{
               console.log(res);
            });
    };

    render(){
        const {isAuthorized, jwt} = this.state;

        return(
            <Provider value={{isAuthorized, jwt, onAuth: this.onAuth, onLogout: this.onLogout, addToCart: this.addToCart}}>
                {this.props.children}
            </Provider>
        )
    }
}

export {ReducerProvider};

export function withReducer(WrappedComponent){
    return class AuthHOC extends React.Component {
        render(){
            return(
                <Consumer>
                    {(contextProps)=> ( <WrappedComponent {...contextProps} {...this.props} />)}
                </Consumer>
            )
        }
    }
}
