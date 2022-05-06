import axios from "axios";
import qs from "qs";

const API_URL = "http://localhost:8080/api/";


class AuthService {
    doLogin(username, password) {
        const data = {'password': password, 'username': username};
        const options = {
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        };
        return axios.post(API_URL + 'auth/login', qs.stringify(data), options).then(res => res.data);
    }

    doRegister(userData) {
        const options = {
            headers: {
                'Content-Type': 'application/json;charset=UTF-8',
                'Accept': 'application/json;charset=UTF-8'
            },
        };
        return axios.post(API_URL + 'user', userData, options).then(res => res.data);
    }

    fetchCurrentUser(username, token) {
        const options = {
            method: 'GET',
            headers: {'Authorization': 'Bearer ' + token},
            url: API_URL + 'user?email=' + username
        }
        return axios(options).then(res => res.data);
    }

    async login(username, password) {
        const loginResponse = await this.doLogin(username, password);
        const userResponse = await this.fetchCurrentUser(username, loginResponse.access_token)
        const decodedToken = this.parseJwt(loginResponse.access_token);
        localStorage.setItem("user", JSON.stringify(userResponse));
        localStorage.setItem("token_decoded", JSON.stringify(decodedToken));
        return userResponse;
    }

    async register(userData) {
        await this.doRegister(userData);
        await this.login(userData.email, userData.password);
    }

    logout() {
        localStorage.clear();
    }

    getCurrentUser() {
        return JSON.parse(localStorage.getItem('user'));
    }

    parseJwt(token) {
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        const jsonPayload = decodeURIComponent(atob(base64).split('').map(function (c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        }).join(''));
        return JSON.parse(jsonPayload);
    }
}

export default new AuthService();