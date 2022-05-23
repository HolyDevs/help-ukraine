import React, {useState} from "react";
import styled from 'styled-components';
import {Link, useNavigate} from "react-router-dom";
import {InputFormOutlined} from "../../components/widgets/Inputs";
import AppButton from "../../components/styled-components/AppButton";
import AuthService from "../../services/AuthService";
import {AppSection} from "../../components/styled-components/Sections";
import LogoImg from "../../assets/logo.png";
import SearchingOfferService from "../../services/SearchingOfferService";

const LoginLogoHeader = styled.div`
    height: 20vh;
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
    padding-top: 30px;
    height: 80vh;
    color: var(--ukrainski-bialy);
    background-color: var(--ukrainski-niebieski);
    color: white;
    font-size:20px;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: center;`;

const Logo = styled.img`
     background-size: 30vh;
     width: 20vh;`;


const Section = styled.div`
     margin-bottom: 30px;`;


const Login = () => {
    let navigate = useNavigate();
    const [login, setLogin] = useState("jan.uciekinier@gmail.com");
    const [password, setPassword] = useState("aaa");

    const handleLoginChanged = (event)  => {
        setLogin(event.target.value);
    }

    const handlePasswordChanged = (event) => {
        setPassword(event.target.value);
    }

    const loginHost = () => {
        navigate("/host/offers");
    }

    const loginRefugee = () => {
        SearchingOfferService.fetchCurrentSearchingOffer().then(res => {
            navigate("/refugee/search");
        }).catch(err => {
            window.alert("Something went wrong - cannot fetch your data")
        })
    }

    function handleClick() {
        AuthService.login(login, password).then(
            (res) => {
                switch (res.accountType) {
                    case 'REFUGEE':
                        loginRefugee();
                        break;
                    case 'HOST':
                        loginHost();
                        break;
                }
            },
            error => {
                window.alert("Incorrect login or password");
            }
        );
    }

    return (
        <>
            <LoginLogoHeader>
                <Logo src={LogoImg}>
                </Logo>
            </LoginLogoHeader>
            <LoginBottom>
                <Section>
                    <InputFormOutlined value={login} onChange={handleLoginChanged} inputLabel="Login" type="text"/>
                </Section>
                <Section>
                    <InputFormOutlined value={password} onChange={handlePasswordChanged}
                                       inputLabel="Password"
                                       bottomLabel="Forgot your password?"
                                       bottomLabelUrl={"/error"}
                                       type="password"/>
                </Section>
                <AppSection>
                <AppButton onClick={handleClick}>Login</AppButton>
                </AppSection>
                <AppSection>
                <Link to="/rolePicker">
                <AppButton>Register</AppButton>
                </Link>
                </AppSection>
            </LoginBottom>
        </>
    )
}

export default Login;