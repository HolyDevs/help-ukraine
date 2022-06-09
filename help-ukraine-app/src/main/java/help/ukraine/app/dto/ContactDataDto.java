package help.ukraine.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ContactDataDto {
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
}
