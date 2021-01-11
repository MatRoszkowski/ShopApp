import React from "react";

class CardItemOrder extends React.Component{
    order = this.props.order
    render(){
        return(
            <tr className="table-active">
                <th scope="row">{this.order.product.name}</th>
                <td>{this.order.product.price}</td>
                <td>{this.order.quantity}</td>
                <td>{this.order.product.price * this.order.quantity}</td>
            </tr>
        )
    }
}

export {CardItemOrder};
