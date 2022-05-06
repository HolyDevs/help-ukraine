import React, {useState} from 'react';
import {InputFormFilled} from "../../../components/widgets/Inputs";
import AppButton from "../../../components/styled-components/AppButton";
import {PustePoleJakLebBrzezinskiego20px, RegisterSection} from "../../../components/styled-components/Sections";
import {RegisterHeader} from "../../../components/styled-components/Headers";
import {RegisterBody} from "../../../components/styled-components/Screens";
import ValidationService from "../../../services/ValidationService";
import {useNavigate} from "react-router-dom";

const RegisterRefugeeBasicInfoForm = () => {

    let navigate = useNavigate();
    const [state, setState] = useState({});

    const handleStateChanged = (event, key) => {
        state[key] = event.target.value;
        setState(state);
    }


    // temporary alert-based error handling
    // todo: create proper error info
    const handleSubmitButton = () => {
        if (!ValidationService.areStringsValid([state["name"], state["surname"]])) {
            window.alert("Text input cannot be empty");
            return;
        }
        const userToBeRegistered = JSON.parse(localStorage.getItem('userToBeRegistered'))
        userToBeRegistered.name = state["name"]
        userToBeRegistered.surname = state["surname"]
        localStorage.setItem('userToBeRegistered', JSON.stringify(userToBeRegistered))
        navigate("/registerRefugee/account-further-info");
    }

    return (
        <RegisterBody>
            <RegisterHeader>Tell us about your situation.</RegisterHeader>
            <RegisterSection>
                <InputFormFilled value={state["name"]} onChange={(e) => {
                    handleStateChanged(e, "name");
                }} inputLabel="Name:" type="text"/>
            </RegisterSection>
            <RegisterSection>
                <InputFormFilled value={state["surname"]} onChange={(e) => {
                    handleStateChanged(e, "surname");
                }} inputLabel="Surname:" type="text"/>
            </RegisterSection>
            <RegisterSection>
                <InputFormFilled value={state["adults"]} onChange={(e) => {
                    handleStateChanged(e, "adults");
                }} inputLabel="Adults:" type="number"/>
            </RegisterSection>
            <RegisterSection>
                <InputFormFilled value={state["children"]} onChange={(e) => {
                    handleStateChanged(e, "children");
                }} inputLabel="Children:" type="text"/>
            </RegisterSection>
            <RegisterSection>
                <PustePoleJakLebBrzezinskiego20px>
                </PustePoleJakLebBrzezinskiego20px>
                <AppButton onClick={handleSubmitButton}>Submit</AppButton>
            </RegisterSection>
        </RegisterBody>
    )
}

export default RegisterRefugeeBasicInfoForm;