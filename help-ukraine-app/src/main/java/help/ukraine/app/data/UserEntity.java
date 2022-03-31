package help.ukraine.app.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class UserEntity {

    private static final String ROLE_COLUMN = "role";

    @Id
    private String username;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ROLE_COLUMN)
    private RoleEntity roleEntity;

    @Column(name = ROLE_COLUMN, insertable = false, updatable = false)
    private String role;

    @Column(nullable = false)
    private String password;
}
