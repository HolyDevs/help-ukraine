import axios from "axios";
import AuthService from "./AuthService";
import SearchingOfferService from "./SearchingOfferService";

const API_URL = "/api/";

class PremiseOfferService {

    async getAuthHeader() {
        const accessToken = await AuthService.getAccessToken();
        return {
            headers: {'Authorization': 'Bearer ' + accessToken},
        }
    }

    async createPremiseOffer(premiseOfferData) {
        const options = await this.getAuthHeader();
        return axios.post(API_URL + "premise-offers", premiseOfferData, options).then(res => res.data);
    }

    async modifyPremiseOffer(premiseOfferData) {
        const options = await this.getAuthHeader();
        return axios.put(API_URL + "premise-offers/" + premiseOfferData.id, premiseOfferData, options).then(res => res.data);
    }

    async fetchPremiseOffers() {
        const options = await this.getAuthHeader();
        return axios.get(API_URL + "premise-offers", options).then(res => res.data);
    }

    async filterPremiseOffers(numberOfPeople, animalsInvolved, movingIssues) {
        const options = await this.getAuthHeader();
        return axios.get(API_URL
            + "premise-offers/search?numberOfPeople=" + numberOfPeople
            + "&animalsInvolved=" + animalsInvolved
            + "&movingIssues=" + movingIssues, options).then(res => res.data);
    }

    async filterPremiseOffersByCurrentSearchingOffer() {
        const searchingOffer = SearchingOfferService.getCurrentSearchingOffer();
        const numOfPeople = searchingOffer.searchingPeople.length + 1;
        const animalsInvolved = searchingOffer.animalsInvolved;
        const movingIssues = SearchingOfferService.hasSearchingOfferMovingIssues(searchingOffer);
        return await this.filterPremiseOffers(numOfPeople, animalsInvolved, movingIssues);
    }



    async fetchPremiseOffersByHostId(hostId) {
        const options = await this.getAuthHeader();
        return axios.get(API_URL + "premise-offers?hostId=" + hostId, options).then(res => res.data);
    }

    async getPremiseOfferById(id) {
        const options = await this.getAuthHeader();
        return axios.get(API_URL + "premise-offers/" + id, options).then(res => res.data);
    }
}

export default new PremiseOfferService();