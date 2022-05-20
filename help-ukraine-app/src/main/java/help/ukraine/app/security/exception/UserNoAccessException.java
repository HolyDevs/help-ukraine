package help.ukraine.app.security.exception;

public class UserNoAccessException extends Exception {
    public UserNoAccessException(String message) {
        super(message);
    }
}
