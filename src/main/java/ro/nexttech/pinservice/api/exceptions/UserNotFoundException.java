package ro.nexttech.pinservice.api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserNotFoundException extends RuntimeException {
    private String message;
}
