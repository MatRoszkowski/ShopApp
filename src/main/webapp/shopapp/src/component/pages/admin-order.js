import React from "react";
import {AdminConsole} from "../admin-console/admin-console";
import {AdminOrderWrap} from "../admin-order/admin-order";

const AdminOrder = ()=>{
    return(
        <div className="row">
            <div className="col-xl-3">
                <AdminConsole />
            </div>
            <div className="col-xl-9">
                <AdminOrderWrap />
            </div>
        </div>
    )
};

export {AdminOrder};
