import styled from "styled-components";
import React from "react";

const Input = styled.input`
    box-sizing: border-box;
    width: 34vh;
    height: 40px;
    padding-top: 0px;
    padding-bottom: 10px;
    font-size: 26px;
    text-align: center;
    background: none;
    &:focus {
        border-width: 0 0 2px;
    }
    outline: 0;
    //tutaj drobne uproszczenie wzgledem makiety
    border-width: 0 0 1px;
    border-color: var(--bialy);
    caret-color: var(--bialy);
    color: var(--bialy);
`
const InputLabel = styled.div`
    color: white;
    font-size: 14px;
`
const BottomLabel = styled.div`
    color: white;
    font-size: 14px;
    text-align: right;
    font-weight: lighter;
`
const Wrapper = styled.div`
    margin-bottom: 30px;
`
const Link = styled.a`
    text-decoration: none;
    color: white;
`

const InputForm = (props) => {
    return (
        <Wrapper>
            <InputLabel>{props.inputLabel}</InputLabel>
            <Input>
            </Input>
            <BottomLabel><Link href="https://www.youtube.com/watch?v=aHtEm9sxzYg">{props.bottomLabel}</Link></BottomLabel>
        </Wrapper>
    )
}

export default InputForm;