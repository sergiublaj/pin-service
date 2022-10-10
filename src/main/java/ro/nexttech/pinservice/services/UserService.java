package ro.nexttech.pinservice.services;

import ro.nexttech.pinservice.api.models.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO getUserByEmail(String email);

    List<UserDTO> getUsers();

    UserDTO saveUser(UserDTO user);
}
