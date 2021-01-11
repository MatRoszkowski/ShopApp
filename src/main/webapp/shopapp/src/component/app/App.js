import React from "react";
import "./app.scss";
import {HeaderWrap} from "../header/header";
import {Route, Switch, Redirect} from "react-router-dom";
import {ItemListWrap} from "../item-list/item-list";
import {ReducerProvider} from "../reducer/reducer";
import {LoginPage} from "../pages/login-page";
import {CardList,CardListWrap} from "../card-list/card-list";
import {Admin} from "../pages/admin-panel";
import {AdminProduct} from "../pages/admin-product";
import {AdminUser} from "../pages/admin-user";
import {AdminOrder} from "../pages/admin-order";


export default class App extends React.Component {
  render(){
    return (
        <ReducerProvider>
            <div className="container">
                <HeaderWrap />
                <Switch>
                    <Route path="/" component={ItemListWrap} exact/>
                    <Route path="/login" component={LoginPage} exact/>
                    <Route path="/card" component={CardListWrap} exact />
                    <Route path="/admin" component={Admin} exact />
                    <Route path="/admin-product" component={AdminProduct} exact />
                    <Route path="/admin-user" component={AdminUser} exact />
                    <Route path="/admin-order" component={AdminOrder} exact />
                </Switch>
            </div>
        </ReducerProvider>

    )
  }
}
