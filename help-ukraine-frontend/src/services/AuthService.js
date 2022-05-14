import axios from "axios";
import qs from "qs";

const API_URL = "/api/";

class AuthService {

    doLogin(username, password) {
        const data = {'password': password, 'username': username};
        const options = {
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        };
        return axios.post(API_URL + 'auth/login', qs.stringify(data), options).then(res => res.data);
    }

    doRefreshToken(refreshToken) {
        const options = {
            method: 'GET',
            headers: {'Authorization': 'Bearer ' + refreshToken},
            url: API_URL + 'auth/refresh'
        }
        return axios(options).then(res => res.data);
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

    doModify(username, userData, token) {
        const options = {
            headers: {
                'Content-Type': 'application/json;charset=UTF-8',
                'Accept': 'application/json;charset=UTF-8',
                'Authorization': 'Bearer ' + token
            },
        };
        return axios.put(API_URL + 'user?email=' + username, userData, options).then(res => res.data);
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
        sessionStorage.setItem("user", JSON.stringify(userResponse));
        this.fillTokenData(loginResponse);
        return userResponse;
    }

    fillTokenData(loginResponse) {
        const decodedAccessToken = this.parseJwt(loginResponse.access_token);
        sessionStorage.setItem("decoded_access_token", JSON.stringify(decodedAccessToken));
        sessionStorage.setItem("access_token", loginResponse.access_token);
        sessionStorage.setItem("refresh_token", loginResponse.refresh_token);
    }

    async register(userData) {
        await this.doRegister(userData);
        return await this.login(userData.email, userData.password);
    }

    async modifyCurrentUser(username, userData) {
        const token = this.getAccessToken();
        const userResponse = await this.doModify(username, userData, token);
        sessionStorage.setItem("user", JSON.stringify(userResponse));
        return userResponse;
    }

    logout() {
        sessionStorage.clear();
    }

    getCurrentUser() {
        return JSON.parse(sessionStorage.getItem('user'));
    }

    getAccessToken() {
        const decodedAccessToken = JSON.parse(sessionStorage.getItem("decoded_access_token"));
        const expiryTimestamp = decodedAccessToken.exp;
        if (new Date(expiryTimestamp * 1000) < Date.now()) {
            this.refreshToken();
            console.log('Refreshed user token')
        }

        return sessionStorage.getItem("access_token");
    }

    refreshToken() {
        const refreshToken = sessionStorage.getItem("refresh_token");
        this.doRefreshToken(refreshToken).then((res) => {
            this.fillTokenData(res);
        }).catch((error) => {
            this.logout();
            throw error;
        });
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