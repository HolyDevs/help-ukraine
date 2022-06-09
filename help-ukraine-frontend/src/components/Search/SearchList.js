import SearchListItem from "./SearchListItem";

const SearchList = ({ acceptedContactData, results }) => {

    const generateResults = () => {
        return results.map((result, index) => <SearchListItem acceptedContactData = {acceptedContactData} key={index} result={result} />);
    }

    return (
        <ul>
            {generateResults()}
        </ul>
    );
}

export default SearchList;