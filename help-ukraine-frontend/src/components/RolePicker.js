import React from 'react';
import styled from 'styled-components';
import Card from './Card'

const RolePickerBody = styled.div`
    height: 100vh;
    background-color: var(--ukrainski-niebieski);
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;`;

const RolePickerButton = styled(Card)`
    height: 30vh;
    background: white;
    width: 30vh;
    color: var(--ukrainski-niebieski);
    border-radius: 5px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    font-size:24px;
    font-weight: 600;
    }`;
const RolePickerButtonDistancer = styled.div`
    background-color: var(--ukrainski-niebieski);
    height: 5vh;`;

const RolePicker = () => {
    return (
        <RolePickerBody>
            <RolePickerButton className="card card-1">
                I need help
            </RolePickerButton>
            <RolePickerButtonDistancer>
            </RolePickerButtonDistancer>
            <RolePickerButton className="card-1">
                I can help
            </RolePickerButton>
        </RolePickerBody>
    );
}
export default RolePicker;