const Modal = ({ children, isVisible, onClose}) => {

    const setModalClassName = () => {
        return "modal" + (isVisible ? "" : "--hidden");
    }

    return (
        <div className={setModalClassName()}>
            <div className="backdrop" onClick={() => onClose()}/>
            <div className="content">{children}</div>
        </div>
    );
}

export default Modal;