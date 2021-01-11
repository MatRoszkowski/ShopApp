import React from "react";
import {Link} from "react-router-dom";
import "./header.scss";
import {withReducer} from "../reducer/reducer";

const Header = ({onLogout}) =>{
   return(
           <nav className="navbar navbar-expand-lg navbar-dark bg-primary">
               <Link to="/" className="navbar-brand">Shop App</Link>
               <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarColor01"
                       aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
                   <span className="navbar-toggler-icon"></span>
               </button>
               <div className="collapse navbar-collapse" id="navbarColor01">
                   <ul className="navbar-nav mr-auto">
                       <li className="nav-item">
                           <Link to="/login" className="nav-link">Signin</Link>
                       </li>
                       <li className="nav-item">
                           <Link to="/admin" className="nav-link">Admin panel</Link>
                       </li>
                       <li className="nav-item">
                           <Link to="/card" className="nav-link">Card</Link>
                       </li>
                   </ul>
                   <button className="btn btn-secondary logout" onClick={()=> onLogout()}>Logout</button>
               </div>
           </nav>
   )
};

export {Header};

export const HeaderWrap = withReducer(({onLogout})=>{
    return <Header onLogout={onLogout}/>
});
