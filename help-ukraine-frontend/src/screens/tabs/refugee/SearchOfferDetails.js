import {useLocation} from "react-router";
import numOfPeopleIcon from "../../../assets/tag-num-of-people-icon.png";
import locationIcon from "../../../assets/tag-location-icon.png";
import dateFromIcon from "../../../assets/tag-date-icon.png";
import dateToIcon from "../../../assets/tag-date-icon.png";
import numOfBedroomsIcon from "../../../assets/tag-num-of-bedrooms-icon.png";
import numOfBathroomsIcon from "../../../assets/tag-num-of-bathrooms-icon.png";
import numOfKitchensIcon from "../../../assets/tag-num-of-kitchens-icon.png";
import animalsAllowedIcon from "../../../assets/tag-animals-allowed-icon.png";
import wheelchairFriendlyIcon from "../../../assets/tag-wheelchair-friendly-icon.png";
import smokingAllowedIcon from "../../../assets/tag-smoking-allowed-icon.png";
import Tag from "../../../components/Search/Tag";
import Button from "../../../components/Common/Button";
import MailService from "../../../services/MailService";
import PendingService from "../../../services/PendingService";

const SearchOfferDetails = () => {

    const {state} = useLocation();
    const {details} = state;
    const {acceptedContactData} = state;

    const sendMailAndCreatePending = async () => {
        await PendingService.createPendingForCurrentSearchingOffer(details.id);
        await MailService.sendHelpRequest(details.id);
    }

    const handleMakeContact = () => {
        sendMailAndCreatePending().then(() => {
            window.alert("Your contact request was sent!");
        }).catch(err => {
            handleErrorResponse(err);
        });
    }

    const handleErrorResponse = (err) => {
        if (!err.response) {
            console.error(err);
            return;
        }
        if (err.response.status === 409) {
            window.alert("Your contact request to this offer was already sent!");
            return;
        }
        window.alert("Something went wrong - cannot send request");
    }

    const generateTags = () => {
        console.log(details);
        return (
            <>
                <Tag icon={numOfPeopleIcon} tagName={details.peopleToTake + ""}/>
                <Tag icon={locationIcon} tagName={details.city}/>
                <Tag icon={dateFromIcon} tagName={"from: " + details.fromDate}/>
                <Tag icon={dateToIcon} tagName={"to: " + details.toDate}/>
                <Tag icon={numOfBedroomsIcon} tagName={"bedrooms: " + details.bedrooms + ""}/>
                <Tag icon={numOfBathroomsIcon} tagName={"bathrooms: " + details.bathrooms + ""}/>
                <Tag icon={numOfKitchensIcon} tagName={"kitchens: " + details.kitchens + ""}/>
                {details.animalsAllowed && <Tag icon={animalsAllowedIcon} tagName={"Pet friendly"}/>}
                {details.wheelchairFriendly && <Tag icon={wheelchairFriendlyIcon} tagName={"Wheelchair adjusted"}/>}
                {details.smokingAllowed && <Tag icon={smokingAllowedIcon} tagName={"Smoking allowed"}/>}
            </>
        );
    }

    return (
        <div className="details">
            <img src={details.offerImagesLocations[0]}/>
            <div className="details__content">
                {!acceptedContactData && <h2>{details.city + " - " + details.peopleToTake + " accommodation(s)"}</h2>}
                {acceptedContactData &&
                <h2>{details.city + ", " + details.street + " " + details.houseNumber + ", " + details.postalCode}</h2>}
                <div className="separator"/>
                <div className="details__content__tags">
                    {generateTags()}
                </div>
                <h3>About home</h3>
                <p>{details.description}</p>
                {acceptedContactData && <h3>Contact data</h3>}
                {acceptedContactData && <p>{acceptedContactData.name + " " + acceptedContactData.surname + "\n" +
                acceptedContactData.email + " " + acceptedContactData.phoneNumber}</p>}
                {!acceptedContactData && <Button onClick={handleMakeContact}>Make contact</Button>}
            </div>
        </div>
    );
}

export default SearchOfferDetails;