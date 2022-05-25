import axios from "axios";
import AuthService from "./AuthService";

const API_URL = "/api/";

class SearchingOfferService {

    getAuthHeader() {
        const accessToken = AuthService.getAccessToken();
        return {
            headers: {'Authorization': 'Bearer ' + accessToken},
        }
    }

    createSearchingOffer(searchingOfferData) {
        const options = this.getAuthHeader();
        return axios.post(API_URL + "searching-offers", searchingOfferData, options).then(res => res.data);
    }

    modifySearchingOffer(searchingOfferData) {
        const options = this.getAuthHeader();
        return axios.put(API_URL + "searching-offers/" + searchingOfferData.id, searchingOfferData, options).then(res => res.data);
    }

    fetchSearchingOffers() {
        const options = this.getAuthHeader();
        return axios.get(API_URL + "searching-offers", options).then(res => res.data);
    }

    fetchSearchingOffersByRefugeeId(refugeeId) {
        const options = this.getAuthHeader();
        return axios.get(API_URL + "searching-offers?refugeeId=" + refugeeId, options).then(res => res.data);
    }

    fetchSearchingOfferByRefugeeId(refugeeId) {
        const options = this.getAuthHeader();
        return axios.get(API_URL + "searching-offers?refugeeId=" + refugeeId, options).then(res => {
            if (res.data.length > 0) {
                return res.data[0];
            }
            const errMsg = 'No searching offer associated with this user';
            console.error(errMsg);
            throw new Error(errMsg);
        });
    }

    async createCurrentSearchingOffer(searchingOfferData) {
        await this.createSearchingOffer(searchingOfferData);
        const fetchedSearchingOffer = await this.fetchSearchingOfferByRefugeeId(searchingOfferData.refugeeId);
        sessionStorage.setItem("searching-offer", JSON.stringify(fetchedSearchingOffer));
        return fetchedSearchingOffer;
    }

    async fetchCurrentSearchingOffer() {
        const refugeeId = AuthService.getCurrentUser().id;
        const searchingOffer = await this.fetchSearchingOfferByRefugeeId(refugeeId);
        sessionStorage.setItem("searching-offer", JSON.stringify(searchingOffer));
        return searchingOffer;
    }

    getCurrentSearchingOffer() {
        return JSON.parse(sessionStorage.getItem("searching-offer"));
    }

    async modifyCurrentSearchingOffer(searchingOfferData) {
        const response = await this.modifySearchingOffer(searchingOfferData)
        sessionStorage.setItem("searching-offer", JSON.stringify(response));
        return response;
    }

    getSearchingOfferById(id) {
        const options = this.getAuthHeader();
        return axios.get(API_URL + "searching-offers/" + id, options).then(res => res.data);
    }
}

export default new SearchingOfferService();