package ro.nexttech.pinservice.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ro.nexttech.pinservice.api.models.UserDTO;
import ro.nexttech.pinservice.security.service.AuthenticationServiceImpl;
import ro.nexttech.pinservice.servicelayer.models.calvin.UserEntityCalvin;

import java.util.List;

@Mapper(componentModel = "spring", imports = {AuthenticationServiceImpl.class})
public interface UserMapper {

    @Mapping(target = "name", source = "userEntityCalvin.name")
    @Mapping(target = "email", source = "userEntityCalvin.emailAddress")
    @Mapping(target = "age", source = "userEntityCalvin.age")
    @Mapping(target = "role", expression = "java(AuthenticationServiceImpl.getRoleByEmail(userEntityCalvin.getEmailAddress()))")
    UserDTO userEntityCalvinToUserDTO(UserEntityCalvin userEntityCalvin);

    List<UserDTO> userEntityCalvinListToUserDTOList(List<UserEntityCalvin> userEntityCalvinList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "emailAddress", source = "email")
    @Mapping(target = "age", source = "age")
    UserEntityCalvin userDTOToUserEntityCalvin(UserDTO user);
}
