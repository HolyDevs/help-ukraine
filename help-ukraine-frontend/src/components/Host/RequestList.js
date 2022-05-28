import { useEffect, useState } from "react";
import RequestService from "../../services/RequestService";
import RequestListItem from "./RequestListItem";

const RequestList = ({home}) => {

    const [requests, setRequests] = useState([]);

    useEffect(() => {
        reloadRequests();
    }, []);

    const reloadRequests = () => {
        RequestService.fetchCandidatesByPremiseOfferId(home.id)
            .then(res => {
                console.log(res);
                setRequests(res);
            })
            .catch(error => {
                window.alert("Something went wrong - cannot fetch offers")
            })
    }

    const generateList = () => {
        return (
            <ul>
                {requests.map(request => <RequestListItem key={request.searchingOfferId} request={request} reloadRequests={reloadRequests} premiseOfferId={home.id}/>)}
            </ul>
        );
    }

    return (
        <div className="request-list">
            <h3>{home && home.city + " - " + home.street + " " + home.houseNumber}</h3>
            {generateList()}
        </div>
    )
}

export default RequestList;