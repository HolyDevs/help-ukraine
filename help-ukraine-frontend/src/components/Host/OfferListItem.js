import { useNavigate } from "react-router";
import OfferListItemContent from "./OfferListItemContent";
import OfferListItemImage from "./OfferListItemImage";
import styled from "styled-components";

const OfferItem = styled.li`
    display: flex;
    box-shadow: 0px 0px 4px 0px rgba(66, 68, 90, 0.25);
    border-radius: 10px;
    margin-top: 13px;
    padding: 5px;`;
const OfferItemLeftContent = styled.div`
    font-size: 14px;
    flex: 4 1 auto;
    display: flex;
    flex-direction: column;
    justify-content: start;
    align-items: start;`;

const OfferItemRightContent = styled.div`
    flex: 1 1 auto;
    display: flex;
    justify-content: center;
    align-items: center;`;

const OfferListItem = ({ result }) => {

    const navigate = useNavigate();
    const onSearchItemClicked = () => {
        navigate("/host/offers/" + result.id, { state: { details: result } });
    }

    return (
        <OfferItem onClick={() => onSearchItemClicked()}>
            <OfferItemLeftContent>
                <OfferListItemContent result={result} />
            </OfferItemLeftContent>
            <OfferItemRightContent>
                <OfferListItemImage src={result.offerImagesLocations[0]} />
            </OfferItemRightContent>
        </OfferItem>
    );
}

export default OfferListItem;