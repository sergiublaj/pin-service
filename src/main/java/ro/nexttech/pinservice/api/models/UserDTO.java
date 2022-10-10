package ro.nexttech.pinservice.api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class UserDTO {
    private String name;
    private String email;
    private Integer age;
    private String role;
}
