import FamilyMembers from "../../../components/Family/FamilyMembers";
import addIcon from "../../../assets/add-icon.png"
import Modal from "../../../components/Common/Modal";
import AddNewMember from "../../../components/Family/AddNewMember";
import { useState } from "react";

const Family = () => {

    const members = [
        {
            name: "Kasia",
            surname: "Nowak",
            birthDate: "1998-05-10",
            gender: "Female",
            movingIssues: true
        },
        {
            name: "Kamil",
            surname: "Kowalski",
            birthDate: "1998-05-12",
            gender: "Male",
            movingIssues: false
        },
        {
            name: "Ania",
            surname: "Klops",
            birthDate: "1998-05-14",
            gender: "Female",
            movingIssues: true
        }
    ]

    const [selectedFamilyMember, setSelectedFamilyMember] = useState(null);
    const [addNewMemberModalVisibility, setAddNewMemberModalVisibility] = useState(false);

    const onModalClose = () => {
        setAddNewMemberModalVisibility(false);
    }

    const onFamilyMemberClicked = (member) => {
        setSelectedFamilyMember(member);
        setAddNewMemberModalVisibility(true);
    }

    const onAddNewMemberClicked = () => {
        setSelectedFamilyMember(null);
        setAddNewMemberModalVisibility((addNewMemberModalVisibility) => !addNewMemberModalVisibility);
    }

    return (
    <div className="family">
        <h1 className="familyTitle">Family members</h1>
        <FamilyMembers members={members} onFamilyMemberClicked={onFamilyMemberClicked}/>
        <div 
            className="addFamilyMember" 
            onClick={() => onAddNewMemberClicked()}
        >
            <img src={addIcon}></img>
        </div>
        <Modal isVisible={addNewMemberModalVisibility} onClose={() => onModalClose()}>
            <AddNewMember member={selectedFamilyMember}/>
        </Modal>
    </div>
    );
}

export default Family;