package help.ukraine.app.dto;

import help.ukraine.app.enumerator.Sex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserContactDataDto {
    private String name;
    private String surname;
    private Sex sex;
    private LocalDate birthDate;
    private String email;
    private String phoneNumber;
}
