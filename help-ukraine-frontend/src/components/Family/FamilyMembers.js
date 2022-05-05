import FamilyMember from "./FamilyMember"

const FamilyMembers = ({ members }) => {

    const getMembers = () => {
        return members.map((member, index) => <FamilyMember key={index} member={member}/>);
    }

    return (
        <div className="familyMembers">
            <ul>
                {getMembers()}
            </ul>
        </div>

    )
}

export default FamilyMembers