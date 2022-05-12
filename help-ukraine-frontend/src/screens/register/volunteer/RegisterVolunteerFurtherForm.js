import React, {useState} from "react";
import {RegisterBody} from "../../../components/styled-components/Screens";
import {RegisterHeader} from "../../../components/styled-components/Headers";
import {PustePole20px, RegisterSection, TextSection} from "../../../components/styled-components/Sections";
import {Checkbox, Dropdown, InputFormFilled, TextareaContent} from "../../../components/widgets/Inputs";
import {useNavigate} from "react-router-dom";
import AppButton from "../../../components/styled-components/AppButton";
import AuthService from "../../../services/AuthService";
import PremiseOfferService from "../../../services/PremiseOfferService";
import ValidationService from "../../../services/ValidationService";

const RegisterVolunteerFurtherForm = () => {

    let navigate = useNavigate();
    const [peopleToTake, setPeopleToTake] = useState(1);
    const [bathrooms, setBathrooms] = useState(1);
    const [kitchens, setKitchens] = useState(1);
    const [bedrooms, setBedrooms] = useState(1);
    const [wheelchairFriendly, setWheelchairFriendly] = useState(false);
    const [animalsAllowed, setAnimalsAllowed] = useState(false);
    const [smokingAllowed, setSmokingAllowed] = useState(false);
    const [description, setDescription] = useState("");
    const [city, setCity] = useState("");
    const [houseNumber, setHouseNumber] = useState("");
    const [street, setStreet] = useState("");
    const [postalCode, setPostalCode] = useState("");
    const [fromDate, setFromDate] = useState();
    const [toDate, setToDate] = useState();

    const registerUserAndCreateNewPremiseOffer = async () => {
        const userToBeRegistered = JSON.parse(sessionStorage.getItem('userToBeRegistered'));
        const registerRes = await AuthService.register(userToBeRegistered);
        const premiseOffer = createNewPremiseOffer(registerRes.id);
        await PremiseOfferService.createPremiseOffer(premiseOffer);
    }

    const createNewPremiseOffer = (hostId) => {
        return {
            hostId: hostId,
            active: true,
            verified: true,
            peopleToTake: peopleToTake,
            bathrooms: bathrooms,
            kitchens: kitchens,
            bedrooms: bedrooms,
            animalsAllowed: animalsAllowed,
            wheelchairFriendly: wheelchairFriendly,
            smokingAllowed: smokingAllowed,
            city: city,
            street: street,
            postalCode: postalCode,
            houseNumber: houseNumber,
            description: description,
            fromDate: fromDate,
            toDate: toDate,
            offerImagesLocations: ["https://upload.wikimedia.org/wikipedia/commons/thumb/5/5e/Happy_family_%281%29.jpg/1200px-Happy_family_%281%29.jpg?20120321172928"]
        }
    }

    // temporary alert-based error handling
    // todo: create proper error info
    const validateInputs = () => {
        const stringForms = [street, city, houseNumber, postalCode, description];
        if (!ValidationService.areStringsValid(stringForms)) {
            window.alert("Text input cannot be empty");
            return false;
        }
        if (!ValidationService.areFromToDatesValid(fromDate, toDate)) {
            window.alert("Chosen date is invalid");
            return false;
        }
        return true;
    }

    const handleProceedButton = () => {
        if (!validateInputs()) {
            return;
        }
        registerUserAndCreateNewPremiseOffer().then(() => {
            sessionStorage.removeItem('userToBeRegistered');
            navigate("/volunteer");
        }).catch((error) => {
            window.alert("Registration failed: " + error.response?.data);
        });
    }

    return (
        <RegisterBody>
            <RegisterHeader>
                We need some information about your house.
            </RegisterHeader>
            <RegisterSection>
                <InputFormFilled value={fromDate} onChange={(e) => {
                    setFromDate(e.target.value);
                }} inputLabel="Offer active from:" type="date"/>
            </RegisterSection>
            <PustePole20px/>
            <RegisterSection>
                <InputFormFilled value={toDate} onChange={(e) => {
                    setToDate(e.target.value);
                }} inputLabel="Offer active to:" type="date"/>
            </RegisterSection>
            <PustePole20px/>
            <RegisterSection>
                <Dropdown inputLabel="Number of residents:"
                          onChangeCallback={(value) => setPeopleToTake(value)}
                          options={[
                              {key: "1", value: "1"},
                              {key: "2", value: "2"},
                              {key: "3", value: "3"},
                              {key: "4", value: "4"},
                              {key: "5", value: "5"},
                              {key: "6", value: "6"},
                              {key: "7", value: "7"},
                              {key: "8", value: "8"},
                              {key: "9", value: "9"},
                              {key: "10", value: "10"}
                          ]}/>
            </RegisterSection>
            <PustePole20px/>
            <RegisterSection>
                <Dropdown inputLabel="Number of bathrooms:"
                          onChangeCallback={(value) => setBathrooms(value)}
                          options={[
                              {key: "1", value: "1"},
                              {key: "2", value: "2"},
                              {key: "3", value: "3"},
                              {key: "4", value: "4"},
                              {key: "5", value: "5"},
                              {key: "6", value: "6"},
                              {key: "7", value: "7"},
                              {key: "8", value: "8"},
                              {key: "9", value: "9"},
                              {key: "10", value: "10"}
                          ]}/>
            </RegisterSection>
            <PustePole20px/>
            <RegisterSection>
                <Dropdown inputLabel="Number of kitchens:"
                          onChangeCallback={(value) => setKitchens(value)}
                          options={[
                              {key: "1", value: "1"},
                              {key: "2", value: "2"},
                              {key: "3", value: "3"},
                              {key: "4", value: "4"},
                              {key: "5", value: "5"},
                              {key: "6", value: "6"},
                              {key: "7", value: "7"},
                              {key: "8", value: "8"},
                              {key: "9", value: "9"},
                              {key: "10", value: "10"}
                          ]}/>
            </RegisterSection>
            <PustePole20px/>
            <RegisterSection>
                <Dropdown inputLabel="Number of bedrooms:"
                          onChangeCallback={(value) => setBedrooms(value)}
                          options={[
                              {key: "1", value: "1"},
                              {key: "2", value: "2"},
                              {key: "3", value: "3"},
                              {key: "4", value: "4"},
                              {key: "5", value: "5"},
                              {key: "6", value: "6"},
                              {key: "7", value: "7"},
                              {key: "8", value: "8"},
                              {key: "9", value: "9"},
                              {key: "10", value: "10"}
                          ]}/>
            </RegisterSection>
            <PustePole20px/>
            <TextSection>
                Additional information about equipment for the apartment
            </TextSection>
            <PustePole20px/>
            <RegisterSection>
                <TextareaContent value={description}
                                 onChange={(e) => setDescription(e.target.value)}/>
            </RegisterSection>
            <PustePole20px/>
            <RegisterSection>
                <Checkbox onCheckCallback={(value) => setWheelchairFriendly(value)}
                          inputLabel="Prepared for people with physical disabilities"/>
            </RegisterSection>
            <PustePole20px/>
            <RegisterSection>
                <Checkbox onCheckCallback={(value) => setAnimalsAllowed(value)}
                          inputLabel="Accept pets"/>
            </RegisterSection>
            <PustePole20px/>
            <RegisterSection>
                <Checkbox onCheckCallback={(value) => setSmokingAllowed(value)}
                          inputLabel="Smoking allowed"/>
            </RegisterSection>
            <PustePole20px/>
            <RegisterSection>
                <InputFormFilled value={street} onChange={(e) => {
                    setStreet(e.target.value);
                }} inputLabel="Street:" type="text"/>
            </RegisterSection>
            <PustePole20px/>
            <RegisterSection>
                <InputFormFilled value={houseNumber} onChange={(e) => {
                    setHouseNumber(e.target.value);
                }} inputLabel="House No." type="text"/>
            </RegisterSection>
            <PustePole20px/>
            <RegisterSection>
                <InputFormFilled value={postalCode} onChange={(e) => {
                    setPostalCode(e.target.value);
                }} inputLabel="Post code:" type="text"/>
            </RegisterSection>
            <PustePole20px/>
            <RegisterSection>
                <InputFormFilled value={city} onChange={(e) => {
                    setCity(e.target.value);
                }} inputLabel="City:" type="text"/>
            </RegisterSection>
            <PustePole20px/>
            <RegisterSection>
                <AppButton onClick={handleProceedButton}>
                    Proceed
                </AppButton>
            </RegisterSection>
        </RegisterBody>

    )
}

export default RegisterVolunteerFurtherForm;