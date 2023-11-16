import React from "react";

class ScoreArea extends React.Component {
    render() {
        return <div className={"score-area"} style={styles.scoreArea}>
            <div className={"content"}>
                <div className={"text"}>New Score: <span style={{fontWeight: 600}}> {this.props.score}</span></div>
            </div>
        </div>
    }
}


const styles = {
    scoreArea: {
        borderRadius: "10px",
        border: "1px solid rgba(116, 36, 218, 0.40)",
        background: "#FAF6FF"
    }
}

export default ScoreArea;
