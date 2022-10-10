package ro.nexttech.pinservice.servicelayer.services.calvin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.nexttech.pinservice.servicelayer.models.calvin.UserEntityCalvin;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static ro.nexttech.pinservice.PinServiceApplication.users;

@Component
@RequiredArgsConstructor
public class UserServiceClient {
    public Optional<UserEntityCalvin> getUserByEmail(String email) {
        return users.stream().filter((user) -> user.getEmailAddress().equals(email)).findFirst();
    }

    public List<UserEntityCalvin> getUsers() {
        return users;
    }

    public UserEntityCalvin saveUser(UserEntityCalvin user) {
        user.setId(UUID.randomUUID().toString());
        users.add(user);
        return user;
    }
}
