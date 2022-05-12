import styled from "styled-components";

const ImageWrapper = styled.div`
    display: flex;
    flex-wrap: wrap;`;

const Image = styled.img`
    display: flex;
    flex-flow: row nowrap;
    align-items: center;
    max-width:100%;
    max-height:150px;`;
const OfferListItemImage = ({ src }) => {
    return (
        <ImageWrapper>
            <Image src={src}></Image>
        </ImageWrapper>
    );
}

export default OfferListItemImage;