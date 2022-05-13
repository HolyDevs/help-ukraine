import { useLocation } from "react-router";

import {HostDetailsBody} from "../../components/styled-components/Screens";
import {AppSection, PustePole20px, TextSection} from "../../components/styled-components/Sections";
import {Checkbox, Dropdown, InputFormFilled, TextareaContent} from "../../components/widgets/Inputs";
import AppButton from "../../components/styled-components/AppButton";
import React, {useState} from "react";
import ValidationService from "../../services/ValidationService";
import AuthService from "../../services/AuthService";
import PremiseOfferService from "../../services/PremiseOfferService";

const HostOfferDetails = () => {

    let {state} = useLocation();
    let {details} = state;

    const [peopleToTake, setPeopleToTake] = useState(details.peopleToTake);
    const [bathrooms, setBathrooms] = useState(details.bathrooms);
    const [kitchens, setKitchens] = useState(details.kitchens);
    const [bedrooms, setBedrooms] = useState(details.bedrooms);
    const [wheelchairFriendly, setWheelchairFriendly] = useState(details.wheelchairFriendly);
    const [animalsAllowed, setAnimalsAllowed] = useState(details.animalsAllowed);
    const [smokingAllowed, setSmokingAllowed] = useState(details.smokingAllowed);
    const [description, setDescription] = useState(details.description);
    const [city, setCity] = useState(details.city);
    const [houseNumber, setHouseNumber] = useState(details.houseNumber);
    const [street, setStreet] = useState(details.street);
    const [postalCode, setPostalCode] = useState(details.postalCode);
    const [fromDate, setFromDate] = useState(details.fromDate);
    const [toDate, setToDate] = useState(details.toDate);

    const buildModifiedPremiseOfferData = () => {
        const updatedPremiseOfferData = Object.assign({}, details);
        updatedPremiseOfferData.peopleToTake = peopleToTake;
        updatedPremiseOfferData.bathrooms = bathrooms;
        updatedPremiseOfferData.kitchens = kitchens;
        updatedPremiseOfferData.animalsAllowed = animalsAllowed;
        updatedPremiseOfferData.city = city;
        updatedPremiseOfferData.street = street;
        updatedPremiseOfferData.postalCode = postalCode;
        updatedPremiseOfferData.houseNumber = houseNumber;
        updatedPremiseOfferData.description = description;
        updatedPremiseOfferData.fromDate = fromDate;
        updatedPremiseOfferData.toDate = toDate;
        return updatedPremiseOfferData;
    }

    const rebuildDataForms = () => {
        setPeopleToTake(details.peopleToTake);
        setBathrooms(details.bathrooms);
        setKitchens(details.kitchens);
        setBedrooms(details.bedrooms);
        setAnimalsAllowed(details.animalsAllowed);
        setCity(details.city);
        setStreet(details.street);
        setPostalCode(details.postalCode);
        setHouseNumber(details.houseNumber);
        setDescription(details.description);
        setFromDate(details.fromDate);
        setToDate(details.toDate);
    }

    const handleSaveButton = () => {
        if (!validateInputs()) {
            return;
        }
        const updatedOfferData = buildModifiedPremiseOfferData();
        PremiseOfferService.modifyPremiseOffer(updatedOfferData).then(res => {
            details = res;
            rebuildDataForms(details);
        }).catch(error => {
            rebuildDataForms(details);
            window.alert("Offer edition failed: " + error.response?.data);
        })
    }

    const validateInputs = () => {
        if (!ValidationService.areFromToDatesValid(fromDate, toDate)) {
            window.alert("Chosen date is invalid");
            return false;
        }
        const stringForms = [street, city, houseNumber, postalCode, description];
        if (!ValidationService.areStringsValid(stringForms)) {
            window.alert("Text input cannot be empty");
            return false;
        }
        return true;
    }

    return (
    <div className="details">
        <img src={details.offerImagesLocations[0]} />
        <HostDetailsBody>
            <AppSection>
                <InputFormFilled value={city} onChange={(e) => {
                    setCity(e.target.value);
                }} inputLabel="City:" type="text"/>
            </AppSection>
            <AppSection>
                <InputFormFilled value={fromDate} onChange={(e) => {
                    setFromDate(e.target.value);
                }} inputLabel="Offer active from:" type="date"/>
            </AppSection>
            <PustePole20px/>
            <AppSection>
                <InputFormFilled value={toDate} onChange={(e) => {
                    setToDate(e.target.value);
                }} inputLabel="Offer active to:" type="date"/>
            </AppSection>
            <PustePole20px/>
            <AppSection>
                <Dropdown
                    initalValue={peopleToTake}
                    inputLabel="Number of residents:"
                          onChangeCallback={(value) => setPeopleToTake(value.value)}
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
            </AppSection>
            <PustePole20px/>
            <AppSection>
                <Dropdown
                         initalValue={bathrooms}
                          inputLabel="Number of bathrooms:"
                          onChangeCallback={(value) => setBathrooms(value.value)}
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
            </AppSection>
            <PustePole20px/>
            <AppSection>
                <Dropdown
                    initalValue={kitchens}
                    inputLabel="Number of kitchens:"
                          onChangeCallback={(value) => setKitchens(value.value)}
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
            </AppSection>
            <PustePole20px/>
            <AppSection>
                <Dropdown
                    initalValue={bedrooms}
                          inputLabel="Number of bedrooms:"
                          onChangeCallback={(value) => setBedrooms(value.value)}
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
            </AppSection>
            <PustePole20px/>
            <TextSection>
                Additional information about equipment for the apartment
            </TextSection>
            <PustePole20px/>
            <AppSection>
                <TextareaContent value={description}
                                 onChange={(e) => setDescription(e.target.value)}/>
            </AppSection>
            <PustePole20px/>
            <AppSection>
                <Checkbox initialState={wheelchairFriendly} onCheckCallback={(value) => setWheelchairFriendly(value)}
                          inputLabel="Prepared for people with physical disabilities"/>
            </AppSection>
            <PustePole20px/>
            <AppSection>
                <Checkbox initialState={animalsAllowed}  onCheckCallback={(value) => setAnimalsAllowed(value)}
                          inputLabel="Accept pets"/>
            </AppSection>
            <PustePole20px/>
            <AppSection>
                <Checkbox initialState={smokingAllowed}  onCheckCallback={(value) => setSmokingAllowed(value)}
                          inputLabel="Smoking allowed"/>
            </AppSection>
            <PustePole20px/>
            <AppSection>
                <InputFormFilled value={street} onChange={(e) => {
                    setStreet(e.target.value);
                }} inputLabel="Street:" type="text"/>
            </AppSection>
            <PustePole20px/>
            <AppSection>
                <InputFormFilled value={houseNumber} onChange={(e) => {
                    setHouseNumber(e.target.value);
                }} inputLabel="House No." type="text"/>
            </AppSection>
            <PustePole20px/>
            <AppSection>
                <InputFormFilled value={postalCode} onChange={(e) => {
                    setPostalCode(e.target.value);
                }} inputLabel="Post code:" type="text"/>
            </AppSection>
            <PustePole20px/>

            <PustePole20px/>
            <AppSection>
                <AppButton onClick={handleSaveButton}>
                    Save
                </AppButton>
            </AppSection>
        </HostDetailsBody>
    </div>
    );
}

export default HostOfferDetails;