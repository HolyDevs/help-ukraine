import styled from "styled-components";
import React, {useState} from "react";

const Input = styled.input`
    box-sizing: border-box;
    width: 34vh;
    height: 40px;
    padding-top: 0px;
    padding-bottom: 10px;
    font-size: 26px;
    text-align: start;
    background: none;
    &:focus {
        border-width: 0 0 2px;
    }
    outline: 0;
    border-width: 0 0 1px;
    border-color: var(--bialy);
    caret-color: var(--bialy);
    color: var(--bialy);
`
const InputFilled = styled.input`
    padding: 0.4em 2em 0.4em 1em;
    box-sizing: border-box;
    width: 100%;
    height: 40px;
    font-size: 20px;
    font-weight: 400;
    text-align: start;
    background: none;
    &:focus {
        border-width: 3 3 3px;
        border-color:  var(--ukrainski-zolty);
    }
    outline: 0;
    border-color: var(--bialy);
    caret-color: var(--bialy);
    color: var(--ukrainski-niebieski);
    background-color: var(--bialy);
    border-radius: 16px;
`
const OutlineInputLabel = styled.div`
    color: white;
    font-size: 14px;
`
const FilledInputLabel = styled.div`
    color: white;
    font-size: 14px;
    padding-left: 20px;
    padding-bottom: 3px;
`
const BottomLabel = styled.div`
    font-size: 12px;
    text-align: right;
    font-weight: lighter;
`

const Link = styled.a`
    text-decoration: none;
    color: var(--ukrainski-szary);
`

const InputFormOutlined = (props) => {
    return (
        <div>
            <OutlineInputLabel>{props.inputLabel}</OutlineInputLabel>
            <Input value={props.value} onChange={props.onChange} type={props.type}>
            </Input>
            <BottomLabel><Link path={props.bottomLabelUrl}>{props.bottomLabel}</Link></BottomLabel>
        </div>
    )
}

const InputFormFilled = (props) => {
    return (
        <div>
            <FilledInputLabel>{props.inputLabel}</FilledInputLabel>
            <InputFilled value={props.value} onChange={props.onChange} type={props.type}>
            </InputFilled>
            <BottomLabel><Link path={props.bottomLabelUrl}>{props.bottomLabel}</Link></BottomLabel>
        </div>
    )
}

const DropDownContainer = styled("div")`
    width: 100%;
    margin: 0 auto;
    color: var(--ukrainski-niebieski);
`;

const DropDownList = styled("ul")`
    width: 38vh;
    padding: 0;
    margin: 0;
    padding-left: 1em;
    background: #ffffff;
    border: 2px solid #e5e5e5;
    box-sizing: border-box;
    color: var(--ukrainski-niebieski);
    font-size: 1.3rem;
    font-weight: 500;
    z-index: 1;
    position: absolute;
    margin-top: -16px;
    
`;
const ListItem = styled("li")`
    padding: 10px;
    width: 30vh;
    list-style: none;
    &:hover {
    background-color: #f1f1f1;
    }
`;
const DropDownHeaderLabel = styled("div")`
    width: 87%;
    font-size: 20px;
    font-weight: 400;
`;
const DropDownHeaderRow = styled("div")`
  margin-bottom: 0.8em;
   padding: 0.4em 2em 0.4em 1em;
   box-shadow: 0 2px 3px rgba(0, 0, 0, 0.15);
   font-weight: 500;
   font-size: 1.3rem;
   background: #ffffff;
   border-radius: 16px;
   display: flex;
   flex-direction: row;
`;
const DropDownHeaderButton = styled("div")`
   height: 25px;
   width: 10%;
   display: flex;
    ${({ rotate }) => rotate && `
    transform: rotate(180deg);
  `}
`;
const DropDownWrapper = styled("div")`
   width: 100%;
 `;
const Dropdown = (props) => {
    const [isOpen, setIsOpen] = useState(false);
    const [selectedOption, setSelectedOption] = useState(props.options[0]);

    const toggling = () => setIsOpen(!isOpen);

    const onOptionClicked = (e, option) => {
        setSelectedOption(option);
        setIsOpen(false);
        valueChangedCallback(option.value);
    };

    const valueChangedCallback = (value) => {
        if (!props.onChangeCallback) {
            return;
        }
        props.onChangeCallback(value);
    }

    return (
        <DropDownWrapper>
        <FilledInputLabel>{props.inputLabel}</FilledInputLabel>
        <DropDownContainer>
            <DropDownHeaderRow onClick={toggling}>
                <DropDownHeaderLabel>
                    {selectedOption.value}
                </DropDownHeaderLabel>
                <DropDownHeaderButton rotate={!isOpen}>
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 384 512"><path d="M192 384c-8.188 0-16.38-3.125-22.62-9.375l-160-160c-12.5-12.5-12.5-32.75 0-45.25s32.75-12.5 45.25 0L192 306.8l137.4-137.4c12.5-12.5 32.75-12.5 45.25 0s12.5 32.75 0 45.25l-160 160C208.4 380.9 200.2 384 192 384z"/></svg>
                </DropDownHeaderButton>
            </DropDownHeaderRow>

            {isOpen && (
                    <DropDownList>
                        {props.options.map(option => (
                            <ListItem onClick={(e) => onOptionClicked(e, option)} key={option.key}>
                                {option.value}
                            </ListItem>
                        ))}
                    </DropDownList>
            )}
        </DropDownContainer>
        </DropDownWrapper>
    )
}

const CheckboxDot = styled.div`
    height: 40px;
    width: 40px;
     display: flex;
    background-color: var(--bialy);
    border-radius: 16px;
     flex-direction: row;
    justify-content: center;
    align-items: center;
`;
const CheckboxDotUnchecked = styled.div`
    height: 30px;
    width: 30px;
     display: flex;
    background-color: var(--ukrainski-niebieski);
    border-radius: 16px;
     flex-direction: row;
    justify-content: center;
    align-items: center;
`;

const CheckboxSection = styled.div`
    display: flex;
    flex-direction: row;
    justify-content: start;
    align-items: center;
    font-size: 16px;
    font-weight: 200;
`
const ToggledMark = styled.div`
    background-color:  var(--ukrainski-zolty);
    height: 30px;
    width: 30px;
    border-radius: 16px;
`
const CheckboxLabel = styled.div`
     font-size: 16px;
     padding-left: 20px;
`

const Checkbox = (props) => {
    const [isChecked, setIsChecked] = useState(false);
    const toggling = (e) => {
        setIsChecked(!isChecked);
        valueChangedCallback(!isChecked);
    }

    const valueChangedCallback = (isChecked) => {
        if (!props.onCheckCallback) {
            return;
        }
        props.onCheckCallback(isChecked);
    }

    return (
        <CheckboxSection>
            <div>
                <CheckboxDot onClick={(e) => toggling(e)}>
                    {isChecked ? <ToggledMark/>  : <CheckboxDotUnchecked/>  }
                </CheckboxDot>
            </div>
            <CheckboxLabel>
                {props.inputLabel}
            </CheckboxLabel>

        </CheckboxSection>
    );
};

const TextareaContent = styled.textarea`
    background-color: white;
    width: 90%;
    height: 100px;
    border-radius: 10px;
    color: --var(ukrainski-niebieski);
    padding: 5%;
`;

export { InputFormOutlined, InputFormFilled, Dropdown, Checkbox, TextareaContent };
