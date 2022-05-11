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
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");

    const createNewUser = () => {
        return {
            email: email,
            password: password,
            isAccountVerified: true,
            accountType: "REFUGEE"
        };
    }

    // temporary alert-based error handling
    // todo: create proper error info
    const validateInputs = () => {
        const stringForms = [email, password, confirmPassword];
        if (!ValidationService.areStringsValid(stringForms)) {
            window.alert("Text input cannot be empty");
            return false;
        }
        if (!ValidationService.isEmailValid(email)) {
            window.alert("Entered email is invalid");
            return false;
        }
        if (password !== confirmPassword) {
            window.alert("Passwords do not match");
            return false;
        }
        return true;
    }

    const handleRegisterButton = () => {
        if (!validateInputs()) {
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
                <InputFormFilled value={email} onChange={(e) => {
                    setEmail(e.target.value);
                }} inputLabel="Email:" type="text"/>
            </RegisterSection>
            <RegisterSection>
                <InputFormFilled value={password} onChange={(e) => {
                    setPassword(e.target.value);
                }} inputLabel="Password:" type="password"/>
            </RegisterSection>
            <RegisterSection>
                <InputFormFilled value={confirmPassword} onChange={(e) => {
                    setConfirmPassword(e.target.value);
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