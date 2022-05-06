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
    const [state, setState] = useState({});

    const handleStateChanged = (event, key) => {
        state[key] = event.target.value;
        setState(state);
    }

    const registerUserAndCreateNewPremiseOffer = async () => {
        const userToBeRegistered = JSON.parse(sessionStorage.getItem('userToBeRegistered'));
        const registerRes = await AuthService.register(userToBeRegistered);
        const premiseOffer = createNewPremiseOffer(registerRes.id);
        await PremiseOfferService.createPremiseOffer(premiseOffer);
    }

    const createNewPremiseOffer = (hostId) => {
        const address = parseAddress();
        return {
            hostId: hostId,
            active: true,
            peopleToTake: state["peopleToTake"].value,
            animalsAllowed: state["animalsAllowed"],
            wheelchairFriendly: state["wheelchairFriendly"],
            premiseAddress: address,
            description: state["description"]
        }
    }

    const parseAddress = () => {
        return state["city"] + ", " + state["street"] + " "
            + state["houseNumber"] + ", " + state["postcode"];
    }

    // temporary alert-based error handling
    // todo: create proper error info
    const handleProceedButton = () => {
        const stringForms = [state["street"], state["city"], state["houseNumber"],
            state["postcode"], state["description"]]
        if (!ValidationService.areStringsValid(stringForms)) {
            window.alert("Text input cannot be empty");
            return;
        }
        registerUserAndCreateNewPremiseOffer().then(() => {
            sessionStorage.removeItem('userToBeRegistered');
            navigate("/volunteer");
        }).catch((error) => {
            window.alert("Registration failed: " + error.response.data);
        });
    }

    return (
        <RegisterBody>
            <RegisterHeader>
                We need some information about your house.
            </RegisterHeader>
            <RegisterSection>
                <Dropdown inputLabel="Number of residents:"
                          value={state["peopleToTake"] = {value: "1"}}
                          onChange={(e) => handleStateChanged(e, 'peopleToTake')}
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
            <RegisterSection>
                <TextareaContent value={state["description"]}
                                 onChange={(e) => handleStateChanged(e, 'description')}/>
            </RegisterSection>
            <PustePole20px/>
            <RegisterSection>
                <Checkbox defaultChecked={state["wheelchairFriendly"]}
                          onChange={(e) => handleStateChanged(e, 'wheelchairFriendly')}
                          inputLabel="Prepared for people with physical disabilities"/>
            </RegisterSection>
            <RegisterSection>
                <Checkbox defaultChecked={state["animalsAllowed"]}
                          onChange={(e) => handleStateChanged(e, 'animalsAllowed')}
                          inputLabel="Accept pets."/>
            </RegisterSection>
            <RegisterSection>
                <InputFormFilled value={state["street"]} onChange={(e) => {
                    handleStateChanged(e, "street");
                }} inputLabel="Street:" type="text"/>
            </RegisterSection>
            <RegisterSection>
                <InputFormFilled value={state["houseNumber"]} onChange={(e) => {
                    handleStateChanged(e, "houseNumber");
                }} inputLabel="House No." type="text"/>
            </RegisterSection>
            <RegisterSection>
                <InputFormFilled value={state["postcode"]} onChange={(e) => {
                    handleStateChanged(e, "postcode");
                }} inputLabel="Post code:" type="text"/>
            </RegisterSection>
            <RegisterSection>
                <InputFormFilled value={state["city"]} onChange={(e) => {
                    handleStateChanged(e, "city");
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