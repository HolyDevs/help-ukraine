import axios from "axios";
import AuthService from "./AuthService";
import SearchingOfferService from "./SearchingOfferService";

const API_URL = "/api/";

class ContactDataService {

    async getAuthHeader() {
        const accessToken = await AuthService.getAccessToken();
        return {
            headers: {'Authorization': 'Bearer ' + accessToken},
        }
    }

    async getContactDataByUserId(userId) {
        const options = await this.getAuthHeader();
        return axios.get(API_URL + "contact-data/" + userId, options).then(res => res.data);
    }
}

export default new ContactDataService();