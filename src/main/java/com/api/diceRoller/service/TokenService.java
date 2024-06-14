package com.api.diceRoller.service;

import com.api.diceRoller.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.UUID;

@Service
public class TokenService {

    @Autowired
    private JwtEncoder jwtEncoder;

    public String generateToken (User user) {
        String scopes = String.join(" ", user.getRoles());
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("Dice Roller")
                .subject(user.getId().toString())
                .issuedAt(Instant.now())
                .claim("email", user.getEmail())
                .claim("scope", scopes)
                .expiresAt(Instant.now().plusSeconds(300))
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    /**
     * verify if the email jwt is not equal to the email
     *
     * @param jwt user's jwt
     * @param email user's e-mail
     * @return boolean
     */
    public boolean notBelongs(Jwt jwt, String email) {
        return !email.equals(jwt.getClaim("email").toString()) || !jwt.getClaim("scope").toString().contains("ADMIN");
    }

    /**
     * verify if the subject jwt is not equal to the user id
     *
     * @param jwt user's jwt
     * @param userId user's id
     * @return boolean
     */
    public boolean notBelongs(Jwt jwt, UUID userId) {
        return !jwt.getSubject().equals(userId.toString()) || !jwt.getClaim("scope").toString().contains("ADMIN");
    }

}
