import React from 'react';
import styled from 'styled-components';

const RefugeeBody = styled.div`
    height: 100vh;
    background-color: var(--ukrainski-niebieski);
    color: white;
    font-size:20px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;`;

const Refugee = () => {
    return (
        <RefugeeBody>
            Ekran uchodźcy
        </RefugeeBody>
    )
}

export default Refugee;