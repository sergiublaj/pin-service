package ro.nexttech.pinservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.nexttech.pinservice.api.exceptions.UserNotFoundException;
import ro.nexttech.pinservice.api.models.UserDTO;
import ro.nexttech.pinservice.mapping.UserMapper;
import ro.nexttech.pinservice.servicelayer.models.calvin.UserEntityCalvin;
import ro.nexttech.pinservice.servicelayer.services.calvin.UserServiceClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserServiceClient userServiceClient;

    @Override
    public UserDTO getUserByEmail(String email) {
        UserEntityCalvin userEntityCalvin = userServiceClient.getUserByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User " + email + " not found!"));

        return userMapper.userEntityCalvinToUserDTO(userEntityCalvin);
    }

    @Override
    public List<UserDTO> getUsers() {
        List<UserEntityCalvin> userEntityCalvinList = userServiceClient.getUsers();

        return userMapper.userEntityCalvinListToUserDTOList(userEntityCalvinList);
    }

    @Override
    public UserDTO saveUser(UserDTO user) {
        UserEntityCalvin userEntityCalvin = userMapper.userDTOToUserEntityCalvin(user);

        return userMapper.userEntityCalvinToUserDTO(userServiceClient.saveUser(userEntityCalvin));
    }
}
