package ro.nexttech.pinservice.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import ro.nexttech.pinservice.api.exceptions.UserExceptionBody;
import ro.nexttech.pinservice.security.util.CookieUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.sql.Date;

public class AuthFailureHandler implements AuthenticationFailureHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        CookieUtil.deleteCookieFromResponse(response, CookieUtil.AUTH_COOKIE_NAME);
        response.setContentType(MediaType.APPLICATION_JSON);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        objectMapper.writeValue(response.getWriter(), new UserExceptionBody(exception.getMessage(), new Date(System.currentTimeMillis())));
    }
}
