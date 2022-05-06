import React, {useState} from "react";
import {RegisterBody} from "../../../components/styled-components/Screens";
import {RegisterHeader} from "../../../components/styled-components/Headers";
import {
    PustePole20px,
    RegisterSection,
    TextSection
} from "../../../components/styled-components/Sections";
import {Checkbox, Dropdown, InputFormFilled, TextareaContent} from "../../../components/widgets/Inputs";
import {Link} from "react-router-dom";
import AppButton from "../../../components/styled-components/AppButton";

const RegisterVolunteerFurtherForm = () => {

    const [state, setState] = useState({});

    const handleStateChanged = (event, key)  => {
        state[key] = event.target.value;
        setState(state);
    }

    return (
        <RegisterBody>
            <RegisterHeader>
                We need some information about your house.
            </RegisterHeader>
            <RegisterSection>
                <Dropdown inputLabel="Number of residents:"
                    // options={["male", "female"]}
                          options={[
                              { key: "1", value: "1" },
                              { key: "2", value: "2" },
                              { key: "3", value: "3" },
                              { key: "4", value: "4" },
                              { key: "5", value: "5" },
                              { key: "6", value: "6" },
                              { key: "7", value: "7" },
                              { key: "8", value: "8" },
                              { key: "9", value: "9" },
                              { key: "10", value: "10" }
                          ]}/>
            </RegisterSection>
            <RegisterSection>
                <InputFormFilled value={state["phone"]} onChange={(e) => {handleStateChanged(e, "phone");}} inputLabel="Phone number:" type="tel"/>
            </RegisterSection>
            <PustePole20px/>
            <TextSection>
                Additional information about equipment for the apartment
            </TextSection>
            <RegisterSection>
                <TextareaContent/>
            </RegisterSection>
            <PustePole20px/>
            <RegisterSection>
                <Checkbox inputLabel="Prepared for people with phisical disabilities"/>
            </RegisterSection>
            <RegisterSection>
                <Checkbox inputLabel="Accept pets."/>
            </RegisterSection>
            <RegisterSection>
                <InputFormFilled value={state["street"]} onChange={(e) => {handleStateChanged(e, "name");}} inputLabel="Street:" type="text"/>
            </RegisterSection>
            <RegisterSection>
                <InputFormFilled value={state["houseNumber"]} onChange={(e) => {handleStateChanged(e, "name");}} inputLabel="House No." type="text"/>
            </RegisterSection>
            <RegisterSection>
                <InputFormFilled value={state["postcode"]} onChange={(e) => {handleStateChanged(e, "name");}} inputLabel="Post code:." type="text"/>
            </RegisterSection>
            <RegisterSection>
                <InputFormFilled value={state["city"]} onChange={(e) => {handleStateChanged(e, "name");}} inputLabel="City:." type="text"/>
            </RegisterSection>
            <PustePole20px/>
            <RegisterSection>
                <Link to="/volunteer/">
                    <AppButton>
                        Proceed
                    </AppButton>
                </Link>
            </RegisterSection>
        </RegisterBody>

    )
}

export default RegisterVolunteerFurtherForm;