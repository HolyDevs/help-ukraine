import { useNavigate } from "react-router";
import OfferListItemContent from "./OfferListItemContent";
import OfferListItemImage from "./OfferListItemImage";

const OfferListItem = ({ result, detailsUrl }) => {

    const navigate = useNavigate();

    const onSearchItemClicked = () => {
        navigate(detailsUrl + "/" + result.id, { state: { details: result } });
    }

    return (
        <li className="searchItem" onClick={() => onSearchItemClicked()}>
            <div className="searchItem__leftContent">
                <OfferListItemContent result={result} />
            </div>
            <div className="searchItem__rightContent">
                <OfferListItemImage src={result.offerImagesLocations[0]} />
            </div>
        </li>
    );
}

export default OfferListItem;