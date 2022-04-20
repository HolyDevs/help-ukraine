import React, {useState} from "react";
import styled from 'styled-components';
import {Link, useNavigate} from "react-router-dom";
import InputForm from "../components/InputForm";
import AppButton from "../components/AppButton";
import AuthService from "../services/AuthService";

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
    background-color: var(--ukrainski-niebieski);
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
    let navigate = useNavigate();
    const [login, setLogin] = useState("jan.lokalny@gmail.com");
    const [password, setPassword] = useState("aaa");
    const handleLoginChanged = (event)  => {
        setLogin(event.target.value);
    }
    const handlePasswordChanged = (event) => {
        setPassword(event.target.value);
    }
    function handleClick() {
        AuthService.login(login, password).then(
            (res) => {
                switch (res.role) {
                    case 'ROLE_REFUGEE':
                        navigate("/refugee");
                        break;
                    case 'VOLUNTEER':
                        navigate("/volunteer");
                        break;
                }
              window.location.reload();
              console.log(res);
            },
            error => {

            }
        );
    }

    return (
        <>
            <LoginLogoHeader>
                <Logo src={"./logo.png"}>
                </Logo>
            </LoginLogoHeader>
            <LoginBottom>
                <InputForm value={login} onChange={handleLoginChanged} inputLabel="Login" type="text"/>
                <InputForm value={password} onChange={handlePasswordChanged}
                           inputLabel="Password"
                           bottomLabel="Forgot your password?"
                           bottomLabelUrl={"/error"}
                           type="password"/>
                <AppButton onClick={handleClick}>Login</AppButton>
                <Link to="/rolePicker">
                    <AppButton>Register</AppButton>
                </Link>
            </LoginBottom>
        </>
    )
}

export default Login;