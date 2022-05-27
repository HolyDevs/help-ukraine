import RequestList from "../../../components/Host/RequestList";
import "../../../styles/Main.scss"

const HostRequests = () => {


    const getRequestLists = () => {
        return (
            <ul>
                <RequestList/>
                <RequestList/>
                <RequestList/>
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