import axios from "axios";
import qs from "qs";
const API_URL = "http://localhost:8080/api/auth/";


class AuthService {
    doLogin(username, password) {
        const data = { 'password': password, 'username': username};
        const options = {
            method: 'POST',
            headers: { 'content-type': 'application/x-www-form-urlencoded' },
            data: qs.stringify(data),
            url: 'auth/login'
        };
        return axios(options).then(res => res.data);
    }
    async login(username, password) {
        const response = await this.doLogin(username, password);
        localStorage.setItem("user_response", JSON.stringify(response));
        localStorage.setItem("token_decoded", JSON.stringify(this.parseJwt(response.access_token)));
        return this.parseJwt(response.access_token);
    }
    logout() {
        localStorage.removeItem("user");
    }
    register(username, email, password) {
        return axios.post(API_URL + "signup", {
            username,
            email,
            password
        });
    }
    getCurrentUser() {
        return JSON.parse(localStorage.getItem('user'));
    }
    parseJwt (token) {
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        const jsonPayload = decodeURIComponent(atob(base64).split('').map(function (c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        }).join(''));
        return JSON.parse(jsonPayload);
    }
}
export default new AuthService();