import axios from "axios";
import AuthService from "./AuthService";

const API_URL = "/api/";

class PremiseOfferService {
    createPremiseOffer(premiseOfferData) {
        const accessToken = AuthService.getAccessToken();
        const options = {
            headers: {'Authorization': 'Bearer ' + accessToken},
        }
        return  axios.post(API_URL + "premise-offers", premiseOfferData, options).then(res => res.data);
    }
}

export default new PremiseOfferService();