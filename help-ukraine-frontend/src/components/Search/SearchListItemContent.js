import personIcon from '../../assets/person-icon.png';
import placeIcon from '../../assets/place-icon.png';

const SearchListItemContent = ({ result }) => {
    return (
        <div className="searchListItemContent">
            <div className="searchListItemContent__date">{result.dateFrom}</div>
            <div className="searchListItemContent__date">{result.dateTo}</div>
            <div className="spacer"/>
            <div className="searchListItemContent__name">{result.name}</div>
            <div className="searchListItemContent__details">
                <img src={personIcon}/>
                <span>{result.numOfPeople}</span>
                <img src={placeIcon}/>
                <span>{result.location}</span>
            </div>
        </div>
    );
};

export default SearchListItemContent;