import axios from "axios";
import AuthService from "./AuthService";

const API_URL = "/api/";

class SearchingOfferService {

    async getAuthHeader() {
        const accessToken = await AuthService.getAccessToken();
        return {
            headers: {'Authorization': 'Bearer ' + accessToken},
        }
    }

    async createSearchingOffer(searchingOfferData) {
        const options = await this.getAuthHeader();
        return axios.post(API_URL + "searching-offers", searchingOfferData, options).then(res => res.data);
    }

    async modifySearchingOffer(searchingOfferData) {
        const options = await this.getAuthHeader();
        return axios.put(API_URL + "searching-offers/" + searchingOfferData.id, searchingOfferData, options).then(res => res.data);
    }

    async fetchSearchingOffers() {
        const options = await this.getAuthHeader();
        return axios.get(API_URL + "searching-offers", options).then(res => res.data);
    }

    async fetchSearchingOffersByRefugeeId(refugeeId) {
        const options = await this.getAuthHeader();
        return axios.get(API_URL + "searching-offers?refugeeId=" + refugeeId, options).then(res => res.data);
    }

    async fetchSearchingOfferByRefugeeId(refugeeId) {
        const options = await this.getAuthHeader();
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

    async getSearchingOfferById(id) {
        const options = await this.getAuthHeader();
        return axios.get(API_URL + "searching-offers/" + id, options).then(res => res.data);
    }

    hasSearchingOfferMovingIssues(searchingOfferData) {
        if (searchingOfferData.userMovingIssues) {
            return true;
        }
        return searchingOfferData.searchingPeople.some(p => p.movingIssues);
    }
}

export default new SearchingOfferService();