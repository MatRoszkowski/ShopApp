import React from "react";
import {AdminConsole} from "../admin-console/admin-console";
import {AddNewItemWrap} from "../add-item-form/add-item-form";

const AdminProduct = ()=>{
    return(
        <div className="row">
            <div className="col-xl-3">
                <AdminConsole />
            </div>
            <div className="col-xl-9">
                <AddNewItemWrap />
            </div>
        </div>
    )
};

export {AdminProduct};
