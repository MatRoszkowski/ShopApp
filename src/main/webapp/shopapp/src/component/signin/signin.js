import React from "react";
import "./signin.scss";
import {withReducer} from "../reducer/reducer";
import {Redirect} from "react-router-dom";
import {ShopService} from "../../service/shopServise";

class Signin extends React.Component {

    shopService = new ShopService();

    state = {
        username: null,
        password: null
    };

    onUsername = (event)=>{
      this.setState({
          username: event.target.value
      })
    };

    onPassword = (event)=>{
      this.setState({
        password: event.target.value
      })
    };

    onSignin = ()=>{
        const {onAuth} = this.props;
        const {username, password} = this.state;
        const user = {username, password};
        this.shopService.signin(user)
            .then((res)=>{
                onAuth(res.data);
            })
    };


    render(){
        return(
            <div className="jumbotron">
                <div className="form-group">
                    <h3>Signin</h3>
                    <input type="text" className="form-control signin-form" placeholder="Username" onChange={(event)=>this.onUsername(event)}/>
                    <input type="password" className="form-control signin-form" placeholder="Password" onChange={(event)=>this.onPassword(event)}/>
                </div>
                <button className="btn btn-primary" onClick={()=> this.onSignin()}>SignIn</button>
            </div>
        )
    }
}

export {Signin};

export const SigninWrap = withReducer(({isAuthorized, onAuth})=>{
    return isAuthorized ? <Redirect to="/"/> : <Signin onAuth={onAuth}/>
});
