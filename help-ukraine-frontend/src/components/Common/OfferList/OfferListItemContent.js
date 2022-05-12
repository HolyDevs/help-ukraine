import personIcon from '../../../assets/person-icon.png';
import placeIcon from '../../../assets/place-icon.png';

const OfferListItemContent = ({ result }) => {
    return (
        <div className="searchListItemContent">
            <div className="searchListItemContent__date">{result.fromDate}</div>
            <div className="searchListItemContent__date">{result.toDate}</div>
            <div className="spacer"/>
            <div className="searchListItemContent__name">{result.city + " - " + result.peopleToTake + " accommodation(s)"}</div>
            <div className="searchListItemContent__details">
                <img src={personIcon}/>
                <span>{result.peopleToTake}</span>
                <img src={placeIcon}/>
                <span>{result.city}</span>
            </div>
        </div>
    );
};

export default OfferListItemContent;