import OfferList from "../../../components/Common/OfferList/OfferList";
import {useEffect, useState} from "react";
import PremiseOfferService from "../../../services/PremiseOfferService";

const Search = () => {

    const [results, setResults] = useState([]);
    useEffect(() => {
        PremiseOfferService.fetchPremiseOffers()
            .then(res => {
                setResults(res);
            })
            .catch(error => {
                window.alert("Something went wrong - cannot fetch offers")
            })
    }, []);

    return (
        <div className="search">
            <h1 className="search__title">We have found you some potential homes.</h1>
            {results && <OfferList detailsUrl={"/refugee/search"} results={results}/>}
        </div>
    );
}

export default Search;