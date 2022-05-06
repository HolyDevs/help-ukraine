import React, {useState} from 'react';
import {RegisterBody} from "../../../components/styled-components/Screens";
import {RegisterHeader} from "../../../components/styled-components/Headers";
import {PustePole20px, RegisterSection} from "../../../components/styled-components/Sections";
import {Dropdown, InputFormFilled} from "../../../components/widgets/Inputs";
import {useNavigate} from "react-router-dom";
import AppButton from "../../../components/styled-components/AppButton";
import ValidationService from "../../../services/ValidationService";

const RegisterVolunteer = () => {

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
            name: state["name"],
            surname: state["surname"],
            phoneNumber: state["phone"],
            sex: state["sex"].value.toUpperCase(),
            isAccountVerified: true,
            accountType: "HOST",
            birthDate: state["dateOfBirth"]
        };
    }

    // temporary alert-based error handling
    // todo: create proper error info
    const handleSubmitButton = () => {
        const stringForms = [state["name"], state["surname"], state["email"],
            state["password"], state["confirmPassword"], state["phone"]]
        if (!ValidationService.areStringsValid(stringForms)) {
            window.alert("Text input cannot be empty");
            return;
        }
        if (!ValidationService.isDateValid(state["dateOfBirth"])) {
            window.alert("Chosen date is invalid");
            return;
        }
        if (state["password"] !== state["confirmPassword"]) {
            window.alert("Passwords do not match");
            return;
        }
        const userToBeRegistered = createNewUser();
        sessionStorage.setItem('userToBeRegistered', JSON.stringify(userToBeRegistered));
        navigate("/registerVolunteer/volunteer-further-info");
    }

    return (
        <RegisterBody>
            <RegisterHeader>Enter form to continue.</RegisterHeader>
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
                <InputFormFilled value={state["dateOfBirth"]} onChange={(e) => {
                    handleStateChanged(e, "dateOfBirth");
                }} inputLabel="Date of birth:" type="date"/>
            </RegisterSection>
            <RegisterSection>
                <Dropdown value={state["sex"] = {value: "Male"}} inputLabel="Sex:"
                          onChange={(e) => handleStateChanged(e, 'sex')}
                          options={[
                              {key: "male", value: "Male"},
                              {key: "female", value: "Female"}
                          ]}/>
            </RegisterSection>
            <RegisterSection>
                <InputFormFilled value={state["phone"]} onChange={(e) => {
                    handleStateChanged(e, "phone");
                }} inputLabel="Phone:" type="tel"/>
            </RegisterSection>
            <RegisterSection>
                <InputFormFilled value={state["email"]} onChange={(e) => {
                    handleStateChanged(e, "email");
                }} inputLabel="Email:" type="mail"/>
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
            <PustePole20px/>
            <RegisterSection>
                <PustePole20px>
                </PustePole20px>
                <AppButton onClick={handleSubmitButton}>Submit</AppButton>
            </RegisterSection>
        </RegisterBody>
    );
}
export default RegisterVolunteer;