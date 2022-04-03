import React from "react";
import styled from 'styled-components';
import {Link} from "react-router-dom";
import InputForm from "../components/InputForm";
import AppButton from "../components/AppButton";

const LoginLogoHeader = styled.div`
    height: 40vh;
    background-color: var(--bialy);
    color: white;
    font-size:20px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    padding:50px;`;


const LoginBottom = styled.div`
    margin-top: 20px;
    padding-top: 15px;
    height: 60vh;
    color: var(--ukrainski-bialy);
    background-color:  var(--ukrainski-niebieski);
    color: white;
    font-size:20px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;`;

const Logo = styled.img`
     background-size: 30vh;
     width: 35vh;
     height: 40vh;`;


const Login = () => {
    return (
        <>
            <LoginLogoHeader>
                <Logo src={"./logo.png"}>
                </Logo>
            </LoginLogoHeader>
            <LoginBottom>
                <InputForm inputLabel="Login" type="text"></InputForm>
                <InputForm inputLabel="Password" bottomLabel="Forgot your password?" type="password"></InputForm>
                <AppButton>Login</AppButton>
                <Link to="/rolePicker">
                    <AppButton>Register</AppButton>
                </Link>
            </LoginBottom>
        </>
    )
}

export default Login;