import React from "react";

class ItemDetails extends React.Component{
    render(){
        return(
            <div className="row">
                <div className="col-xl-6">
                    <img src="data:image/svg+xml;charset=UTF-8,%3Csvg%20width%3D%22318%22%20height%3D%22180%22%20xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%22%20viewBox%3D%220%200%20318%20180%22%20preserveAspectRatio%3D%22none%22%3E%3Cdefs%3E%3Cstyle%20type%3D%22text%2Fcss%22%3E%23holder_158bd1d28ef%20text%20%7B%20fill%3Argba(255%2C255%2C255%2C.75)%3Bfont-weight%3Anormal%3Bfont-family%3AHelvetica%2C%20monospace%3Bfont-size%3A16pt%20%7D%20%3C%2Fstyle%3E%3C%2Fdefs%3E%3Cg%20id%3D%22holder_158bd1d28ef%22%3E%3Crect%20width%3D%22318%22%20height%3D%22180%22%20fill%3D%22%23777%22%3E%3C%2Frect%3E%3Cg%3E%3Ctext%20x%3D%22129.359375%22%20y%3D%2297.35%22%3EImage%3C%2Ftext%3E%3C%2Fg%3E%3C%2Fg%3E%3C%2Fsvg%3E"
                         alt="Card image" />
                </div>
                <div className="col-xl-6">
                    <div className="jumbotron">
                        <h1 className="display-3">Hello, world!</h1>
                        <p className="lead">This is a simple hero unit, a simple jumbotron-style component for calling
                            extra attention to featured content or information.</p>
                        <hr className="my-4" />
                            <p>It uses utility classes for typography and spacing to space content out within the larger
                                container.</p>
                            <p className="lead">
                                <a className="btn btn-primary btn-lg" href="#" role="button">Learn more</a>
                            </p>
                    </div>
                </div>
            </div>
        )
    }
}

export {ItemDetails};
