import React from "react";

class CardItem extends React.Component{
    order = this.props.order
    render(){
        return(
            <tr className="table-active">
                <th scope="row">{this.order.product.name}</th>
                <td>{this.order.product.price}</td>
                <td>{this.order.quantity}
                    <button onClick={()=>this.props.AddBtn(this.order.product.id)} type="button" className="btn btn-success">+</button>
                    <button onClick={()=>this.props.DelBtn(this.order.product.id)} type="button" className="btn btn-danger">-</button></td>
                <td>{this.order.product.price * this.order.quantity}</td>
            </tr>
        )
    }
}

export {CardItem};
