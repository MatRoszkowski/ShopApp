import React from 'react';
import {withReducer} from "./reducer";
import {Route, Redirect} from 'react-router-dom';

export const PrivateRoute = withReducer(({component: RouteComponent, isAuthorized, ...rest})=>{
    return (
        <Route {...rest} render={routeProps => (
            isAuthorized ?
                (<RouteComponent {...routeProps} />)
                :
                (<Redirect to="/signin" />)
        )
        }/>
    )
});


