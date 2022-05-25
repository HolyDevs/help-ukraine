import editIcon from "../../assets/edit-icon.png";
import deleteIcon from "../../assets/delete-icon.png";

const FamilyMember = ({ member, onEditClick, onDeleteClick }) => {


    return (
        <li className="familyMember">
            <div className="memberDetails"> {member.name + " " + member.surname} </div>
            <div className="spacer"/>
            <></>
            <div className="deleteIcon">
                <img onClick={onDeleteClick} src={deleteIcon}></img>
            </div>
            <div className="editIcon">
                <img onClick={onEditClick} src={editIcon}></img>
            </div>

        </li>
    )
}

export default FamilyMember