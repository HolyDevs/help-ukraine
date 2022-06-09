import axios from "axios";
import AuthService from "./AuthService";

const API_URL = "/api/";

class MailService {

    async getAuthHeader() {
        const accessToken = await AuthService.getAccessToken();
        return {
            headers: {'Authorization': 'Bearer ' + accessToken},
        }
    }

    async sendHelpRequest(offerId) {
        const  refugeeId =  AuthService.getCurrentUser().id
        const options = await this.getAuthHeader();
        return axios.post(
            API_URL + "mail/pending/" + offerId + "/" + refugeeId,null, options).then(res => res.data);
    }

    async sendAcceptedRequest(offerId) {
        const  hostId =  AuthService.getCurrentUser().id
        const options = await this.getAuthHeader();
        return axios.post(
            API_URL + "mail/accepted/" + offerId + "/" + hostId,null, options).then(res => res.data);
    }

    async sendRejectedRequest(offerId) {
        const  hostId =  AuthService.getCurrentUser().id
        const options = await this.getAuthHeader();
        return axios.post(
            API_URL + "mail/rejected/" + offerId + "/" + hostId,null, options).then(res => res.data);
    }
}

export default new MailService();