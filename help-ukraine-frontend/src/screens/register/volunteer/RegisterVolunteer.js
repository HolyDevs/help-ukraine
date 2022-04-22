import React from 'react';
import styled from 'styled-components';

const RegisterVolunteerBody = styled.div`
    height: 100vh;
    background-color: var(--ukrainski-niebieski);
    color: white;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;`;



const RegisterVolunteer = () => {
    return (
        <RegisterVolunteerBody>
            rejestracja wolonatriusza
        </RegisterVolunteerBody>
    )
}

export default RegisterVolunteer;