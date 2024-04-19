package sg.edu.nus.journybackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class InvalidFollowException extends RuntimeException {
    public InvalidFollowException(String message) {
        super(message);
    }
}