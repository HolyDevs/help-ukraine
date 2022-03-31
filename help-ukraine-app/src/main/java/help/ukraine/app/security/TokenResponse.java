package help.ukraine.app.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class TokenResponse {

    @JsonProperty(SecurityConstants.ACCESS_TOKEN_NAME)
    private String accessToken;
    @JsonProperty(SecurityConstants.REFRESH_TOKEN_NAME)
    private String refreshToken;
}
