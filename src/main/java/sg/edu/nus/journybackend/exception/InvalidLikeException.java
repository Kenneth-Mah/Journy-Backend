package sg.edu.nus.journybackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidLikeException extends RuntimeException {
    public InvalidLikeException(String message) {
        super(message);
    }
}