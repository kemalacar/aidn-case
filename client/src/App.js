import './App.css';
import './components/input-component'
import InputComponent from "./components/input-component";
import { useState} from "react";
import ScoreArea from "./components/score-area";
import {BackendApi} from "./service/Api";
import Swal from 'sweetalert2';

function App() {

    let inputs = [
        {title: "Body temperature", toolTip: "Degrees celcius", type: 'TEMP', value: ''},
        {title: "Heartrate", toolTip: "Beats per minute", type: 'HR', value: ''},
        {title: "Respiratory rate", toolTip: "Beats per minute", type: 'RR', value: ''},
    ]

    const [measurements, setMeasurements] = useState(inputs);

    const [score, setScore] = useState(0);
    const [showScore, setShowScore] = useState(false);

    const save = () => {

        // console.log(formData)
        const req = { measurements}

        BackendApi.calculate(req).then(value => {
            setScore(value.score);
            setShowScore(true);
        }).catch(reason => {
            console.log(reason)
            const msg = reason.response.data;
            Swal.fire({
                title: "Exception",
                html: msg?.detail,

            });
        })

    };

    const reset = (e) => {
        setMeasurements(inputs)
        setScore(0);
        setShowScore(false);
    };

    const handleInputChange = index => e =>  {
        let newArr = [...measurements];
        newArr[index].value = e.target.value;
        setMeasurements(newArr);
    };

    return (
        <div className="App">
            <div className="body-class">
                <h2 className={"label"}>
                    NEWS score calculator
                </h2>

                {measurements.map((value, index, array) => {
                        return <InputComponent key={value.name} input={value} onChange={handleInputChange(index)}/>
                    }
                )}

                <div className={"button-area"}>
                    <button
                        onClick={save}
                        className={"button-layout"}
                        style={{
                            borderRadius: "40px",
                            background: "#7424DA",
                        }}>
                        <label style={{color: "#FFF"}} className={"button-text"}>Calculate NEWS score</label>
                    </button>

                    <button className={"button-layout"}
                            onClick={reset}
                            style={{
                                borderRadius: "40px",
                                background: "#FAF6FF"
                            }}>
                        <label className={"button-text"}>Reset form</label>
                    </button>
                </div>

                {showScore && < ScoreArea score={score}></ScoreArea>}
            </div>
        </div>
    );
}


export default App;
