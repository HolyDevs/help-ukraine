import axios from "axios";
import AuthService from "./AuthService";

const API_URL = "/api/";

class RequestService {

    getAuthHeader() {
        const accessToken = AuthService.getAccessToken();
        return {
            headers: {'Authorization': 'Bearer ' + accessToken},
        }
    }

    fetchCandidatesByPremiseOfferId(premiseOfferId) {
        const options = this.getAuthHeader();
        return axios.get(API_URL + "candidate/" + premiseOfferId, options).then(res => res.data);
    }

    acceptCandidate(searchingOfferId, premiseOfferId) {
        const options = this.getAuthHeader();
        return axios.post(API_URL + "accepted", {searchingOfferId, premiseOfferId}, options).then(res => res.data);
    }

    declineCandidate(searchingOfferId, premiseOfferId) {
        const options = this.getAuthHeader();
        return axios.post(API_URL + "pending", {searchingOfferId, premiseOfferId}, options).then(res => res.data);
    }
}

export default new RequestService();