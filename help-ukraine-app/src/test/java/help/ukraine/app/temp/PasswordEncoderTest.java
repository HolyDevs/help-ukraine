package help.ukraine.app.temp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PasswordEncoderTest {
    @Test
    void encode() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String secret = "SECRET";
        String password = "aaa";
        String encrypted = passwordEncoder.encode(password);
        int i = 0;
    }
}
