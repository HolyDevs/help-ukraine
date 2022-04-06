package help.ukraine.app.security.constants;

import java.util.List;

public final class AuthUrls {
    public static final String LOGIN_URL = "/auth/login";
    public static final String REFRESH_TOKEN_URL = "/auth/refresh";
    public static final String REGISTER_USER_URL = "/user/register";
    public static final List<String> NO_AUTH_URLS = List.of(LOGIN_URL, REFRESH_TOKEN_URL, REGISTER_USER_URL);
}
