import personIcon from '../../assets/person-icon.png';
import placeIcon from '../../assets/place-icon.png';
import lock_open from '../../assets/lock_open.png'
import lock_closed from '../../assets/lock_closed.png'
import styled from "styled-components";

const Image = styled.img`
    display: flex;
    flex-flow: row nowrap;
    align-items: center;`;

const ImageWrapper = styled.div`
    flex-direction: row;
    display: flex;
    span,
    img {
        color: var(--ukrainski-szary-ciemny);
    }
    img {
        height: 16px;
        margin-right: 5px;
}`;



const OfferListItemContentDate = styled.div`
    color: var(--ukrainski-szary);
`;

const OfferListItemContentName = styled.div`
   flex: 1;
   font-size: 18px;
   font-weight: bolder;
   display: flex;
   flex-direction: row;
`;
const Column = styled.div`
   flex: 1;
   display: flex;
   flex-direction: column;
`;
const ImageWrapperRow = styled.div`
   display: flex;
   flex-direction: row;
   padding-left: 5px;
`;
const Panel = styled.div`
   flex: 1;
   width: 100%;
   display: flex;
   flex-direction: row;
`;
const OfferListItemContent = ({ result }) => {
    console.log(result);
    return (
        <>
            <OfferListItemContentName>{result.city + " - " + result.peopleToTake + " accommodation(s)"}</OfferListItemContentName>
            <Panel>
                <ImageWrapper>
                    <ImageWrapperRow>
                        <Image src={personIcon}/>
                        <span>{result.peopleToTake}</span>
                    </ImageWrapperRow>
                    <ImageWrapperRow>
                        <Image src={placeIcon}/>
                        <span>{result.city}</span>
                    </ImageWrapperRow>
                    <ImageWrapperRow>
                        <Image src={result.active ? lock_open : lock_closed}/>
                        <span>{result.active ? "Active" : "Not active" }</span>
                    </ImageWrapperRow>
                </ImageWrapper>
            </Panel>
            <Column>
                <OfferListItemContentDate><b>Opens: </b>{result.fromDate}</OfferListItemContentDate>
                <OfferListItemContentDate><b>Closes: </b>{result.toDate}</OfferListItemContentDate>
            </Column>
        </>
    );
};

export default OfferListItemContent;