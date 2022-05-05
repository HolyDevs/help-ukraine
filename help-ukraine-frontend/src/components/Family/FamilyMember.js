import editIcon from "../../assets/edit-icon.png";

const FamilyMember = ({ member }) => {
    return (
        <li className="familyMember">
            <div className="memberDetails"> {member.name + " " + member.surname} </div>
            <div className="spacer"/>
            <div className="editIcon">
                <img src={editIcon}></img>
            </div>
        </li>
    )
}

export default FamilyMember