import React, {useState} from 'react';
import {InputFormFilled} from "../../../components/widgets/Inputs";
import {Link} from "react-router-dom";
import AppButton from "../../../components/styled-components/AppButton";
import {PustePoleJakLebBrzezinskiego20px, RegisterSection} from "../../../components/styled-components/Sections";
import {RegisterHeader} from "../../../components/styled-components/Headers";
import {RegisterBody} from "../../../components/styled-components/Screens";

const RegisterRefugeeAccountForm = () => {

    const [state, setState] = useState({});

    const handleStateChanged = (event, key)  => {
        state[key] = event.target.value;
        setState(state);
    }

    return (
        <RegisterBody>
            <RegisterHeader>Account creation.</RegisterHeader>
            <RegisterSection>
                <InputFormFilled value={state["email"]} onChange={(e) => {handleStateChanged(e, "email");}} inputLabel="Email:" type="text"/>
            </RegisterSection>
            <RegisterSection>
                <InputFormFilled value={state["password"]} onChange={(e) => {handleStateChanged(e, "password");}} inputLabel="Password:" type="password"/>
            </RegisterSection>
            <RegisterSection>
                <InputFormFilled value={state["confirmPassword"]} onChange={(e) => {handleStateChanged(e, "confirmPassword");}} inputLabel="Confirm password:" type="password"/>
            </RegisterSection>
            <PustePoleJakLebBrzezinskiego20px>

            </PustePoleJakLebBrzezinskiego20px>
            <RegisterSection>
                <Link to="/registerRefugee/account-basic-info">
                    <AppButton>Create an account</AppButton>
                </Link>
            </RegisterSection>
        </RegisterBody>

    )
}

export default RegisterRefugeeAccountForm;