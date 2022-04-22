import styled from "styled-components";
import React from "react";

const RegisterHeaderWrapper = styled.div`

`;
const RegisterHeaderContent = styled.div`
    font-size: 26px;
    width: 36vh;  
    margin-bottom: 30px`;

export const RegisterHeader = ({children}) => {
    return (
        <RegisterHeaderWrapper>
            <RegisterHeaderContent>
                {children}
            </RegisterHeaderContent>
        </RegisterHeaderWrapper>
    )
}