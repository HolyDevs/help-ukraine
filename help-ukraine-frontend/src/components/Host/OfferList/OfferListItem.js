import { useNavigate } from "react-router";
import OfferListItemContent from "./OfferListItemContent";
import OfferListItemImage from "./OfferListItemImage";
import styled from "styled-components";

const SearchItem = styled.li`
    display: flex;
    box-shadow: 0px 0px 4px 0px rgba(66, 68, 90, 0.25);
    border-radius: 10px;
    margin-top: 13px;
    padding: 5px;`;
const SearchItemLeftContent = styled.div`
    font-size: 14px;
    flex: 4 1 auto;
    display: flex;
    flex-direction: column;
    justify-content: start;
    align-items: start;`;

const SearchItemRightContent = styled.div`
    flex: 1 1 auto;
    display: flex;
    justify-content: center;
    align-items: center;`;

const OfferListItem = ({ result, detailsUrl }) => {

    const navigate = useNavigate();
    const onSearchItemClicked = () => {
        navigate(detailsUrl + "/" + result.id, { state: { details: result } });
    }

    return (
        <SearchItem onClick={() => onSearchItemClicked()}>
            <SearchItemLeftContent>
                <OfferListItemContent result={result} />
            </SearchItemLeftContent>
            <SearchItemRightContent>
                <OfferListItemImage src={result.offerImagesLocations[0]} />
            </SearchItemRightContent>
        </SearchItem>
    );
}

export default OfferListItem;