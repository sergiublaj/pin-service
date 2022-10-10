package ro.nexttech.pinservice.servicelayer.models.calvin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class UserEntityCalvin {
    private String id;
    private String name;
    private String emailAddress;
    private Integer age;
}
