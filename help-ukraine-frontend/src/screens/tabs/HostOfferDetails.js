import { useLocation } from "react-router";

import {HostDetailsBody} from "../../components/styled-components/Screens";
import {AppSection, PustePole20px, TextSection} from "../../components/styled-components/Sections";
import {Checkbox, Dropdown, InputFormFilled, TextareaContent} from "../../components/widgets/Inputs";
import AppButton from "../../components/styled-components/AppButton";
import React, {useState} from "react";
import ValidationService from "../../services/ValidationService";
import PremiseOfferService from "../../services/PremiseOfferService";

const HostOfferDetails = () => {

    let {state} = useLocation();
    let {details} = state;
    let offerDetails = details;

    const [peopleToTake, setPeopleToTake] = useState(offerDetails.peopleToTake);
    const [bathrooms, setBathrooms] = useState(offerDetails.bathrooms);
    const [kitchens, setKitchens] = useState(offerDetails.kitchens);
    const [bedrooms, setBedrooms] = useState(offerDetails.bedrooms);
    const [wheelchairFriendly, setWheelchairFriendly] = useState(offerDetails.wheelchairFriendly);
    const [animalsAllowed, setAnimalsAllowed] = useState(offerDetails.animalsAllowed);
    const [smokingAllowed, setSmokingAllowed] = useState(offerDetails.smokingAllowed);
    const [description, setDescription] = useState(offerDetails.description);
    const [city, setCity] = useState(offerDetails.city);
    const [houseNumber, setHouseNumber] = useState(offerDetails.houseNumber);
    const [street, setStreet] = useState(offerDetails.street);
    const [postalCode, setPostalCode] = useState(offerDetails.postalCode);
    const [fromDate, setFromDate] = useState(offerDetails.fromDate);
    const [toDate, setToDate] = useState(offerDetails.toDate);
    const [active, setActive] = useState(offerDetails.active);

    const buildModifiedPremiseOfferData = () => {
        const updatedPremiseOfferData = Object.assign({}, offerDetails);
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
        updatedPremiseOfferData.active = active;
        updatedPremiseOfferData.wheelchairFriendly = wheelchairFriendly;
        updatedPremiseOfferData.smokingAllowed = smokingAllowed;
        return updatedPremiseOfferData;
    }

    const rebuildDataForms = () => {
        console.log(offerDetails);
        setPeopleToTake(offerDetails.peopleToTake);
        setBathrooms(offerDetails.bathrooms);
        setKitchens(offerDetails.kitchens);
        setBedrooms(offerDetails.bedrooms);
        setAnimalsAllowed(offerDetails.animalsAllowed);
        setCity(offerDetails.city);
        setStreet(offerDetails.street);
        setPostalCode(offerDetails.postalCode);
        setHouseNumber(offerDetails.houseNumber);
        setDescription(offerDetails.description);
        setFromDate(offerDetails.fromDate);
        setActive(offerDetails.active);
        setToDate(offerDetails.toDate);
        setWheelchairFriendly(offerDetails.wheelchairFriendly);
        setSmokingAllowed(offerDetails.smokingAllowed);

    }

    const handleSaveButton = () => {
        if (!validateInputs()) {
            return;
        }
        const updatedOfferData = buildModifiedPremiseOfferData();
        PremiseOfferService.modifyPremiseOffer(updatedOfferData).then(res => {
            offerDetails = res;
            rebuildDataForms(offerDetails);
        }).catch(error => {
            rebuildDataForms(offerDetails);
            window.alert("Offer edition failed: " + error.response?.data);
        })
    }

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


    React.useEffect(() => {
       PremiseOfferService.getPremiseOfferById(details.id).then(
           (res) => {
                 console.log('aaaa');   
                 console.log(res);
               offerDetails = res;
               rebuildDataForms();
           });
    }, []);


    return (
    <div className="details">
        <img src={offerDetails.offerImagesLocations[0]} />
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
                    initalValue={peopleToTake - 1}
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
                <Checkbox initialState={smokingAllowed}  onCheckCallback={(value) =>
                {setSmokingAllowed(value); console.log(value);}}
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
            <AppSection>
                <Checkbox initialState={active} onCheckCallback={(value) => setActive(value)}
                          inputLabel="Is active"/>
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