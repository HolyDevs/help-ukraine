import personIcon from '../../assets/person-icon.png';
import placeIcon from '../../assets/place-icon.png';

const SearchListItemContent = ({acceptedContactData, result}) => {
    return (
        <div className="searchListItemContent">
            <div className="searchListItemContent__date">{result.fromDate}</div>
            <div className="searchListItemContent__date">{result.toDate}</div>
            <div className="spacer"/>
            {!acceptedContactData && <div
                className="searchListItemContent__name">{result.city + " - " + result.peopleToTake + " accommodation(s)"}</div>}
            {acceptedContactData && <div className="searchListItemContent__name">{acceptedContactData.email}</div>}
            {acceptedContactData && <div className="searchListItemContent__name">{acceptedContactData.phoneNumber}</div>}
            {acceptedContactData && <div
                className="searchListItemContent__name">{result.city + ", " + result.street + " " + result.houseNumber + ", " + result.postalCode
            }</div>}
            <div className="searchListItemContent__details">
                <img src={personIcon}/>
                <span>{result.peopleToTake}</span>
                <img src={placeIcon}/>
                <span>{result.city}</span>
            </div>
        </div>
    );
};

export default SearchListItemContent;