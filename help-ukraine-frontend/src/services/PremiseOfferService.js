import axios from "axios";
import AuthService from "./AuthService";

const API_URL = "/api/";

class PremiseOfferService {

    getAuthHeader() {
        const accessToken = AuthService.getAccessToken();
        return {
            headers: {'Authorization': 'Bearer ' + accessToken},
        }
    }

    createPremiseOffer(premiseOfferData) {
        const options = this.getAuthHeader();
        return axios.post(API_URL + "premise-offers", premiseOfferData, options).then(res => res.data);
    }

    modifyPremiseOffer(premiseOfferData) {
        const options = this.getAuthHeader();
        return axios.put(API_URL + "premise-offers/" + premiseOfferData.id, premiseOfferData, options).then(res => res.data);
    }

    fetchPremiseOffers() {
        const options = this.getAuthHeader();
        return axios.get(API_URL + "premise-offers", options).then(res => res.data);
    }

    fetchPremiseOffersByHostId(hostId) {
        const options = this.getAuthHeader();
        return axios.get(API_URL + "premise-offers?hostId=" + hostId, options).then(res => res.data);
    }
}

export default new PremiseOfferService();