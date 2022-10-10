package ro.nexttech.pinservice.security.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ro.nexttech.pinservice.PinServiceApplication;

import java.util.List;
import java.util.stream.Collectors;

public class AuthenticationServiceImpl implements AuthenticationManager {
    public static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
    public static final List<UserDetails> userList = PinServiceApplication.users.stream().map((user) -> User
            .withUsername(user.getEmailAddress())
            .password(passwordEncoder.encode("123456"))
            .roles(getRoleByEmail(user.getEmailAddress()))
            .build()).collect(Collectors.toList());


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println(userList.get(0).getPassword());
        UserDetails userDetail = userList.stream().
                filter((user) ->
                        user.getUsername().equals(authentication.getName()) &&
                                (passwordEncoder.matches(authentication.getCredentials().toString(), user.getPassword()) ||
                                        authentication.getCredentials().equals(user.getPassword()))
                ).findFirst().orElse(null);

        if (userDetail == null) {
            throw new BadCredentialsException("Invalid credentials!");
        }

        return new UsernamePasswordAuthenticationToken(userDetail.getUsername(), userDetail.getPassword());
    }

    public static String getRoleByEmail(String email) {
        return email.equals("johndoe@mail.com") ? "ADMIN" : "USER";
    }
}
