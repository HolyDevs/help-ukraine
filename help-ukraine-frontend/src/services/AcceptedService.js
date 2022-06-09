import axios from "axios";
import AuthService from "./AuthService";
import SearchingOfferService from "./SearchingOfferService";

const API_URL = "/api/";

class AcceptedService {

    async getAuthHeader() {
        const accessToken = await AuthService.getAccessToken();
        return {
            headers: {'Authorization': 'Bearer ' + accessToken},
        }
    }

    async fetchAcceptedBySearchingOfferId(searchingOfferId) {
        const options = await this.getAuthHeader();
        return axios.get(API_URL + "accepted?searchingOfferId=" + searchingOfferId, options).then(res => {
            if (res.data.length > 0) {
                return res.data[0];
            }
            return null;
        });
    }

    async fetchAcceptedForCurrentSearchingOffer() {
        const searchingOffer = SearchingOfferService.getCurrentSearchingOffer();
        return await this.fetchAcceptedBySearchingOfferId(searchingOffer.id);
    }
}

export default new AcceptedService();