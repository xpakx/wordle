package io.github.xpakx.wordle.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadLengthException extends RuntimeException {
    public BadLengthException(String msg) {
        super(msg);
    }
}
