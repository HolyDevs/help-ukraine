import FamilyMember from "./FamilyMember"

const FamilyMembers = ({ members, onFamilyMemberClicked, onFamilyMemberRemoved }) => {

    const getMembers = () => {
        return members.map((member, index) => <FamilyMember key={index} member={member} onDeleteClick={() => onFamilyMemberRemoved(index)} onEditClick={() => onFamilyMemberClicked(member)}/>);
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