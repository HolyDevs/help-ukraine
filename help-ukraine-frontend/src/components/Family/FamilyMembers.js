import FamilyMember from "./FamilyMember"

const FamilyMembers = ({ members, onFamilyMemberClicked }) => {

    const getMembers = () => {
        return members.map((member, index) => <FamilyMember key={index} member={member} onClick={() => onFamilyMemberClicked(member)}/>);
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