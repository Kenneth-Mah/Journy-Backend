package sg.edu.nus.journybackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class InvalidLoginCredentialException extends RuntimeException {
    public InvalidLoginCredentialException(String message) {
        super(message);
    }
}