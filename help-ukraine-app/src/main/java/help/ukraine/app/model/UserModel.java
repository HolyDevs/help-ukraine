package help.ukraine.app.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserModel {

    private String username;

    private String firstName;

    private String lastName;

    private String role;

    private String password;
}
