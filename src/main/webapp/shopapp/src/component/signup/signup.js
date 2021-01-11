import React from "react";
import "./signup.scss";
import {ShopService} from "../../service/shopServise";

class Signup extends React.Component {
    state={
        username: null,
        email: null,
        firsName: null,
        lastName: null,
        password: null,
        phoneNumber: null,
        regStatus: false
    };

    shopService = new ShopService();

    onUsername = (event)=>{
        this.setState({
            username: event.target.value
        })
    };
    onEmail = (event)=>{
        this.setState({
            email: event.target.value
        })
    };
    onFirstname = (event)=>{
        this.setState({
            firstName: event.target.value
        })
    };
    onLastname = (event)=>{
        this.setState({
            lastName: event.target.value
        })
    };
    onPassword = (event)=>{
        this.setState({
            password: event.target.value
        })
    };
    onPhone = (event)=>{
        this.setState({
            phoneNumber: event.target.value
        })
    };

    onSignup = ()=>{
        const {username, email, firstName, lastName, password, phoneNumber} = this.state;
        const newUser = {username, email, firstName, lastName, password, phoneNumber};
        this.shopService.registration(newUser)
            .then((res)=>{
                console.log(res);
                if(res.status == "200"){
                    this.setState({
                        regStatus: true
                    })
                }
            });
    };

    render(){

        if(this.state.regStatus){
            return(
                <div className="jumbotron">
                    <div className="form-group">
                        <h3>Registration success</h3>
                    </div>
                </div>
            )
        }

        return(
            <div className="jumbotron">
                <div className="form-group">
                    <h3>Signup</h3>
                    <input type="text" className="form-control signin-form" placeholder="Username" onChange={(event)=>this.onUsername(event)}/>
                    <input type="email" className="form-control signin-form" placeholder="Email" onChange={(event)=>this.onEmail(event)}/>
                    <input type="text" className="form-control signin-form" placeholder="First name" onChange={(event)=>this.onFirstname(event)}/>
                    <input type="text" className="form-control signin-form" placeholder="Last name" onChange={(event)=>this.onLastname(event)}/>
                    <input type="text" className="form-control signin-form" placeholder="Phone" onChange={(event)=>this.onPhone(event)}/>
                    <input type="password" className="form-control signin-form" placeholder="Password" onChange={(event)=>this.onPassword(event)}/>
                </div>
                <button className="btn btn-primary" onClick={()=> this.onSignup()}>SignIn</button>
            </div>
        )
    }
}

export {Signup};
