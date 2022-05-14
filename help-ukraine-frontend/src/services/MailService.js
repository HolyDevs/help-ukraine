import axios from "axios";
import AuthService from "./AuthService";

const API_URL = "/api/";

class MailService {

    getAuthHeader() {
        const accessToken = AuthService.getAccessToken();
        return {
            headers: {'Authorization': 'Bearer ' + accessToken},
        }
    }

    sendHelpRequest(offerId) {
        const  refugeeId =  AuthService.getCurrentUser().id
        const options = this.getAuthHeader();
        return axios.post(
            API_URL + "mail/" + offerId + "/" + refugeeId,null, options).then(res => res.data);
    }
}

export default new MailService();