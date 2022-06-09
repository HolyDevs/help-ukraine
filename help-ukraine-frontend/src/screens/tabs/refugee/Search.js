import SearchList from "../../../components/Search/SearchList";
import {useEffect, useState} from "react";
import PremiseOfferService from "../../../services/PremiseOfferService";
import AcceptedService from "../../../services/AcceptedService";
import ContactDataService from "../../../services/ContactDataService";

const Search = () => {

    const [results, setResults] = useState([]);
    const [acceptedContactData, setAcceptedContactData] = useState(null);

    const fetchData = async () => {
        const accepted = await AcceptedService.fetchAcceptedForCurrentSearchingOffer();
        if (!accepted) {
            await filterOffers();
            return;
        }
        await fetchAcceptedPremiseOffer(accepted.premiseOfferId);
    }

    const filterOffers = async () => {
        const res = await PremiseOfferService.filterPremiseOffersByCurrentSearchingOffer();
        setAcceptedContactData(null);
        setResults(res);
    }

    const fetchAcceptedPremiseOffer = async (acceptedPremiseOfferId) => {
        const premiseOffer = await PremiseOfferService.getPremiseOfferById(acceptedPremiseOfferId);
        const contactData = await ContactDataService.getContactDataByUserId(premiseOffer.hostId);
        setAcceptedContactData(contactData);
        setResults([premiseOffer]);
    }

    useEffect(() => {
        fetchData().catch(error => {
            console.error(error);
            window.alert("Something went wrong - cannot fetch offers")
        });
    }, []);

    return (
        <div className="search">
            {!acceptedContactData && <h1 className="search__title">We have found you some potential homes.</h1>}
            {acceptedContactData && <h1 className="search__title">Here is your new home.</h1>}
            {results && <SearchList acceptedContactData={acceptedContactData} results={results}/>}
        </div>
    );
}

export default Search;