package help.ukraine.app.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import help.ukraine.app.security.constants.SecurityConstants;
import lombok.Builder;

@Builder
public class GeneratedToken {
    @JsonProperty(SecurityConstants.ACCESS_TOKEN_NAME)
    private String accessToken;
    @JsonProperty(SecurityConstants.REFRESH_TOKEN_NAME)
    private String refreshToken;
}
