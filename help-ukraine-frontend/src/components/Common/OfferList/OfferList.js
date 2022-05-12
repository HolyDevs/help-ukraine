import OfferListItem from "./OfferListItem";

const OfferList = ({ results, detailsUrl }) => {

    const generateResults = () => {
        return results.map((result, index) => <OfferListItem key={index} result={result} detailsUrl={detailsUrl} />);
    }

    return (
        <ul>
            {generateResults()}
        </ul>
    );
}

export default OfferList;