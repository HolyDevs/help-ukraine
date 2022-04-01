package help.ukraine.app.model;

import help.ukraine.app.enumerator.AccountType;
import help.ukraine.app.enumerator.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserModel {
    private String email;
    private Sex sex;
    private AccountType accountType;
    private String name;
    private String surname;
    private Date birthDate;
    private String phoneNumber;
    private String password;
    private boolean isAccountVerified;
}
