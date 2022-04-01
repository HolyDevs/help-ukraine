package help.ukraine.app.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TokenVerificationError {
    @JsonProperty
    private String error;
}
