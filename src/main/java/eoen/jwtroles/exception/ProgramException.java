package eoen.jwtroles.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProgramException extends RuntimeException {

    private String message;

    public ProgramException(String message) {
        super(message);
        this.message = message;
    }

}
