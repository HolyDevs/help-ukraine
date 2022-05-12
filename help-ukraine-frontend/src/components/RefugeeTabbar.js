import "../styles/Main.scss";
import familyIcon from "../assets/family-icon.png";
import searchIcon from "../assets/search-icon.png";
import profileIcon from "../assets/profile-icon.png";
import { useState } from "react";
import { useNavigate } from "react-router";

const RefugeeTabbar = () => {

    const [activeTabIndex, setActiveTabIndex] = useState(1);
    const navigate = useNavigate();

    const getTabbarItemClass = (index) => {
        return "tabbar__item" + (activeTabIndex === index ? "--active" : "");
    }

    const onTabClicked = (index) => {
        setActiveTabIndex(index);
        pushSubroute(index);
    }

    const pushSubroute = (index) => {
        switch (index) {
            case 0:
                navigate("/refugee/family");
                break;
            case 1:
                navigate("/refugee/search");
                break;
            case 2:
                navigate("/refugee/profile");
                break;
        }
    }

    return (
        <div className="tabbar">
            <div className={getTabbarItemClass(0)} onClick={() => onTabClicked(0)}>
                <div className="tabbar__item__icon">
                    <img src={familyIcon}></img>
                </div>
            </div>
            <div className={getTabbarItemClass(1)} onClick={() => onTabClicked(1)}>
                <div className="tabbar__item__icon">
                    <img src={searchIcon}></img>
                </div>
            </div>
            <div className={getTabbarItemClass(2)} onClick={() => onTabClicked(2)}>
                <div className="tabbar__item__icon">
                    <img src={profileIcon}></img>
                </div>
            </div>
        </div>
    )
}

export default RefugeeTabbar;