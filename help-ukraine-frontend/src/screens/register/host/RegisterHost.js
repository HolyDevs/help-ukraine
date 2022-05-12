import React, {useState} from 'react';
import {RegisterBody} from "../../../components/styled-components/Screens";
import {RegisterHeader} from "../../../components/styled-components/Headers";
import {PustePole20px, RegisterSection} from "../../../components/styled-components/Sections";
import {Dropdown, InputFormFilled} from "../../../components/widgets/Inputs";
import {useNavigate} from "react-router-dom";
import AppButton from "../../../components/styled-components/AppButton";
import ValidationService from "../../../services/ValidationService";

const RegisterHost = () => {

    let navigate = useNavigate();
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [name, setName] = useState("");
    const [surname, setSurname] = useState("");
    const [phone, setPhone] = useState("");
    const [sex, setSex] = useState("MALE");
    const [birthDate, setBirthDate] = useState();

    const createNewUser = () => {
        return {
            email: email,
            password: password,
            name: name,
            surname: surname,
            phoneNumber: phone,
            sex: sex.toUpperCase(),
            isAccountVerified: true,
            accountType: "HOST",
            birthDate: birthDate
        };
    }

    // temporary alert-based error handling
    // todo: create proper error info
    const validateInputs = () => {
        const stringForms = [name, surname, password, confirmPassword, phone, email]
        if (!ValidationService.areStringsValid(stringForms)) {
            window.alert("Text input cannot be empty");
            return false;
        }
        if (!ValidationService.isEmailValid(email)) {
            window.alert("Entered email is invalid");
            return false;
        }
        if (!ValidationService.isBirthDateValid(birthDate)) {
            window.alert("Chosen date is invalid");
            return false;
        }
        if (password !== confirmPassword) {
            window.alert("Passwords do not match");
            return false;
        }
        return true;
    }

    const handleSubmitButton = () => {
        if (!validateInputs()) {
            return;
        }
        const userToBeRegistered = createNewUser();
        sessionStorage.setItem('userToBeRegistered', JSON.stringify(userToBeRegistered));
        navigate("/registerHost/host-further-info");
    }

    return (
        <RegisterBody>
            <RegisterHeader>Enter form to continue.</RegisterHeader>
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
                <InputFormFilled value={birthDate} onChange={(e) => {
                    setBirthDate(e.target.value);
                }} inputLabel="Date of birth:" type="date"/>
            </RegisterSection>
            <RegisterSection>
                <Dropdown inputLabel="Sex:"
                          onChangeCallback={(value) => setSex(value)}
                          options={[
                              {key: "male", value: "Male"},
                              {key: "female", value: "Female"}
                          ]}/>
            </RegisterSection>
            <RegisterSection>
                <InputFormFilled value={phone} onChange={(e) => {
                    setPhone(e.target.value);
                }} inputLabel="Phone:" type="tel"/>
            </RegisterSection>
            <RegisterSection>
                <InputFormFilled value={email} onChange={(e) => {
                    setEmail(e.target.value);
                }} inputLabel="Email:" type="mail"/>
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
            <PustePole20px/>
            <RegisterSection>
                <PustePole20px>
                </PustePole20px>
                <AppButton onClick={handleSubmitButton}>Submit</AppButton>
            </RegisterSection>
        </RegisterBody>
    );
}
export default RegisterHost;