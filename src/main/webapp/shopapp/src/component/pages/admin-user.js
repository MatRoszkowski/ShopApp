import React from "react";
import {AdminConsole} from "../admin-console/admin-console";
import {UserListWrap} from "../user-list/user-list";

const AdminUser = ()=>{
    return(
        <div className="row">
            <div className="col-xl-3">
                <AdminConsole />
            </div>
            <div className="col-xl-9">
                <UserListWrap />
            </div>
        </div>
    )
};

export {AdminUser};

