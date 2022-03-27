import React from 'react';
import styled from 'styled-components';

const VolunteerBody = styled.div`
    height: 100vh;
    background-color: var(--ukrainski-niebieski);
    color: white;
    font-size:20px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;`;

const Volunteer = () => {
    return (
        <VolunteerBody>
            Ekran pomogającego
        </VolunteerBody>
    )
}
export default Volunteer;