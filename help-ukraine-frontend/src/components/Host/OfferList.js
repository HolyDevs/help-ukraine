import OfferListItem from "./OfferListItem";
import addIcon from "../../assets/add-icon.png";
import styled from "styled-components";

const AddIcon = styled.div`
    margin-top: 50px;
    display: flex;
    justify-content: center;
   `;

const IMG = styled.img`
   height: 60px;
   `;

const OfferList = ({ results }) => {

    const generateResults = () => {
        return results.map((result, index) => <OfferListItem key={index} result={result} />);
    }

    return (
        <ul>
            {generateResults()}
            <AddIcon>
                <IMG src={addIcon}></IMG>
            </AddIcon>
        </ul>
    );
}

export default OfferList;