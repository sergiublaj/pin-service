package ro.nexttech.pinservice.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import ro.nexttech.pinservice.security.filter.AuthFailureHandler;
import ro.nexttech.pinservice.security.filter.AuthSuccessHandler;
import ro.nexttech.pinservice.security.filter.JwtAuthenticationFilter;
import ro.nexttech.pinservice.security.filter.JwtAuthorizationFilter;
import ro.nexttech.pinservice.security.service.AuthenticationServiceImpl;
import ro.nexttech.pinservice.security.util.JwtUtil;

import java.util.Collections;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final JwtUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new AuthenticationServiceImpl();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Collections.singletonList("*"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setExposedHeaders(Collections.singletonList("*"));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .authorizeHttpRequests((authorize) -> authorize
                        .antMatchers("/auth/login")
                        .permitAll())
                .authorizeHttpRequests((authorize) -> authorize
                        .antMatchers(HttpMethod.GET, "/api/details")
                        .hasAnyAuthority("USER", "ADMIN"))
                .authorizeHttpRequests((authorize) -> authorize
                        .antMatchers(HttpMethod.POST, "/api/users")
                        .hasAnyAuthority("ADMIN"))
                .authorizeHttpRequests((authorize) -> authorize
                        .antMatchers(HttpMethod.GET, "/api/users")
                        .hasAnyAuthority("ADMIN"))
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest()
                        .authenticated());

        http
                .addFilter(
                        new JwtAuthenticationFilter(new ObjectMapper(), authenticationManager(),
                                new AuthSuccessHandler(jwtUtil), new AuthFailureHandler()))
                .addFilter(
                        new JwtAuthorizationFilter(jwtUtil, authenticationManager()));

        return http.build();
    }
}
