import FamilyMembers from "../../../components/Family/FamilyMembers";
import addIcon from "../../../assets/add-icon.png"
import Modal from "../../../components/Common/Modal";
import AddNewMember from "../../../components/Family/AddNewMember";
import React, { useState } from "react";
import {AppSection, PustePole20px} from "../../../components/styled-components/Sections";
import AppButton from "../../../components/styled-components/AppButton";
import {useNavigate} from "react-router-dom";

const RegisterRefugeeFamilyMembersForm = () => {

    let navigate = useNavigate();

    const [members, setMembers] = useState([]);

    const [selectedFamilyMember, setSelectedFamilyMember] = useState(null);
    const [addNewMemberModalVisibility, setAddNewMemberModalVisibility] = useState(false);

    React.useEffect(() => {
        const offerData = sessionStorage.getItem('searchingOfferToBeCreated');
        if (!offerData) {
            return;
        }
        const offer = JSON.parse(offerData);
        if (offer.searchingPeople.length === 0) {
            return;
        }
        setMembers(offer.searchingPeople);
    })

    const createNewSearchingOffer = () => {
        return {
            searchingPeople: members
        }
    }

    const handleSubmitButton = () => {
        const searchingOfferToBeCreated = createNewSearchingOffer();
        sessionStorage.setItem('searchingOfferToBeCreated', JSON.stringify(searchingOfferToBeCreated));
        setSelectedFamilyMember(null);
        setAddNewMemberModalVisibility(false);
        navigate("/registerRefugee/account-further-info");
    }

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

    const onFamilyMemberRemoved = (indexToRemove) => {
        const newMembersList = members.filter((element, index) => {
            return index !== indexToRemove
        });
        setMembers(newMembersList);
        setSelectedFamilyMember(null);
    }

    return (
        <div className="family">
            <h1 className="familyTitle">Do you have any family members?</h1>
            <FamilyMembers members={members} onFamilyMemberRemoved={onFamilyMemberRemoved} onFamilyMemberClicked={onFamilyMemberClicked}/>
            <div
                className="addFamilyMember"
                onClick={() => onAddNewMemberClicked()}
            >
                <img src={addIcon}></img>
            </div>
            <Modal isVisible={addNewMemberModalVisibility} onClose={onModalClose}>
                <AddNewMember member={selectedFamilyMember} membersList={members} onSave={onModalClose}/>
            </Modal>
            <AppSection>
                <PustePole20px>
                </PustePole20px>
                <AppButton onClick={handleSubmitButton}>Submit</AppButton>
            </AppSection>
        </div>
    );
}

export default RegisterRefugeeFamilyMembersForm;