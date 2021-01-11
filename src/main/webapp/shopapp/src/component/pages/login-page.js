import React from "react";
import {SigninWrap} from "../signin/signin";
import {Signup} from "../signup/signup";

const LoginPage = ()=>{
    return(
        <div>
            <div className="row">
                <div className="col-xl-6">
                    <SigninWrap />
                </div>
                <div className="col-xl-6">
                    <Signup />
                </div>
            </div>
        </div>
    )
};

export {LoginPage};
