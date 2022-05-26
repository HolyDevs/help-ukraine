import AuthService from "./AuthService";
import axios from "axios";
import SearchingOfferService from "./SearchingOfferService";

const API_URL = "/api/";

class PendingService {

    getAuthHeader() {
        const accessToken = AuthService.getAccessToken();
        return {
            headers: {'Authorization': 'Bearer ' + accessToken},
        }
    }

    async createPendingForCurrentSearchingOffer(premiseOfferId) {
        const searchingOfferId = SearchingOfferService.getCurrentSearchingOffer().id;
        return await this.createPending(searchingOfferId, premiseOfferId);
    }

    createPending(searchingOfferId, premiseOfferId) {
        const options = this.getAuthHeader();
        const pendingData = {
            searchingOfferId: searchingOfferId,
            premiseOfferId: premiseOfferId
        }
        return axios.post(API_URL + "pending", pendingData, options).then(res => res.data);
    }

}
export default new PendingService();