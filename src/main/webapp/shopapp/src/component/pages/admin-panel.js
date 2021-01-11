import React from "react";
import {AdminConsole} from "../admin-console/admin-console";
import {ItemLisAdminWrap} from "../item-list/item-list-admin";

const Admin = () => {
    return(
        <div className="row">
            <div className="col-xl-3">
                <AdminConsole />
            </div>
            <div className="col-xl-9">
                <ItemLisAdminWrap />
            </div>
        </div>
    )
};

export {Admin};
