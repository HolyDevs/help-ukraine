import { useLocation } from "react-router";

const Details = () => {

    const {state} = useLocation();
    const {details} = state;

    return (
    <div className="details">
        <img src={details.imageUrl} />
        <div className="details__content">
            <h2>{details.name}</h2>
            <div className="separator"/>
        </div>
    </div>
    );
}

export default Details;