package help.ukraine.app.security.constants;

import java.util.List;

import static help.ukraine.app.controller.UserController.REGISTER_USER_ENDPOINT;

public final class AuthUrls {
    public static final String BACKEND_URL = "/app";
    public static final String LOGIN_URL = "/auth/login";
    public static final String REFRESH_TOKEN_URL = "/auth/refresh";
    public static final List<String> NO_AUTH_URLS = List.of(LOGIN_URL, REFRESH_TOKEN_URL, REGISTER_USER_ENDPOINT);
}
