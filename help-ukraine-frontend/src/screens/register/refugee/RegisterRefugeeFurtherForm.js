import React, {useState} from 'react';
import {Checkbox, Dropdown, InputFormFilled, TextareaContent} from "../../../components/widgets/Inputs";
import {useNavigate} from "react-router-dom";
import AppButton from "../../../components/styled-components/AppButton";
import {AppSection, PustePole20px, TextSection} from "../../../components/styled-components/Sections";
import {RegisterHeader} from "../../../components/styled-components/Headers";
import {RegisterBody} from "../../../components/styled-components/Screens";
import ValidationService from "../../../services/ValidationService";
import AuthService from "../../../services/AuthService";
import SearchingOfferService from "../../../services/SearchingOfferService";
import LabelService from "../../../services/LabelService";


const RegisterRefugeeFurtherForm = () => {

    let navigate = useNavigate();
    const [phone, setPhone] = useState("");
    const [sex, setSex] = useState(LabelService.getLabelFromKey("MALE"));
    const [birthDate, setBirthDate] = useState();
    const [userMovingIssues, setUserMovingIssues] = useState(false);
    const [animalsInvolved, setAnimalsInvolved] = useState(false);
    const [additionalInfo, setAdditionalInfo] = useState("");

    const fillNewUserWithData = () => {
        const userToBeRegistered = JSON.parse(sessionStorage.getItem('userToBeRegistered'));
        userToBeRegistered.phoneNumber = phone;
        userToBeRegistered.sex = LabelService.getKeyFromLabel(sex);
        userToBeRegistered.birthDate = birthDate;
        return userToBeRegistered;
    }

    const fillNewSearchingOfferWithData = (refugeeId) => {
        const searchingOfferToBeCreated = JSON.parse(sessionStorage.getItem('searchingOfferToBeCreated'));
        searchingOfferToBeCreated.refugeeId = refugeeId;
        searchingOfferToBeCreated.userMovingIssues = userMovingIssues;
        searchingOfferToBeCreated.animalsInvolved = animalsInvolved;
        searchingOfferToBeCreated.additionalInfo = additionalInfo;
        searchingOfferToBeCreated.rangeFromPreferredLocationInKm = 20.0;
        searchingOfferToBeCreated.preferredLocation = "Warsaw";
        return searchingOfferToBeCreated;
    }

    const registerUserAndCreateNewSearchingOffer = async () => {
        const userToBeRegistered = fillNewUserWithData();
        const registerRes = await AuthService.register(userToBeRegistered);
        const searchingOffer = fillNewSearchingOfferWithData(registerRes.id);
        await SearchingOfferService.createCurrentSearchingOffer(searchingOffer);
    }

    // temporary alert-based error handling
    // todo: create proper error info
    const validateInputs = () => {
        if (!ValidationService.areStringsValid([phone, additionalInfo])) {
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
        registerUserAndCreateNewSearchingOffer()
            .then(() => {
                sessionStorage.removeItem('userToBeRegistered');
                sessionStorage.removeItem('searchingOfferToBeCreated');
                navigate("/refugee/search");
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
                              {key: "male", value: LabelService.getLabelFromKey("MALE")},
                              {key: "female", value: LabelService.getLabelFromKey("FEMALE")}
                          ]}/>
            </AppSection>
            <AppSection>
                <InputFormFilled value={phone} onChange={(e) => {
                    setPhone(e.target.value);
                }} inputLabel="Phone number:" type="tel"/>
            </AppSection>
            <PustePole20px/>
            <AppSection>
                <Checkbox initialState={userMovingIssues} onCheckCallback={(value) => setUserMovingIssues(value)}  inputLabel="I have a physical disability and require a wheelchair"/>
            </AppSection>
            <AppSection>
                <Checkbox initialState={animalsInvolved} onCheckCallback={(value) => setAnimalsInvolved(value)} inputLabel="I have a pet"/>
            </AppSection>
            <PustePole20px/>
            <TextSection>
                Additional information that might be important about you (allergies, diseases, requiring special
                treatment, etc.)
            </TextSection>
            <AppSection>
                <TextareaContent value={additionalInfo} onChange={(e) => setAdditionalInfo(e.target.value)}/>
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