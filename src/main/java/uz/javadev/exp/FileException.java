package uz.javadev.exp;

import lombok.Getter;
import uz.javadev.domain.enums.Errors;

@Getter
public class FileException extends RuntimeException {

    private final Errors error;

    public FileException(Errors errors) {
        super(errors.getMessage());
        this.error = errors;
    }
}
