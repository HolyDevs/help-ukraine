import React, {useState} from 'react';
import {Checkbox, Dropdown, InputFormFilled, TextareaContent} from "../../../components/widgets/Inputs";
import {Link} from "react-router-dom";
import AppButton from "../../../components/styled-components/AppButton";
import {
    PustePole20px,
    RegisterSection,
    TextSection
} from "../../../components/styled-components/Sections";
import {RegisterHeader} from "../../../components/styled-components/Headers";
import {RegisterBody} from "../../../components/styled-components/Screens";



const RegisterRefugeeFurtherForm = () => {

    const [state, setState] = useState({});

    const handleStateChanged = (event, key)  => {
        state[key] = event.target.value;
        setState(state);
    }

    return (
        <RegisterBody>
            <RegisterHeader>
                We need some further information.
            </RegisterHeader>
            <RegisterSection>
                <InputFormFilled value={state["dateOfBirth"]} onChange={(e) => {handleStateChanged(e, "dateOfBirth");}} inputLabel="Date of birth:" type="date"/>
            </RegisterSection>
            <RegisterSection>
                <Dropdown inputLabel="Sex:"
                    // options={["male", "female"]}
                          options={[
                              { key: "male", value: "Male" },
                              { key: "female", value: "Female" }
                          ]}/>
            </RegisterSection>
            <RegisterSection>
                <InputFormFilled value={state["phone"]} onChange={(e) => {handleStateChanged(e, "phone");}} inputLabel="Phone number:" type="tel"/>
            </RegisterSection>
            <PustePole20px/>
            <RegisterSection>
            <Checkbox inputLabel="I have a physical disability and require a wheelchair"/>
            </RegisterSection>
            <RegisterSection>
            <Checkbox inputLabel="I have a pet"/>
            </RegisterSection>
            <PustePole20px/>
            <TextSection>
               Additional information that might be important about you (allergies, diseases, requiring special treatment, etc.)
            </TextSection>
            <RegisterSection>
            <TextareaContent/>
            </RegisterSection>
            <RegisterSection>
                <Link to="/main/search">
                <AppButton>
                    Proceed
                </AppButton>
                </Link>
            </RegisterSection>
        </RegisterBody>

    )
}

export default RegisterRefugeeFurtherForm;