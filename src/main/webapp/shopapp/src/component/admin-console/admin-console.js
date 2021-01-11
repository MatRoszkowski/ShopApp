import React from "react";
import "./admin-console.scss";
import {Link} from "react-router-dom";

class AdminConsole extends React.Component{
    render() {
        return(
            <div className="jumbotron admin-console">
                <Link to="admin" className="btn btn-primary">Product list</Link>
                <Link to="admin-user" className="btn btn-primary">User list</Link>
                <Link to="admin-order" className="btn btn-primary">Order list</Link>
                <Link to="admin-product" className="btn btn-primary">Add new product</Link>
            </div>
        )
    }
}

export {AdminConsole};
