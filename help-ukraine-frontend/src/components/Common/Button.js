const Button = ({ children, onClick, ...props }) => {
    const handleClick = () => {
        if (onClick) {
            onClick();
        }
    }
    return (
        <button className="std-button" onClick={handleClick} {...props}>
            {children}
        </button>
    )
}
export default Button;