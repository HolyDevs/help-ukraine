import React, {useState} from 'react';
import {RegisterBody} from "../../../components/styled-components/Screens";
import {RegisterHeader} from "../../../components/styled-components/Headers";
import {AppSection, PustePole20px} from "../../../components/styled-components/Sections";
import {Dropdown, InputFormFilled} from "../../../components/widgets/Inputs";
import {useNavigate} from "react-router-dom";
import AppButton from "../../../components/styled-components/AppButton";
import ValidationService from "../../../services/ValidationService";
import LabelService from "../../../services/LabelService";

const RegisterHost = () => {

    let navigate = useNavigate();
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [name, setName] = useState("");
    const [surname, setSurname] = useState("");
    const [phone, setPhone] = useState("");
    const [sex, setSex] = useState(LabelService.getLabelFromKey("MALE"));
    const [birthDate, setBirthDate] = useState();

    const createNewUser = () => {
        return {
            email: email,
            password: password,
            name: name,
            surname: surname,
            phoneNumber: phone,
            sex: LabelService.getKeyFromLabel(sex),
            isAccountVerified: true,
            accountType: "HOST",
            birthDate: birthDate
        };
    }

    React.useEffect(() => {
        const userData = sessionStorage.getItem('userToBeRegistered');
        if (!userData) {
            return;
        }
        const user = JSON.parse(userData);
        setEmail(user.email);
        setName(user.name);
        setSurname(user.surname);
        setPhone(user.phoneNumber);
        setBirthDate(user.birthDate);
        setPassword(user.password);
        setConfirmPassword(user.password);
        setSex(LabelService.getLabelFromKey(user.sex));
    })

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
                <InputFormFilled value={birthDate} onChange={(e) => {
                    setBirthDate(e.target.value);
                }} inputLabel="Date of birth:" type="date"/>
            </AppSection>
            <AppSection>
                <Dropdown inputLabel="Sex:"
                          initalValue={sex}
                          onChangeCallback={(value) => setSex(value.value)}
                          options={[
                              {key: "male", value: LabelService.getLabelFromKey("MALE")},
                              {key: "female", value: LabelService.getLabelFromKey("FEMALE")}
                          ]}/>
            </AppSection>
            <AppSection>
                <InputFormFilled value={phone} onChange={(e) => {
                    setPhone(e.target.value);
                }} inputLabel="Phone:" type="tel"/>
            </AppSection>
            <AppSection>
                <InputFormFilled value={email} onChange={(e) => {
                    setEmail(e.target.value);
                }} inputLabel="Email:" type="mail"/>
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
            <PustePole20px/>
            <AppSection>
                <PustePole20px>
                </PustePole20px>
                <AppButton onClick={handleSubmitButton}>Submit</AppButton>
            </AppSection>
        </RegisterBody>
    );
}
export default RegisterHost;