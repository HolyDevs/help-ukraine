import RequestListItem from "./RequestListItem";

const RequestList = () => {

    const generateList = () => {
        return (
            <ul>
                <RequestListItem/>
                <RequestListItem/>
            </ul>
        );
    }

    return (
        <div className="request-list">
            <h3>Wrocław - Akacjowa</h3>
            {generateList()}
        </div>
    )
}

export default RequestList;