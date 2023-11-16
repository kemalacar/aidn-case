import React from "react";

class InputComponent extends React.Component {
    render() {
        return <div className={"input-field"}>
            <div className={"input-label"}>
                <h3 className={"input-text-body"}>
                    {this.props.input.title}
                </h3>

                <div>
                    <small className={"small-14"} style={{alignSelf: "stretch"}}>
                        {this.props.input.toolTip}
                    </small>
                </div>


            </div>

            <input type={"number"}
                   style={{
                       border: "1px solid rgba(116, 36, 218, 0.05)",
                       background: "#FAF6FF"

                   }}

                   value={this.props.input.value}
                   name={this.props.input.type}
                   className={"input"}
                   onChange={this.props.onChange}
            />
        </div>;
    }
}

export default InputComponent;
