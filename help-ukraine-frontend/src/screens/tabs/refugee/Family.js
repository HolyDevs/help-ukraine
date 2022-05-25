import FamilyMembers from "../../../components/Family/FamilyMembers";
import addIcon from "../../../assets/add-icon.png"
import Modal from "../../../components/Common/Modal";
import AddNewMember from "../../../components/Family/AddNewMember";
import React, {useState} from "react";
import SearchingOfferService from "../../../services/SearchingOfferService";

const Family = () => {

    const [members, setMembers] = useState(() => {
        const searchingOffer = SearchingOfferService.getCurrentSearchingOffer();
        return searchingOffer.searchingPeople;
    })

    const [selectedFamilyMember, setSelectedFamilyMember] = useState(null);
    const [addNewMemberModalVisibility, setAddNewMemberModalVisibility] = useState(false);

    const buildModifiedSearchingOfferData = (searchingOfferData, membersList) => {
        const updatedSearchingOfferData = Object.assign({}, searchingOfferData);
        updatedSearchingOfferData.searchingPeople = membersList;
        return updatedSearchingOfferData;
    }

    const modifyFamily = (membersList) => {
        const searchingOffer = SearchingOfferService.getCurrentSearchingOffer();
        const updatedSearchingOffer = buildModifiedSearchingOfferData(searchingOffer, membersList);
        SearchingOfferService.modifyCurrentSearchingOffer(updatedSearchingOffer).then(res => {
            setMembers(res.searchingPeople);
            hideModal();
        }).catch(error => {
            setMembers(searchingOffer.searchingPeople)
            hideModal();
            window.alert("Family edition failed: " + error.response?.data);
        })
    }

    const hideModal = () => {
        setSelectedFamilyMember(null);
        setAddNewMemberModalVisibility(false);
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
        modifyFamily(newMembersList);
    }

    return (
    <div className="family">
        <h1 className="familyTitle">Family members</h1>
        <FamilyMembers members={members} onFamilyMemberClicked={onFamilyMemberClicked} onFamilyMemberRemoved={onFamilyMemberRemoved}/>
        <div 
            className="addFamilyMember" 
            onClick={() => onAddNewMemberClicked()}
        >
            <img src={addIcon}></img>
        </div>
        <Modal isVisible={addNewMemberModalVisibility} onClose={() => onModalClose()}>
            <AddNewMember member={selectedFamilyMember} membersList={members} onSave={modifyFamily}/>
        </Modal>
    </div>
    );
}

export default Family;