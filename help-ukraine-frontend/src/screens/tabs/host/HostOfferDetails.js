import {useLocation, useNavigate} from "react-router";

import {HostDetailsBody} from "../../../components/styled-components/Screens";
import {AppSection, PustePole20px, TextSection} from "../../../components/styled-components/Sections";
import {Checkbox, Dropdown, InputFormFilled, TextareaContent} from "../../../components/widgets/Inputs";
import AppButton from "../../../components/styled-components/AppButton";
import React, {useState} from "react";
import ValidationService from "../../../services/ValidationService";
import PremiseOfferService from "../../../services/PremiseOfferService";
import AuthService from "../../../services/AuthService";

const HostOfferDetails = () => {

    const navigate = useNavigate();
    let {state} = useLocation();
    let offerDetails = state?.details;

    const [peopleToTake, setPeopleToTake] = useState(1);
    const [bathrooms, setBathrooms] = useState(1);
    const [kitchens, setKitchens] = useState(1);
    const [bedrooms, setBedrooms] = useState(1);
    const [wheelchairFriendly, setWheelchairFriendly] = useState(false);
    const [animalsAllowed, setAnimalsAllowed] = useState(false);
    const [smokingAllowed, setSmokingAllowed] = useState(false);
    const [active, setActive] = useState(true);
    const [description, setDescription] = useState("");
    const [city, setCity] = useState("");
    const [houseNumber, setHouseNumber] = useState("");
    const [street, setStreet] = useState("");
    const [postalCode, setPostalCode] = useState("");
    const [fromDate, setFromDate] = useState();
    const [toDate, setToDate] = useState();

    const fillPremiseOfferWithData = (premiseOffer) => {
        premiseOffer.peopleToTake = peopleToTake;
        premiseOffer.bathrooms = bathrooms;
        premiseOffer.bedrooms= bedrooms;
        premiseOffer.kitchens = kitchens;
        premiseOffer.animalsAllowed = animalsAllowed;
        premiseOffer.city = city;
        premiseOffer.street = street;
        premiseOffer.postalCode = postalCode;
        premiseOffer.houseNumber = houseNumber;
        premiseOffer.description = description;
        premiseOffer.fromDate = fromDate;
        premiseOffer.toDate = toDate;
        premiseOffer.active = active;
        premiseOffer.wheelchairFriendly = wheelchairFriendly;
        premiseOffer.smokingAllowed = smokingAllowed;
    }

    const buildModifiedPremiseOfferData = () => {
        const updatedPremiseOfferData = Object.assign({}, offerDetails);
        fillPremiseOfferWithData(updatedPremiseOfferData);
        return updatedPremiseOfferData;
    }

    const buildNewPremiseOfferData = () => {
        const premiseOffer = {
            hostId: AuthService.getCurrentUser().id,
            verified: true,
            offerImagesLocations: ["https://upload.wikimedia.org/wikipedia/commons/thumb/5/5e/Happy_family_%281%29.jpg/1200px-Happy_family_%281%29.jpg?20120321172928"]
        }
        fillPremiseOfferWithData(premiseOffer);
        return premiseOffer;
    }

    const rebuildDataForms = () => {
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
        setToDate(offerDetails.toDate);
        setActive(offerDetails.active)
        setWheelchairFriendly(offerDetails.wheelchairFriendly);
        setSmokingAllowed(offerDetails.smokingAllowed);
    }

    const handleSaveButton = () => {
        if (!validateInputs()) {
            return;
        }
        if (!offerDetails) {
            createPremiseOffer()
            return;
        }
        updatePremiseOffer();
    }

    const createPremiseOffer = () => {
        const newPremiseOffer = buildNewPremiseOfferData();
        PremiseOfferService.createPremiseOffer(newPremiseOffer).then(res => {
            navigate("/host/offers")
        }).catch(error => {
            window.alert("Offer creation failed: " + error.response?.data);
        })
    }

    const updatePremiseOffer = () => {
        const updatedOfferData = buildModifiedPremiseOfferData();
        PremiseOfferService.modifyPremiseOffer(updatedOfferData).then(res => {
            offerDetails = res;
            rebuildDataForms(offerDetails);
            alert("Saved!");
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
       if (!offerDetails) {
           return;
       }
       PremiseOfferService.getPremiseOfferById(offerDetails.id).then(
           (res) => {
               offerDetails = res;
               rebuildDataForms();
           });
    }, []);


    return (
    <div className="details">
        {offerDetails && <img src={offerDetails.offerImagesLocations[0]} />}
        {!offerDetails && <img src={"https://upload.wikimedia.org/wikipedia/commons/thumb/5/5e/Happy_family_%281%29.jpg/1200px-Happy_family_%281%29.jpg?20120321172928"} />}
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