package ro.nexttech.pinservice.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ro.nexttech.pinservice.api.exceptions.UserExceptionBody;
import ro.nexttech.pinservice.api.models.LoginRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.sql.Date;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final ObjectMapper objectMapper;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(ObjectMapper objectMapper, AuthenticationManager authenticationManager,
                                   AuthSuccessHandler authSuccessHandler, AuthFailureHandler authFailureHandler) {
        this.objectMapper = objectMapper;
        this.authenticationManager = authenticationManager;
        this.setFilterProcessesUrl("/auth/login");
        this.setAuthenticationSuccessHandler(authSuccessHandler);
        this.setAuthenticationFailureHandler(authFailureHandler);
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequest authenticationRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()
            );
            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            response.setContentType(MediaType.APPLICATION_JSON);
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            objectMapper.writeValue(response.getWriter(), new UserExceptionBody("Access denied!", new Date(System.currentTimeMillis())));
        }

        return null;
    }
}
