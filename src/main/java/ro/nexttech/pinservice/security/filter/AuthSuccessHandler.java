package ro.nexttech.pinservice.security.filter;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import ro.nexttech.pinservice.security.util.CookieUtil;
import ro.nexttech.pinservice.security.util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@AllArgsConstructor
public class AuthSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String jwtToken = jwtUtil.generateToken(authentication.getPrincipal().toString());
        CookieUtil.addCookieToResponse(response, CookieUtil.AUTH_COOKIE_NAME, jwtToken);
    }
}
