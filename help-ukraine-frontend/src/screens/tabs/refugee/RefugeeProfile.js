import React, {useState} from "react";
import {useNavigate} from "react-router";
import AuthService from "../../../services/AuthService";
import ValidationService from "../../../services/ValidationService";
import {ProfileBody} from "../../../components/styled-components/Screens";
import {AppSection, PustePole20px, TextSection} from "../../../components/styled-components/Sections";
import {Checkbox, Dropdown, InputFormFilled, TextareaContent} from "../../../components/widgets/Inputs";
import AppButton from "../../../components/styled-components/AppButton";
import SearchingOfferService from "../../../services/SearchingOfferService";


const RefugeeProfile = () => {
    const navigate = useNavigate();
    const [name, setName] = useState("");
    const [surname, setSurname] = useState("");
    const [birthDate, setBirthDate] = useState("");
    const [phone, setPhoneNumber] = useState("");
    const [email, setEmail] = useState("");
    const [sex, setSex] = useState("");
    const [additionalInfo, setAdditionalInfo] = useState("");
    const [userMovingIssues, setUserMovingIssues] = useState(false);
    const [animalsInvolved, setAnimalsInvolved] = useState(false);

    React.useEffect(() => {
        const user = AuthService.getCurrentUser();
        const searchingOffer = SearchingOfferService.getCurrentSearchingOffer();
        rebuildDataForms(user, searchingOffer);
    }, []);

    // temporary alert-based error handling
    // todo: create proper error info
    const validateInputs = () => {
        if (!ValidationService.areStringsValid([name, surname, phone, email, additionalInfo])) {
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
        return true;
    }

    const buildModifiedSearchingOfferData = (searchingOfferData) => {
        const updatedSearchingOfferData = Object.assign({}, searchingOfferData);
        updatedSearchingOfferData.userMovingIssues = userMovingIssues;
        updatedSearchingOfferData.animalsInvolved = animalsInvolved;
        updatedSearchingOfferData.additionalInfo = additionalInfo;
        return updatedSearchingOfferData;
    }

    const buildModifiedUserData = (userData) => {
        const updatedUserData = Object.assign({}, userData);
        updatedUserData.name = name;
        updatedUserData.surname = surname;
        updatedUserData.birthDate = birthDate;
        updatedUserData.email = email;
        updatedUserData.phoneNumber = phone;
        updatedUserData.sex = sex.toUpperCase();
        return updatedUserData;
    }

    const rebuildDataForms = (userData, searchingOffer) => {
        setName(userData.name);
        setSurname(userData.surname);
        setBirthDate(userData.birthDate);
        setEmail(userData.email);
        setPhoneNumber(userData.phoneNumber);
        setSex(userData.sex === 'MALE'? 'Male': 'Female');
        setUserMovingIssues(searchingOffer.userMovingIssues);
        setAnimalsInvolved(searchingOffer.animalsInvolved);
        setAdditionalInfo(searchingOffer.additionalInfo)
    }

    const modifyUserAndSearchingOffer = async (userData, offerData) => {
        const updatedUserData = buildModifiedUserData(userData);
        const updatedOfferData = buildModifiedSearchingOfferData(offerData);
        const userRes = await AuthService.modifyCurrentUser(updatedUserData);
        const offerRes = await SearchingOfferService.modifyCurrentSearchingOffer(updatedOfferData);
        return [userRes, offerRes];
    }

    const handleSaveButton = () => {
        if (!validateInputs()) {
            return;
        }
        const userData = AuthService.getCurrentUser();
        const offerData = SearchingOfferService.getCurrentSearchingOffer();
        modifyUserAndSearchingOffer(userData, offerData).then(([userRes, offerRes]) => {
            if (isEmailModified(userData.email, userRes.email)) {
                logOut();
                window.alert("Email was modified - you have to login again");
                return;
            }
            rebuildDataForms(userRes, offerRes);
        }).catch(error => {
            rebuildDataForms(userData, offerData);
            window.alert("Profile edition failed: " + error.response?.data);
        });
    }

    const isEmailModified = (oldValue, newValue) => {
        return oldValue !== newValue;
    }

    const logOut = () => {
        AuthService.logout();
        navigate("/");
    }

    return (
        <div className="profile">
            <ProfileBody>
                <h1>Profile</h1>
                <AppSection>
                    <InputFormFilled value={name} onChange={(e) => {
                        setName(e.target.value);
                    }} inputLabel="Name:" type="text" dark="true"/>
                </AppSection>
                <PustePole20px/>
                <AppSection>
                    <InputFormFilled value={surname} onChange={(e) => {
                        setSurname(e.target.value)
                    }} inputLabel="Surname:" type="text" dark="true"/>
                </AppSection>
                <PustePole20px/>
                <AppSection>
                    <Dropdown
                        initalValue={sex}
                        inputLabel="Sex:" dark="true"
                        onChangeCallback={(value) => setSex(value.value)}
                        options={[
                            {key: "male", value: "Male"},
                            {key: "female", value: "Female"}
                        ]}/>
                </AppSection>
                <AppSection>
                    <InputFormFilled value={birthDate} onChange={(e) => {
                        setBirthDate(e.target.value);
                    }} inputLabel="Date of birth:" type="date" dark="true"/>
                </AppSection>
                <PustePole20px/>
                <AppSection>
                    <InputFormFilled value={phone} onChange={(e) => {
                        setPhoneNumber(e.target.value)
                    }} inputLabel="Phone number:" type="text" dark="true"/>
                </AppSection>
                <PustePole20px/>
                <AppSection>
                    <InputFormFilled value={email} onChange={(e) => {
                        setEmail(e.target.value)
                    }} inputLabel="Email:" type="text" dark="true"/>
                </AppSection>
                <AppSection>
                    <Checkbox initialState={userMovingIssues}
                              onCheckCallback={(value) => setUserMovingIssues(value)}
                              inputLabel="I have a physical disability and require a wheelchair"/>
                </AppSection>
                <AppSection>
                    <Checkbox  initialState={animalsInvolved}
                               onCheckCallback={(value) => setAnimalsInvolved(value)}
                               inputLabel="I have a pet"/>
                </AppSection>
                <PustePole20px/>
                <TextSection>
                    Additional information that might be important about you (allergies, diseases, requiring special
                    treatment, etc.)
                </TextSection>
                <PustePole20px/>
                <AppSection>
                    <TextareaContent value={additionalInfo} onChange={(e) => setAdditionalInfo(e.target.value)}/>
                </AppSection>
                <PustePole20px/>
                <AppSection>
                    <AppButton onClick={handleSaveButton}>
                        Save
                    </AppButton>
                </AppSection>
                <AppSection>
                    <AppButton onClick={logOut}>
                        Logout
                    </AppButton>
                </AppSection>
            </ProfileBody>
            <PustePole20px/>
        </div>
    );
}

export default RefugeeProfile;