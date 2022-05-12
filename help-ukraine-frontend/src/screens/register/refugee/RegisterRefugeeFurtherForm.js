import React, {useState} from 'react';
import {Checkbox, Dropdown, InputFormFilled, TextareaContent} from "../../../components/widgets/Inputs";
import {useNavigate} from "react-router-dom";
import AppButton from "../../../components/styled-components/AppButton";
import {PustePole20px, RegisterSection, TextSection} from "../../../components/styled-components/Sections";
import {RegisterHeader} from "../../../components/styled-components/Headers";
import {RegisterBody} from "../../../components/styled-components/Screens";
import ValidationService from "../../../services/ValidationService";
import AuthService from "../../../services/AuthService";


const RegisterRefugeeFurtherForm = () => {

    let navigate = useNavigate();
    const [phone, setPhone] = useState("");
    const [sex, setSex] = useState("MALE");
    const [birthDate, setBirthDate] = useState();

    const fillNewUserWithData = () => {
        const userToBeRegistered = JSON.parse(sessionStorage.getItem('userToBeRegistered'));
        userToBeRegistered.phoneNumber = phone;
        userToBeRegistered.sex = sex.toUpperCase();
        userToBeRegistered.birthDate = birthDate;
        return userToBeRegistered;
    }

    const registerUser = async () => {
        const userToBeRegistered = fillNewUserWithData();
        await AuthService.register(userToBeRegistered);
    }

    // temporary alert-based error handling
    // todo: create proper error info
    const validateInputs = () => {
        if (!ValidationService.isStringValid(phone)) {
            window.alert("Text input cannot be empty");
            return false;
        }
        if (!ValidationService.isBirthDateValid(birthDate)) {
            window.alert("Chosen date is invalid");
            return false;
        }
        return true;
    }

    const handleProceedButton = () => {
        if (!validateInputs()) {
            return;
        }
        registerUser()
            .then(() => {
                sessionStorage.removeItem('userToBeRegistered');
                navigate("/main/search");
            }).catch((error) => {
            window.alert("Registration failed: " + error.response.data);
        });
    }

    return (
        <RegisterBody>
            <RegisterHeader>
                We need some further information.
            </RegisterHeader>
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
                }} inputLabel="Phone number:" type="tel"/>
            </RegisterSection>
            <PustePole20px/>
            <RegisterSection>
                <Checkbox inputLabel="I have a physical disability and require a wheelchair"/>
            </RegisterSection>
            <RegisterSection>
                <Checkbox inputLabel="I have a pet"/>
            </RegisterSection>
            <PustePole20px/>
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