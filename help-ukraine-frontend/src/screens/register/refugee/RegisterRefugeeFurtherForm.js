import React, {useState} from 'react';
import {Checkbox, Dropdown, InputFormFilled, TextareaContent} from "../../../components/widgets/Inputs";
import {useNavigate} from "react-router-dom";
import AppButton from "../../../components/styled-components/AppButton";
import {
    PustePoleJakLebBrzezinskiego20px,
    RegisterSection,
    TextSection
} from "../../../components/styled-components/Sections";
import {RegisterHeader} from "../../../components/styled-components/Headers";
import {RegisterBody} from "../../../components/styled-components/Screens";
import ValidationService from "../../../services/ValidationService";
import AuthService from "../../../services/AuthService";


const RegisterRefugeeFurtherForm = () => {

    let navigate = useNavigate();
    const [state, setState] = useState({});

    const handleStateChanged = (event, key) => {
        state[key] = event.target.value;
        setState(state);
    }

    const fillNewUserWithData = () => {
        const userToBeRegistered = JSON.parse(localStorage.getItem('userToBeRegistered'));
        userToBeRegistered.phoneNumber = state["phone"];
        userToBeRegistered.sex = state["sex"].value.toUpperCase();
        userToBeRegistered.birthDate = state["dateOfBirth"];
        userToBeRegistered.isAccountVerified = true;
        userToBeRegistered.accountType = "REFUGEE";
        return userToBeRegistered;
    }

    // temporary alert-based error handling
    // todo: create proper error info
    const handleProceedButton = () => {
        if (!ValidationService.isStringValid(state["phone"])) {
            window.alert("Text input cannot be empty");
            return;
        }
        if (!ValidationService.isDateValid(state["dateOfBirth"])) {
            window.alert("Chosen date is invalid");
            return;
        }
        const userToBeRegistered = fillNewUserWithData();
        AuthService.register(userToBeRegistered)
            .then(() => {
                localStorage.removeItem('userToBeRegistered');
                navigate("/main/search");
            })
            .catch((error) => {
                window.alert("Registration failed: " + error.response.data);
            });
    }

    return (
        <RegisterBody>
            <RegisterHeader>
                We need some further information.
            </RegisterHeader>
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
                }} inputLabel="Phone number:" type="tel"/>
            </RegisterSection>
            <PustePoleJakLebBrzezinskiego20px/>
            <RegisterSection>
                <Checkbox inputLabel="I have a physicial disability and require a wheelchair"/>
            </RegisterSection>
            <RegisterSection>
                <Checkbox inputLabel="I have a pet"/>
            </RegisterSection>
            <PustePoleJakLebBrzezinskiego20px/>
            <TextSection>
                Additional information that might be important about you (allergies, diseases, requiring special
                treatment, etc.)
            </TextSection>
            <RegisterSection>
                <TextareaContent/>
            </RegisterSection>
            <RegisterSection>
                <AppButton onClick={handleProceedButton}>
                    Proceed
                </AppButton>
            </RegisterSection>
        </RegisterBody>

    )
}

export default RegisterRefugeeFurtherForm;