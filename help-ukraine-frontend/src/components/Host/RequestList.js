import { useEffect, useState } from "react";
import RequestService from "../../services/RequestService";
import RequestListItem from "./RequestListItem";

const RequestList = ({home, reloadRequests}) => {

    const [requests, setRequests] = useState([]);

    useEffect(() => {
        RequestService.fetchCandidatesByPremiseOfferId(home.id)
            .then(res => {
                setRequests(res);
            })
            .catch(error => {
                window.alert("Something went wrong - cannot fetch offers")
            })
    }, [home]);

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