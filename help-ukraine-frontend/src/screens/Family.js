import FamilyMembers from "../components/Family/FamilyMembers";
import addIcon from "../assets/add-icon.png"
import Modal from "../components/Common/Modal";
import AddNewMember from "../components/Family/AddNewMember";
import { isVisible } from "@testing-library/user-event/dist/utils";
import { useState } from "react";

const Family = () => {

    const members = [
        {
            name: "Kasia",
            surname: "Nowak"
        },
        {
            name: "Kamil",
            surname: "Kowalski"
        },
        {
            name: "Ania",
            surname: "Klops"
        }
    ]

    const [addNewMemberModalVisibility, setAddNewMemberModalVisibility] = useState(false);

    const onModalClose = () => {
        setAddNewMemberModalVisibility(false);
    }

    return (
    <>
        <h1 className="familyTitle">Family members</h1>
        <FamilyMembers members={members}/>
        <div 
            className="addFamilyMember" 
            onClick={() => setAddNewMemberModalVisibility((addNewMemberModalVisibility) => !addNewMemberModalVisibility)}
        >
            <img src={addIcon}></img>
        </div>
        <Modal isVisible={addNewMemberModalVisibility} onClose={() => onModalClose()}>
            <AddNewMember/>
        </Modal>
    </>
    );
}

export default Family;