package help.ukraine.app.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import help.ukraine.app.security.constants.AuthTokenContents;
import lombok.Builder;

@Builder
public class GeneratedToken {
    @JsonProperty(AuthTokenContents.ACCESS_TOKEN_NAME)
    private String accessToken;
    @JsonProperty(AuthTokenContents.REFRESH_TOKEN_NAME)
    private String refreshToken;
}
