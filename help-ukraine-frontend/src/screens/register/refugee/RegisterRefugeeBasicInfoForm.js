import React, {useState} from 'react';
import {InputFormFilled} from "../../../components/widgets/Inputs";
import {Link} from "react-router-dom";
import AppButton from "../../../components/styled-components/AppButton";
import {PustePoleJakLebBrzezinskiego20px, RegisterSection} from "../../../components/styled-components/Sections";
import {RegisterHeader} from "../../../components/styled-components/Headers";
import {RegisterBody} from "../../../components/styled-components/Screens";

const RegisterRefugeeBasicInfoForm = () => {

    const [state, setState] = useState({});

    const handleStateChanged = (event, key)  => {
        state[key] = event.target.value;
        setState(state);
    }

    return (
        <RegisterBody>
            <RegisterHeader>Tell us about your situation.</RegisterHeader>
            <RegisterSection>
                <InputFormFilled value={state["name"]} onChange={(e) => {handleStateChanged(e, "name");}} inputLabel="Name:" type="text"/>
            </RegisterSection>
            <RegisterSection>
                <InputFormFilled value={state["surname"]} onChange={(e) => {handleStateChanged(e, "surname");}} inputLabel="Surname:" type="text"/>
            </RegisterSection>
            <RegisterSection>
                <InputFormFilled value={state["adults"]} onChange={(e) => {handleStateChanged(e, "adults");}} inputLabel="Adults:" type="number"/>
            </RegisterSection>
            <RegisterSection>
                <InputFormFilled value={state["children"]} onChange={(e) => {handleStateChanged(e, "children");}} inputLabel="Children:" type="text"/>
            </RegisterSection>
            <RegisterSection>
            <PustePoleJakLebBrzezinskiego20px>

            </PustePoleJakLebBrzezinskiego20px>
            <Link to="/registerRefugee/account-further-info">
                <AppButton>Submit</AppButton>
            </Link>
            </RegisterSection>
        </RegisterBody>
    )
}

export default RegisterRefugeeBasicInfoForm;