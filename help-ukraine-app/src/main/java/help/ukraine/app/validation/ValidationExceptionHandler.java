package help.ukraine.app.validation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ValidationExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        StringBuilder errorMessage = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> errorMessage.append(fieldError.getField()).append(" - ")
                .append(fieldError.getDefaultMessage()).append("; "));
        return ResponseEntity.badRequest().body(errorMessage.toString());
    }
}
