import OfferList from "../../../components/Common/OfferList/OfferList";
import {useEffect, useState} from "react";
import PremiseOfferService from "../../../services/PremiseOfferService";

const Offers = () => {

    const [results, setResults] = useState([]);
    useEffect(() => {
        const currentUser = JSON.parse(sessionStorage.getItem("user"));
        PremiseOfferService.fetchPremiseOffersByHostId(currentUser.id)
            .then(res => {
                setResults(res);
            })
            .catch(error => {
                window.alert("Something went wrong - cannot fetch offers")
            })
    }, []);

    return (
        <div className="search">
            <h1 className="search__title">Your offers.</h1>
            {results && <OfferList detailsUrl={"/host/offers"} results={results}/>}
        </div>
    );
}

export default Offers;