import SearchList from "../../../components/Search/SearchList";
import {useEffect, useState} from "react";
import PremiseOfferService from "../../../services/PremiseOfferService";

const Search = () => {

    const [results, setResults] = useState([]);
    useEffect(() => {
        PremiseOfferService.filterPremiseOffersByCurrentSearchingOffer()
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
            {results && <SearchList results={results}/>}
        </div>
    );
}

export default Search;