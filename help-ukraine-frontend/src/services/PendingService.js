import AuthService from "./AuthService";
import axios from "axios";
import SearchingOfferService from "./SearchingOfferService";

const API_URL = "/api/";

class PendingService {

    async getAuthHeader() {
        const accessToken = await AuthService.getAccessToken();
        return {
            headers: {'Authorization': 'Bearer ' + accessToken},
        }
    }

    async createPendingForCurrentSearchingOffer(premiseOfferId) {
        const searchingOfferId = SearchingOfferService.getCurrentSearchingOffer().id;
        return await this.createPending(searchingOfferId, premiseOfferId);
    }

    async createPending(searchingOfferId, premiseOfferId) {
        const options = await this.getAuthHeader();
        const pendingData = {
            searchingOfferId: searchingOfferId,
            premiseOfferId: premiseOfferId
        }
        return axios.post(API_URL + "pending", pendingData, options).then(res => res.data);
    }

}
export default new PendingService();