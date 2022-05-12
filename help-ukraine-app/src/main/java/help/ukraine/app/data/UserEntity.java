package help.ukraine.app.data;

import help.ukraine.app.enumerator.AccountType;
import help.ukraine.app.enumerator.Sex;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class UserEntity {

    public static final String ID_COLUMN_NAME = "id";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private String name;
    private String surname;
    private LocalDate birthDate;
    private String phoneNumber;
    private String hashedPassword;
    private Boolean isAccountVerified;
}
