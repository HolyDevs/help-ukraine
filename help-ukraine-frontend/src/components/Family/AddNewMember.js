import React, {useEffect, useState} from 'react';
import InputTextRow from "../Common/InputTextRow";
import ValidationService from "../../services/ValidationService";
import LabelService from "../../services/LabelService";

const AddNewMember = ({member, membersList, onSave}) => {
    const [sex, setSex] = useState(LabelService.getLabelFromKey("FEMALE"));
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
        fillMemberWithData(member);
        resetForm();
        onSave(membersList);
    }

    const fillMemberWithData = (memberToFill) => {
        memberToFill.name = name;
        memberToFill.surname = surname;
        memberToFill.birthDate = birthDate;
        memberToFill.movingIssues = movingIssues
        memberToFill.sex = LabelService.getKeyFromLabel(sex);
    }

    const createNewMember = () => {
        const newMember = {};
        fillMemberWithData(newMember);
        return newMember;
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
        setSex(LabelService.getLabelFromKey("FEMALE"));
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
            setSex(LabelService.getLabelFromKey(member.sex));
            setBirthDate(member.birthDate);
            setMovingIssues(member.movingIssues);
        } else {
            setName("");
            setSurname("");
            setSex(LabelService.getLabelFromKey("FEMALE"));
            setBirthDate("")
            setMovingIssues(false);
        }

    }, [member])

    return (
        <div className="addNewMember">
            <div className="memberForm">
                <InputTextRow inputName="Name" inputType="text" value={name}
                              onChange={(event) => setName(event.target.value)}/>
                <InputTextRow inputName="Surname" inputType="text" value={surname}
                              onChange={(event) => setSurname(event.target.value)}/>
                <InputTextRow inputName="Date" inputType="date" value={birthDate}
                              onChange={(event) => setBirthDate(event.target.value)}/>
                <div className="sexChoice">
                    <div className="sexChoiceTitle">Sex</div>
                    <div className="spacer"/>
                    <select className="sexSelect" value={sex} onChange={(event) => setSex(event.target.value)}>
                        <option
                            value={LabelService.getLabelFromKey("FEMALE")}>
                            {LabelService.getLabelFromKey("FEMALE")}
                        </option>
                        <option
                            value={LabelService.getLabelFromKey("MALE")}>
                            {LabelService.getLabelFromKey("MALE")}
                        </option>
                    </select>
                </div>
                <InputTextRow inputName="Moving issues" inputType="checkbox" checked={movingIssues}
                              onChange={(event) => setMovingIssues(event.target.checked)}/>
            </div>
            <div className="spacer"/>
            <div className="saveMemberBox">
                <div className="spacer"/>
                <div className="saveMember" onClick={onSaveButton}> Save</div>
                <div className="spacer"/>
            </div>

        </div>
    );
}

export default AddNewMember;