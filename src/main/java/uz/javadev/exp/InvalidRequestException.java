package uz.javadev.exp;

import lombok.Getter;
import uz.javadev.domain.enums.Errors;

@Getter
public class InvalidRequestException extends RuntimeException {
    private final Errors error;

    public InvalidRequestException(Errors errors) {
        super(errors.getMessage());
        this.error = errors;
    }
}
