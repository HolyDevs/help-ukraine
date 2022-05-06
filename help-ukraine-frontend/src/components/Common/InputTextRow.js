const InputTextRow = ({inputName, inputType, value, onChange, ...props}) => {
    
    return (
        <div className="inputTextRow">
            <div className="inputName"> {inputName} </div>
            <div className="spacer"></div>
            <input type={inputType} value={value} onChange={onChange} {...props}></input>
        </div>
    )
}

export default InputTextRow;