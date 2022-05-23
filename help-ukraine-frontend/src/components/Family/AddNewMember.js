import React, {useEffect, useState} from 'react';
import InputTextRow from "../Common/InputTextRow";
import ValidationService from "../../services/ValidationService";

const AddNewMember = ({member, membersList, onSave}) => {
    const [gender, setGender] = useState("Female");
    const [name, setName] = useState("");
    const [surname, setSurname] = useState("");
    const [birthDate, setBirthDate] = useState("");
    const [movingIssues, setMovingIssues] = useState(false);


    const onSaveButton = () => {
        if (member) {
            modifyMember();
            return;
        }
        addNewMember();
    }

    const modifyMember = () => {
        if (!validateInputs()) {
            return;
        }
        fillMemberWithData();
        resetForm();
        onSave(membersList);
    }

    const fillMemberWithData = () => {
        member.name = name;
        member.surname = surname;
        member.birthDate = birthDate;
        member.movingIssues = movingIssues
    }

    const createNewMember = () => {
        return {
            name: name,
            surname: surname,
            gender: gender,
            birthDate: birthDate,
            movingIssues: movingIssues
        }
    }

    const addNewMember = () => {
        if (!validateInputs()) {
            return;
        }
        const newMember = createNewMember();
        membersList.push(newMember);
        resetForm();
        onSave(membersList);
    }

    const resetForm = () => {
        setName("");
        setSurname("");
        setGender("Female");
        setBirthDate("");
        setMovingIssues(false);
    }

    // temporary alert-based error handling
    // todo: create proper error info
    const validateInputs = () => {
        const stringForms = [name, surname]
        if (!ValidationService.areStringsValid(stringForms)) {
            window.alert("Text input cannot be empty");
            return false;
        }
        if (!ValidationService.isBirthDateValid(birthDate)) {
            window.alert("Chosen date is invalid");
            return false;
        }
        return true;
    }

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
            setBirthDate("")
            setMovingIssues(false);
        }
        
    }, [member])

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
                <div className="saveMember" onClick={onSaveButton}> Save </div>
                <div className="spacer"/>
            </div>
            
        </div>
    );
}

export default AddNewMember;