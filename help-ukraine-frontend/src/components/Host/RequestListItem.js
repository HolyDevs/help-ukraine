const RequestListItem = props => {

    const getFeatures = () => {
        return (
            <><span>Male</span><span>1997-01-01</span><span>Movement issues</span></>
        );

    }

    const getIfPets = () => {
        return <div className="request-list-item__contact-section__pets">Traveling with pets</div>
    }

    const getButtonOrContactSection = () => {

        if (false) return (
            <div className="request-list-item__contact-section">
                <div className="request-list-item__contact-section__phone">500 100 100</div>
                <div className="request-list-item__contact-section__mail">exemplary.mail@gmail.com</div>
            </div>
        );


        return (
            <div className="request-list-item__button-section">
                <button className="request-list-item__button">Accept</button>
                <button className="request-list-item__button-decline">X</button>
            </div>
        );
    }

    const generatePeopleList = () => {
        return (
            <ul>
                <li>
                    <div className="request-list-item__name">Kamil Michalski</div>
                    <div className="request-list-item__features">{getFeatures()}</div>
                </li>
                <li>
                    <div className="request-list-item__name">Kamila Michalska</div>
                    <div className="request-list-item__features">{getFeatures()}</div>
                </li>
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