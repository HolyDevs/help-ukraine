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
    const [filter, setFilter] = useState("All");

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
            <h1 className="search__title">
                <HeaderWrapper>
                    Your offers.
                </HeaderWrapper>
                <FilterWrapper>
                    <Dropdown
                        onChangeCallback={(value) => {setFilter(value.value);
                            console.log(value.value);}}
                        initalValue="All"
                        inputLabel="Number of residents:"
                        // onChangeCallback={(value) => setPeopleToTake(value)}
                        options={[
                            {key: "1", value: "All"},
                            {key: "2", value: "Waiting"},
                            {key: "3", value: "Closed"}
                        ]}/>
                </FilterWrapper>
            </h1>
            {results && <OfferList results={results}/>}
        </div>
    );
}

export default Offers;