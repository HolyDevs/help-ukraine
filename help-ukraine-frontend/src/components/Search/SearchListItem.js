import SearchListItemContent from "./SearchListItemContent";
import SearchListItemImage from "./SearchListItemImage";

const SearchListItem = ({ result }) => {
    return (
        <li className="searchItem">
            <div className="searchItem__leftContent">
                <SearchListItemContent result={result} />
            </div>
            <div className="searchItem__rightContent">
                <SearchListItemImage src={result.imageUrl} />
            </div>
        </li>
    );
}

export default SearchListItem;