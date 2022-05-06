import { RegisterSection } from "../../components/styled-components/Sections";
import { InputFormFilled, Dropdown } from "../../components/widgets/Inputs";
import React, {useEffect, useState} from 'react';
import Select from 'react-select'
import InputTextRow from "../Common/InputTextRow";

const AddNewMember = ({member}) => {

    const [state, setState] = useState({});
    const [gender, setGender] = useState("");
    const [name, setName] = useState("");
    const [surname, setSurname] = useState("");
    const [birthDate, setBirthDate] = useState("");
    const [movingIssues, setMovingIssues] = useState(false);

    useEffect(() => {
        if (member) {
            setName(member.name);
            setSurname(member.surname);
            setGender(member.gender);
            setBirthDate(member.birthDate);
            setMovingIssues(member.movingIssues);
        }
        else {
            setName("");
            setSurname("");
            setGender("");
            setBirthDate("dd.MM.yyyy")
            setMovingIssues(false);
        }
        
    }, [member])

    useEffect(() => {
        console.log("new moving issues: " + movingIssues);
    }, [movingIssues])

    return (
        <div className="addNewMember">
            <div className="memberForm">
                <InputTextRow inputName="Name" inputType="text" value={name} onChange={(event) => setName(event.target.value)}/>
                <InputTextRow inputName="Surname" inputType="text" value={surname} onChange={(event) => setSurname(event.target.value)}/>
                <InputTextRow inputName="Date" inputType="date" value={birthDate} onChange={(event) => setBirthDate(event.target.value)}/>
                <div className="sexChoice">
                    <div className="sexChoiceTitle">Sex</div>
                    <div className="spacer"/>
                    <select className="sexSelect" value={gender} onChange={(event) => setGender(event.target.value)}>
                        <option value="Female">Female</option>
                        <option value="Male">Male</option>
                </select>
                </div>
                <InputTextRow inputName="Moving issues" inputType="checkbox" checked={movingIssues} onChange={(event) => setMovingIssues(event.target.checked)}/>
            </div>
            <div className="spacer"/>
            <div className="saveMemberBox">
                <div className="spacer"/>
                <div className="saveMember"> Save </div>
                <div className="spacer"/>
            </div>
            
        </div>
    );
}

export default AddNewMember;