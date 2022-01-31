package io.github.xpakx.wordle.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WordTooLongException extends RuntimeException {
    public WordTooLongException(String msg) {
        super(msg);
    }
}
