package help.ukraine.app.security.constants;

import java.util.List;

public final class AuthUrls {
    public static final String BACKEND_ROOT = "/app";
    public static final String LOGIN_URL = BACKEND_ROOT + "/app/auth/login";
    public static final String REFRESH_TOKEN_URL = BACKEND_ROOT + "/auth/refresh";
    public static final List<String> NO_AUTH_URLS = List.of(LOGIN_URL, REFRESH_TOKEN_URL);
}
