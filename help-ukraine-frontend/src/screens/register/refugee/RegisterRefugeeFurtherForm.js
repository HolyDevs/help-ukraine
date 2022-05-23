import React, {useState} from 'react';
import {Checkbox, Dropdown, InputFormFilled, TextareaContent} from "../../../components/widgets/Inputs";
import {useNavigate} from "react-router-dom";
import AppButton from "../../../components/styled-components/AppButton";
import {PustePole20px, AppSection, TextSection} from "../../../components/styled-components/Sections";
import {RegisterHeader} from "../../../components/styled-components/Headers";
import {RegisterBody} from "../../../components/styled-components/Screens";
import ValidationService from "../../../services/ValidationService";
import AuthService from "../../../services/AuthService";


const RegisterRefugeeFurtherForm = () => {

    let navigate = useNavigate();
    const [phone, setPhone] = useState("");
    const [sex, setSex] = useState("Male");
    const [birthDate, setBirthDate] = useState();
    const [petAllowed, setPetAllowed] = useState(false);
    const [wheelchairRequired, setWheelchairRequired] = useState(false);
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
                We need some further information about you.
            </RegisterHeader>
            <AppSection>
                <InputFormFilled value={birthDate} onChange={(e) => {
                    setBirthDate(e.target.value);
                }} inputLabel="Date of birth:" type="date"/>
            </AppSection>
            <AppSection>
                <Dropdown inputLabel="Sex:"
                          initalValue={sex}
                          onChangeCallback={(value) => setSex(value.value)}
                          options={[
                              {key: "male", value: "Male"},
                              {key: "female", value: "Female"}
                          ]}/>
            </AppSection>
            <AppSection>
                <InputFormFilled value={phone} onChange={(e) => {
                    setPhone(e.target.value);
                }} inputLabel="Phone number:" type="tel"/>
            </AppSection>
            <PustePole20px/>
            <AppSection>
                <Checkbox initialState={wheelchairRequired} onCheckCallback={(value) => setWheelchairRequired(value)}  inputLabel="I have a physical disability and require a wheelchair"/>
            </AppSection>
            <AppSection>
                <Checkbox initialState={petAllowed} onCheckCallback={(value) => setPetAllowed(value)} inputLabel="I have a pet"/>
            </AppSection>
            <PustePole20px/>
            <TextSection>
                Additional information that might be important about you (allergies, diseases, requiring special
                treatment, etc.)
            </TextSection>
            <AppSection>
                <TextareaContent />
            </AppSection>
            <AppSection>
                <AppButton onClick={handleProceedButton}>
                    Proceed
                </AppButton>
            </AppSection>
        </RegisterBody>

    )
}

export default RegisterRefugeeFurtherForm;