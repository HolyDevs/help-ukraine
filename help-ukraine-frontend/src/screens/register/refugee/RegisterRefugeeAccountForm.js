import React, {useState} from 'react';
import {InputFormFilled} from "../../../components/widgets/Inputs";
import {useNavigate} from "react-router-dom";
import AppButton from "../../../components/styled-components/AppButton";
import {PustePole20px, AppSection} from "../../../components/styled-components/Sections";
import {RegisterHeader} from "../../../components/styled-components/Headers";
import {RegisterBody} from "../../../components/styled-components/Screens";
import ValidationService from "../../../services/ValidationService";

const RegisterRefugeeAccountForm = () => {

    let navigate = useNavigate();
    const [email, setEmail] = useState("");
    const [name, setName] = useState("");
    const [surname, setSurname] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");

    React.useEffect(() => {
        const userData = sessionStorage.getItem('userToBeRegistered');
        if (!userData) {
            return;
        }
        const user = JSON.parse(userData);
        setEmail(user.email);
        setName(user.name);
        setSurname(user.surname);
        setPassword(user.password);
        setConfirmPassword(user.password);
    })

    const createNewUser = () => {
        return {
            email: email,
            name: name,
            surname: surname,
            password: password,
            isAccountVerified: true,
            accountType: "REFUGEE"
        };
    }

    // temporary alert-based error handling
    // todo: create proper error info
    const validateInputs = () => {
        const stringForms = [email, password, confirmPassword, name, surname];
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
        navigate("/registerRefugee/account-family");
    }

    return (
        <RegisterBody>
            <RegisterHeader>Account creation.</RegisterHeader>
            <AppSection>
                <InputFormFilled value={email} onChange={(e) => {
                    setEmail(e.target.value);
                }} inputLabel="Email:" type="text"/>
            </AppSection>
            <AppSection>
                <InputFormFilled value={name} onChange={(e) => {
                    setName(e.target.value);
                }} inputLabel="Name:" type="text"/>
            </AppSection>
            <AppSection>
                <InputFormFilled value={surname} onChange={(e) => {
                    setSurname(e.target.value);
                }} inputLabel="Surname:" type="text"/>
            </AppSection>
            <AppSection>
                <InputFormFilled value={password} onChange={(e) => {
                    setPassword(e.target.value);
                }} inputLabel="Password:" type="password"/>
            </AppSection>
            <AppSection>
                <InputFormFilled value={confirmPassword} onChange={(e) => {
                    setConfirmPassword(e.target.value);
                }} inputLabel="Confirm password:" type="password"/>
            </AppSection>
            <PustePole20px>

            </PustePole20px>
            <AppSection>
                <AppButton onClick={handleRegisterButton}>Create an account</AppButton>
            </AppSection>
        </RegisterBody>

    )
}

export default RegisterRefugeeAccountForm;