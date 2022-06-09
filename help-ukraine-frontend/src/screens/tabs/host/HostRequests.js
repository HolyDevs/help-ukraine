import { useEffect, useState } from "react";
import RequestList from "../../../components/Host/RequestList";
import PremiseOfferService from "../../../services/PremiseOfferService";
import "../../../styles/Main.scss"

const HostRequests = () => {

    const [homes, setHomes] = useState([]);

    useEffect(() => {
        reloadRequests();
    }, []);

    const reloadRequests = () => {
        const currentUser = JSON.parse(sessionStorage.getItem("user"));
        PremiseOfferService.fetchPremiseOffersByHostId(currentUser.id)
            .then(res => {
                setHomes(
                    res.map(home => { return { id: home.id, city: home.city, street: home.street, active: home.active, houseNumber: home.houseNumber}})
                );
            })
            .catch(error => {
                window.alert("Something went wrong - cannot fetch offers")
            })
    }

    const getRequestLists = () => {
        return (
            <ul>
                {homes.map(home => <RequestList key={home.id} reloadRequests={reloadRequests} home={home}/>)}
            </ul>
        );
    }

    return (
        <div className="requests">
            <h1>Candidates</h1>
            {getRequestLists()}
        </div>
    );
}

export default HostRequests;