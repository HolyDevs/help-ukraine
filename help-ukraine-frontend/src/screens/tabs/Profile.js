import {useEffect, useState} from "react";
import {PustePole20px} from "../../components/styled-components/Sections";
import Button from "../../components/Common/Button";
import AuthService from "../../services/AuthService";
import {useNavigate} from "react-router-dom";

const Profile = () => {

    let navigate = useNavigate();
    const [name, setName] = useState("");
    const [surname, setSurname] = useState("");
    const [email, setEmail] = useState("");
    const [accountType, setAccountType] = useState("");
    const [phone, setPhone] = useState("");
    const [sex, setSex] = useState("");
    const [birthDate, setBirthDate] = useState("");

    const logOut = () => {
        AuthService.logout();
        navigate("/");
    }

    useEffect(() => {
        const currentUser = JSON.parse(sessionStorage.getItem("user"));
        setName(currentUser.name);
        setSurname(currentUser.surname);
        setEmail(currentUser.email);
        setAccountType(currentUser.accountType);
        setPhone(currentUser.phone);
        setSex(currentUser.sex);
        setBirthDate(currentUser.birthDate.toString());
    }, []);

    return (
        <div className="profile">
            <h1>Profile</h1>
            <h2>{name + " " + surname}</h2>
            <PustePole20px/>
            <h3>{email}</h3>
            <PustePole20px/>
            <h3>{accountType}</h3>
            <PustePole20px/>
            <h3>{phone}</h3>
            <PustePole20px/>
            <h3>{sex}</h3>
            <PustePole20px/>
            <h3>{birthDate}</h3>
            <PustePole20px/>
            <Button onClick={logOut}>Logout</Button>
        </div>

    );
}

export default Profile;