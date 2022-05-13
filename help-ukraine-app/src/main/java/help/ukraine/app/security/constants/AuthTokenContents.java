package help.ukraine.app.security.constants;

public final class AuthTokenContents {
    // TOKEN GENERATION & CONTENT
    public static final String USERNAME_PARAMETER = "username";
    public static final String PASSWORD_PARAMETER = "password";
    public static final String ROLE_CLAIM = "role";
    public static final String ACCESS_TOKEN_NAME = "access_token";
    public static final String REFRESH_TOKEN_NAME = "refresh_token";

    // TOKEN EXPIRATION PERIODS
    public static final int ACCESS_TOKEN_EXPIRATION = 10 * 60 * 1000 * 1000;
    public static final int REFRESH_TOKEN_EXPIRATION = 30 * 60 * 1000;
}
