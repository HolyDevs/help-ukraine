package help.ukraine.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import help.ukraine.app.enumerator.AccountType;
import help.ukraine.app.enumerator.Sex;
import help.ukraine.app.validation.ValidationRegex;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserModel {
    private Long id;

    @Email(regexp = ValidationRegex.EMAIL_ADDRESS_PATTERN)
    private String email;

    @NotNull
    private Sex sex;

    @NotNull
    private AccountType accountType;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String password;

    @NotNull
    @JsonProperty("isAccountVerified")
    private Boolean isAccountVerified;
}
