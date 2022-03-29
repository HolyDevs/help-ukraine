import React from 'react';
import styled from 'styled-components';
import Card from '../style-generics/Card'
import {Link} from "react-router-dom";

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
            <Link to="/refugee">
                <RolePickerButton>
                    I need help
                </RolePickerButton>
            </Link>
            <RolePickerButtonDistancer>
            </RolePickerButtonDistancer>
            <Link to="/volunteer">
                <RolePickerButton className="card-1">
                    I can help
                </RolePickerButton>
            </Link>
        </RolePickerBody>
    );
}
export default RolePicker;
