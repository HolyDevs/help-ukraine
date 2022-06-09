import {useNavigate} from "react-router";
import SearchListItemContent from "./SearchListItemContent";
import SearchListItemImage from "./SearchListItemImage";

const SearchListItem = ({ acceptedContactData, result }) => {

    const navigate = useNavigate();

    const onSearchItemClicked = () => {
        navigate("/refugee/search/" + result.id, { state: { details: result, acceptedContactData: acceptedContactData } });
    }

    return (
        <li className="searchItem" onClick={() => onSearchItemClicked()}>
            <div className="searchItem__leftContent">
                <SearchListItemContent acceptedContactData={acceptedContactData} result={result} />
            </div>
            <div className="searchItem__rightContent">
                <SearchListItemImage src={result.offerImagesLocations[0]} />
            </div>
        </li>
    );
}

export default SearchListItem;