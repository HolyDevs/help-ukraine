import React, {useState} from 'react';
import {InputFormFilled} from "../../../components/widgets/Inputs";
import AppButton from "../../../components/styled-components/AppButton";
import {PustePole20px, RegisterSection} from "../../../components/styled-components/Sections";
import {RegisterHeader} from "../../../components/styled-components/Headers";
import {RegisterBody} from "../../../components/styled-components/Screens";
import ValidationService from "../../../services/ValidationService";
import {useNavigate} from "react-router-dom";

const RegisterRefugeeBasicInfoForm = () => {

    let navigate = useNavigate();
    const [name, setName] = useState("");
    const [surname, setSurname] = useState("");
    const [adults, setAdults] = useState(0);
    const [children, setChildren] = useState(0);

    // temporary alert-based error handling
    // todo: create proper error info
    const validateInputs = () => {
        if (!ValidationService.areStringsValid([name, surname])) {
            window.alert("Text input cannot be empty");
            return false;
        }
        return true;
    }

    const handleSubmitButton = () => {
        if (!validateInputs()) {
            return;
        }
        const userToBeRegistered = JSON.parse(sessionStorage.getItem('userToBeRegistered'))
        userToBeRegistered.name = name;
        userToBeRegistered.surname = surname;
        sessionStorage.setItem('userToBeRegistered', JSON.stringify(userToBeRegistered))
        navigate("/registerRefugee/account-further-info");
    }

    return (
        <RegisterBody>
            <RegisterHeader>Tell us about your situation.</RegisterHeader>
            <RegisterSection>
                <InputFormFilled value={name} onChange={(e) => {
                    setName(e.target.value);
                }} inputLabel="Name:" type="text"/>
            </RegisterSection>
            <RegisterSection>
                <InputFormFilled value={surname} onChange={(e) => {
                    setSurname(e.target.value);
                }} inputLabel="Surname:" type="text"/>
            </RegisterSection>
            <RegisterSection>
                <InputFormFilled value={adults} onChange={(e) => {
                    setAdults(e.target.value);
                }} inputLabel="Adults:" type="number"/>
            </RegisterSection>
            <RegisterSection>
                <InputFormFilled value={children} onChange={(e) => {
                    setChildren(e.target.value);
                }} inputLabel="Children:" type="number"/>
            </RegisterSection>
            <RegisterSection>
                <PustePole20px>
                </PustePole20px>
                <AppButton onClick={handleSubmitButton}>Submit</AppButton>
            </RegisterSection>
        </RegisterBody>
    )
}

export default RegisterRefugeeBasicInfoForm;