import React, {useState} from "react";
import {Dropdown, InputFormFilled} from "../../components/widgets/Inputs";
import {ProfileBody} from "../../components/styled-components/Screens";
import AppButton from "../../components/styled-components/AppButton";
import {AppSection, PustePole20px} from "../../components/styled-components/Sections";
import AuthService from "../../services/AuthService";
import {useNavigate} from "react-router-dom";
import ValidationService from "../../services/ValidationService";

const Profile = () => {
    const navigate = useNavigate();
    const [name, setName] = useState("");
    const [surname, setSurname] = useState("");
    const [birthDate, setBirthDate] = useState("");
    const [phone, setPhoneNumber] = useState("");
    const [email, setEmail] = useState("");
    const [sex, setSex] = useState("");

    React.useEffect(() => {
        const user = AuthService.getCurrentUser();
        rebuildDataForms(user);
    }, []);

    // temporary alert-based error handling
    // todo: create proper error info
    const validateInputs = () => {
        if (!ValidationService.areStringsValid([name, surname, phone, email])) {
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

    const rebuildDataForms = (userData) => {
        setName(userData.name);
        setSurname(userData.surname);
        setBirthDate(userData.birthDate);
        setEmail(userData.email);
        setPhoneNumber(userData.phoneNumber);
        setSex(userData.sex === 'MALE'? 'Male': 'Female');
    }

    const handleSaveButton = () => {
        if (!validateInputs()) {
            return;
        }
        const userData = AuthService.getCurrentUser();
        const updatedUserData = buildModifiedUserData(userData);
        AuthService.modifyCurrentUser(updatedUserData).then(res => {
            if (isEmailModified(userData.email, res.email)) {
                logOut();
                window.alert("Email was modified - you have to login again");
                return;
            }
            rebuildDataForms(res);
        }).catch(error => {
            rebuildDataForms(userData);
            window.alert("Profile edition failed: " + error.response?.data);
        })
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
                {/*<AppSection>*/}
                {/*    <Checkbox inputLabel="I have a physical disability and require a wheelchair"/>*/}
                {/*</AppSection>*/}
                {/*<AppSection>*/}
                {/*    <Checkbox inputLabel="I have a pet"/>*/}
                {/*</AppSection>*/}
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

export default Profile;