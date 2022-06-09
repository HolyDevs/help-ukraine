import OfferList from "../../../components/Host/OfferList";
import React, {useEffect, useState} from "react";
import PremiseOfferService from "../../../services/PremiseOfferService";
import {Dropdown} from "../../../components/widgets/Inputs";
import styled from "styled-components";

const FilterWrapper = styled.div`
   max-width: 300px;
   width: 100%;
}`;

const HeaderWrapper = styled.div`
   display: flex;
   justify-content: center;
   align-items: center;
}`;

const Offers = () => {

    const [results, setResults] = useState([]);
    const [displayedResults, setDisplayedResults] = useState([]);
    const [filter, setFilter] = useState("All");

    useEffect(() => {
        const currentUser = JSON.parse(sessionStorage.getItem("user"));
        PremiseOfferService.fetchPremiseOffersByHostId(currentUser.id)
            .then(res => {
                setResults(res);
                setDisplayedResults(res);
            })
            .catch(error => {
                window.alert("Something went wrong - cannot fetch offers")
            })
    }, []);

    const handleFilter = (label) => {
        switch (label.value) {
            case "Not active":
                setDisplayedResults(results.filter(e => !e.active));
                break;
            case "Active":
                setDisplayedResults(results.filter(e => e.active));
                break
            default:
                setDisplayedResults(results);
                break;
        }
        setFilter(label.value);
    }

    return (
        <div className="search">
            <h1 className="search__title">
                <HeaderWrapper>
                    Your offers.
                </HeaderWrapper>
                <FilterWrapper>
                    <Dropdown
                        onChangeCallback={(label) => handleFilter(label)}
                        initalValue={filter}
                        options={[
                            {key: "1", value: "All"},
                            {key: "2", value: "Active"},
                            {key: "3", value: "Not active"}
                        ]}/>
                </FilterWrapper>
            </h1>
            {displayedResults && <OfferList results={displayedResults}/>}
        </div>
    );
}

export default Offers;