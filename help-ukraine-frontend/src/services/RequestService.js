import axios from "axios";
import AuthService from "./AuthService";

const API_URL = "/api/";

class RequestService {

    async getAuthHeader() {
        const accessToken = await AuthService.getAccessToken();
        return {
            headers: {'Authorization': 'Bearer ' + accessToken},
        }
    }

    async fetchCandidatesByPremiseOfferId(premiseOfferId) {
        const options = await this.getAuthHeader();
        return axios.get(API_URL + "candidate/" + premiseOfferId, options).then(res => res.data);
    }

    async acceptCandidate(searchingOfferId, premiseOfferId) {
        const options = await this.getAuthHeader();
        return axios.post(API_URL + "accepted", {searchingOfferId, premiseOfferId}, options).then(res => res.data);
    }

    async declineCandidate(searchingOfferId, premiseOfferId) {
        const options = await this.getAuthHeader();
        return axios.delete(API_URL + "pending/" + searchingOfferId + "/" + premiseOfferId, options).then(res => res.data);
    }
}

export default new RequestService();