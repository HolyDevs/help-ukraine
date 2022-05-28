import RequestService from "../../services/RequestService";

const RequestListItem = ({request, reloadRequests, premiseOfferId}) => {

    const getFeatures = (features) => {
        return (
            <><span>{features.sex}</span><span>{features.birthDate}</span><span>{features.movingIssues ? "Movement issues" : ""}</span></>
        );

    }

    const getIfPets = () => {
        return request.animalsInvolved ? <div className="request-list-item__contact-section__pets">Traveling with pets</div> : null;
    }

    const onAcceptClicked = () => {
        RequestService.acceptCandidate(request.searchingOfferId, premiseOfferId).then(
            () => {
                reloadRequests();
            }
        )
    }

    const onDeclineClicked = () => {
        RequestService.declineCandidate(request.searchingOfferId, premiseOfferId).then(
            () => {
                reloadRequests();
            }
        )
    }

    const getButtonOrContactSection = () => {

        if (request.chosen) return (
            <div className="request-list-item__contact-section">
                <div className="request-list-item__contact-section__phone">{request.phoneNumber}</div>
                <div className="request-list-item__contact-section__mail">{request.email}</div>
            </div>
        );


        return (
            <div className="request-list-item__button-section">
                <button className="request-list-item__button" onClick={onAcceptClicked}>Accept</button>
                <button className="request-list-item__button-decline" onClick={onDeclineClicked}>X</button>
            </div>
        );
    }

    const generatePeopleList = () => {
        return (
            <ul>
                <li>
                    <div className="request-list-item__name">{request.name + " " + request.surname}</div>
                    <div className="request-list-item__features">{getFeatures({sex: request.sex, birthDate: request.birthDate, movingIssues: request.movingIssues})}</div>
                </li>
                {request.searchingPeople.map(person => <li>
                    <div className="request-list-item__name">{person.name + " " + person.surname}</div>
                    <div className="request-list-item__features">{getFeatures({sex: person.sex, birthDate: person.birthDate, movingIssues: person.movingIssues})}</div>
                </li>)}
            </ul>
        );
    }

    return (
        <div className="request-list-item">
            {generatePeopleList()}
            <div className="request-list-item__lowersection">
                {getIfPets()}
                <div className="spacer"/>
                {getButtonOrContactSection()}
            </div>
        </div>
    );

}

export default RequestListItem;