package help.ukraine.app.security;

public final class SecurityConstants {
    // ROLES
    public static final String REFUGEE_ROLE = "Refugee";
    public static final String HOST_ROLE = "Admin";
    public static final String VERIFIER_ROLE = "Verifier";

    // LOGIN URL
    public static final String LOGIN_URL = "/login";

    // TOKEN GENERATION & CONTENT
    public static final String USERNAME_PARAMETER = "username";
    public static final String PASSWORD_PARAMETER = "password";
    public static final String ROLE_CLAIM = "role";
    public static final String ACCESS_TOKEN_NAME = "access_token";
    public static final String REFRESH_TOKEN_NAME = "refresh_token";
}
