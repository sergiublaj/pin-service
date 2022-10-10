package ro.nexttech.pinservice.api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
public class UserExceptionBody {
    private String message;
    private Date date;
}
