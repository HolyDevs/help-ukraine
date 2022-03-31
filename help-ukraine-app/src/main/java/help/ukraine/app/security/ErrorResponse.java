package help.ukraine.app.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorResponse {
    @JsonProperty
    private String error;
}
