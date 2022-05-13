const Tag = ({icon, tagName}) => {
    return (
        <div className="tag">
            <img src={icon} />
            <span>{tagName}</span>
        </div>
    );
}

export default Tag;