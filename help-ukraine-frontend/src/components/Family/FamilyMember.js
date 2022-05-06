import editIcon from "../../assets/edit-icon.png";

const FamilyMember = ({ member, onClick }) => {


    return (
        <li className="familyMember">
            <div className="memberDetails"> {member.name + " " + member.surname} </div>
            <div className="spacer"/>
            <div className="editIcon">
                <img onClick={onClick} src={editIcon}></img>
            </div>

        </li>
    )
}

export default FamilyMember