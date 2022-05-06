import React, {useState} from 'react';
import {InputFormFilled} from "../../../components/widgets/Inputs";
import {useNavigate} from "react-router-dom";
import AppButton from "../../../components/styled-components/AppButton";
import {PustePole20px, RegisterSection} from "../../../components/styled-components/Sections";
import {RegisterHeader} from "../../../components/styled-components/Headers";
import {RegisterBody} from "../../../components/styled-components/Screens";
import ValidationService from "../../../services/ValidationService";

const RegisterRefugeeAccountForm = () => {

    let navigate = useNavigate();
    const [state, setState] = useState({});

    const handleStateChanged = (event, key) => {
        state[key] = event.target.value;
        setState(state);
    }

    const createNewUser = () => {
        return {
            email: state["email"],
            password: state["password"],
            isAccountVerified: true,
            accountType: "REFUGEE"
        };
    }

    // temporary alert-based error handling
    // todo: create proper error info
    const handleRegisterButton = () => {
        const stringForms = [state["email"], state["password"], state["confirmPassword"]];
        if (!ValidationService.areStringsValid(stringForms)) {
            window.alert("Text input cannot be empty");
            return;
        }
        if (state["password"] !== state["confirmPassword"]) {
            window.alert("Passwords do not match");
            return;
        }
        const userToBeRegistered = createNewUser();
        sessionStorage.setItem('userToBeRegistered', JSON.stringify(userToBeRegistered));
        navigate("/registerRefugee/account-basic-info");
    }

    return (
        <RegisterBody>
            <RegisterHeader>Account creation.</RegisterHeader>
            <RegisterSection>
                <InputFormFilled value={state["email"]} onChange={(e) => {
                    handleStateChanged(e, "email");
                }} inputLabel="Email:" type="text"/>
            </RegisterSection>
            <RegisterSection>
                <InputFormFilled value={state["password"]} onChange={(e) => {
                    handleStateChanged(e, "password");
                }} inputLabel="Password:" type="password"/>
            </RegisterSection>
            <RegisterSection>
                <InputFormFilled value={state["confirmPassword"]} onChange={(e) => {
                    handleStateChanged(e, "confirmPassword");
                }} inputLabel="Confirm password:" type="password"/>
            </RegisterSection>
            <PustePole20px>

            </PustePole20px>
            <RegisterSection>
                <AppButton onClick={handleRegisterButton}>Create an account</AppButton>
            </RegisterSection>
        </RegisterBody>

    )
}

export default RegisterRefugeeAccountForm;