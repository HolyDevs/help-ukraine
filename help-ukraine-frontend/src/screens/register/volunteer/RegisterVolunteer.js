import React, {useState} from 'react';
import {RegisterBody} from "../../../components/styled-components/Screens";
import {RegisterHeader} from "../../../components/styled-components/Headers";
import {PustePole20px, RegisterSection} from "../../../components/styled-components/Sections";
import {InputFormFilled} from "../../../components/widgets/Inputs";
import {Link} from "react-router-dom";
import AppButton from "../../../components/styled-components/AppButton";

const RegisterVolunteer = () => {

    const [state, setState] = useState({});

    const handleStateChanged = (event, key)  => {
        state[key] = event.target.value;
        setState(state);
    }

    return (
        <RegisterBody>
            <RegisterHeader>Enter form to continue.</RegisterHeader>
            <RegisterSection>
                <InputFormFilled value={state["name"]} onChange={(e) => {handleStateChanged(e, "name");}} inputLabel="Name:" type="text"/>
            </RegisterSection>
            <RegisterSection>
                <InputFormFilled value={state["surname"]} onChange={(e) => {handleStateChanged(e, "surname");}} inputLabel="Surname:" type="text"/>
            </RegisterSection>
            <RegisterSection>
                <InputFormFilled value={state["age"]} onChange={(e) => {handleStateChanged(e, "age");}} inputLabel="Age:" type="number"/>
            </RegisterSection>
            <RegisterSection>
                <InputFormFilled value={state["phone"]} onChange={(e) => {handleStateChanged(e, "phone");}} inputLabel="Phone:" type="tel"/>
            </RegisterSection>
            <RegisterSection>
                <InputFormFilled value={state["email"]} onChange={(e) => {handleStateChanged(e, "email");}} inputLabel="Email:" type="mail"/>
            </RegisterSection>
            <RegisterSection>
                <InputFormFilled value={state["password"]} onChange={(e) => {handleStateChanged(e, "password");}} inputLabel="Password:" type="password"/>
            </RegisterSection>
            <RegisterSection>
                <InputFormFilled value={state["confirmPassword"]} onChange={(e) => {handleStateChanged(e, "confirmPassword");}} inputLabel="Confirm password:" type="password"/>
            </RegisterSection>
            <PustePole20px/>
            <RegisterSection>
                <PustePole20px>
                </PustePole20px>
                <Link to="/registerVolunteer/volunteer-further-info">
                    <AppButton>Submit</AppButton>
                </Link>
            </RegisterSection>
        </RegisterBody>
    );
}
export default RegisterVolunteer;