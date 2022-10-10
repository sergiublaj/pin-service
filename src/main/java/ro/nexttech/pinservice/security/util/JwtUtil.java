package ro.nexttech.pinservice.security.util;

import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ro.nexttech.pinservice.api.models.LoginRequest;

import java.util.Base64;
import java.util.Date;

@Data
@Component
public class JwtUtil {
    @Value("${application.jwt.secretKey}")
    private String secret;
    @Value("${application.jwt.expirationTime}")
    private String expirationTime;

    public String generateToken(String email) {
        return Jwts
                .builder()
                .claim("email", email) // body
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(expirationTime)))
                .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS512), SignatureAlgorithm.HS512)
                .compact();
    }

    public String getEmailFromToken(String token) {
        Base64.Decoder decoder = Base64.getUrlDecoder();
        Gson gson = new Gson();

        String[] chunks = token.split("\\.");
        String payload = new String(decoder.decode(chunks[1]));

        // further verifications

        return gson.fromJson(payload, LoginRequest.class).getEmail();
    }
}
