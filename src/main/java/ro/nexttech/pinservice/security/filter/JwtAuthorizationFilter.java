package ro.nexttech.pinservice.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import ro.nexttech.pinservice.api.exceptions.UserExceptionBody;
import ro.nexttech.pinservice.security.service.AuthenticationServiceImpl;
import ro.nexttech.pinservice.security.util.CookieUtil;
import ro.nexttech.pinservice.security.util.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.sql.Date;
import java.util.Collections;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = request.getHeader(CookieUtil.AUTH_COOKIE_NAME);
        if (jwtToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String email = jwtUtil.getEmailFromToken(jwtToken);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    email, null, Collections.singleton(new SimpleGrantedAuthority(AuthenticationServiceImpl.getRoleByEmail(email)))
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            response.setContentType(MediaType.APPLICATION_JSON);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            objectMapper.writeValue(response.getWriter(), new UserExceptionBody("Access denied!", new Date(System.currentTimeMillis())));
        }

        filterChain.doFilter(request, response);
    }
}
