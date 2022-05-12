import React, {useState} from "react";
import {Checkbox, InputFormFilled} from "../../components/widgets/Inputs";
import {ProfileBody} from "../../components/styled-components/Screens";
import AppButton from "../../components/styled-components/AppButton";
import {AppSection} from "../../components/styled-components/Sections";
import AuthService from "../../services/AuthService";
import {useNavigate} from "react-router-dom";

const Profile = () => {
    const navigate = useNavigate();
    const [name, setName] = useState("");
    const [surname, setSurname] = useState("");
    const [birthDate, setBirthDate] = useState("");
    const [phone, setPhoneNumber] = useState("");
    const [email, setEmail] = useState("");

    React.useEffect(() => {
        const userFormStorage = sessionStorage.getItem("user");
        const user = JSON.parse(userFormStorage);
        console.log(user);
        setName(user.name);
        setSurname(user.surname);
        setBirthDate(user.birthDate);
        setEmail(user.email);
        setPhoneNumber(user.phoneNumber);
    }, []);

    const handleProceedButton = () => {
        const userFormStorage = sessionStorage.getItem("user");
        const user = JSON.parse(userFormStorage);
        user.name = name;
        user.Surname = surname;
        user.Birthdate = birthDate;
        user.Email = email;
        user.phoneNumber = phone;
        //todo zrobić wysyłkę
        sessionStorage.setItem("user", JSON.stringify(user));
    }
    const logOut = () => {
        AuthService.logout();
        navigate("/");
    }
    return (
    <div className="profile">
        <ProfileBody>
            <h1>Profile </h1>
            <AppSection>
                <InputFormFilled value={name} onChange={(e) => { setName(e.target.value); }} inputLabel="Name:" type="text" dark="true"/>
            </AppSection>
            <AppSection>
                <InputFormFilled value={surname} onChange={(e) => {setSurname(e.target.value)   }} inputLabel="Surname:" type="text" dark="true"/>
            </AppSection>
            <AppSection>
                <InputFormFilled value={birthDate} onChange={(e) => {
                    setBirthDate(e.target.value);
                }} inputLabel="Date of birth:" type="date"/>
            </AppSection>
            <AppSection>
                <InputFormFilled value={phone} onChange={(e) => {setPhoneNumber(e.target.value)   }} inputLabel="Phone number:" type="text" dark="true"/>
            </AppSection>
            <AppSection>
                <InputFormFilled value={email} onChange={(e) => {setEmail(e.target.value)   }} inputLabel="Email:" type="text" dark="true"/>
            </AppSection>
            <AppSection>
                <Checkbox inputLabel="I have a physical disability and require a wheelchair"/>
            </AppSection>
            <AppSection>
                <Checkbox inputLabel="I have a pet"/>
            </AppSection>
            <AppSection>
                <AppButton onClick={handleProceedButton}>
                    Save
                </AppButton>
            </AppSection>
            <AppSection>
                <AppButton onClick={logOut}>
                    Logout
                </AppButton>
            </AppSection>
        </ProfileBody>
    </div>
    );
}

export default Profile;