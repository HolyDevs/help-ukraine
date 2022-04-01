package help.ukraine.app.security.constants;

public final class SecurityConstants {
    // AUTH URLS
    public static final String LOGIN_URL = "/auth/login";
    public static final String REFRESH_TOKEN_URL = "/auth/refresh";

    // TOKEN GENERATION & CONTENT
    public static final String USERNAME_PARAMETER = "username";
    public static final String PASSWORD_PARAMETER = "password";
    public static final String ROLE_CLAIM = "role";
    public static final String ACCESS_TOKEN_NAME = "access_token";
    public static final String REFRESH_TOKEN_NAME = "refresh_token";

    // TOKEN EXPIRATION PERIODS
    public static final int ACCESS_TOKEN_EXPIRATION = 10 * 60 * 1000;
    public static final int REFRESH_TOKEN_EXPIRATION = 30 * 60 * 1000;

    // TOKEN VERIFICATION ERROR MESSAGES
    public static final String IMPROPER_FORMAT_AUTH_HEADER_MSG = "Authorization header is missing or has improper format";
    public static final String ACCESS_TOKEN_FAIL = "Cannot verify access token - it is invalid or expired";
}
