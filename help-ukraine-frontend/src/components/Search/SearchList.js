import SearchListItem from "./SearchListItem";

const SearchList = ({ results }) => {

    const generateResults = () => {
        return results.map((result, index) => <SearchListItem key={index} result={result} />);
    }

    return (
        <ul>
            {generateResults()}
        </ul>
    );
}

export default SearchList;