import OfferListItem from "./OfferListItem";
import addIcon from "../../assets/add-icon.png";
import styled from "styled-components";
import {useNavigate} from "react-router-dom";

const AddIcon = styled.div`
    margin-top: 50px;
    display: flex;
    justify-content: center;
   `;

const IMG = styled.img`
   height: 60px;
   `;

const OfferList = ({ results }) => {

    let navigate = useNavigate();

    const handleAddButton = () => {
        navigate("/host/offers/create");
    }

    const generateResults = () => {
        return results.map((result, index) => <OfferListItem key={index} result={result} />);
    }

    return (
        <ul>
            {generateResults()}
            <AddIcon>
                <IMG onClick = {handleAddButton} src={addIcon}></IMG>
            </AddIcon>
        </ul>
    );
}

export default OfferList;