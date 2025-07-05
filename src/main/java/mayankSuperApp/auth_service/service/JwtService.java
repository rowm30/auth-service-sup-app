package mayankSuperApp.auth_service.service;

import io.jsonwebtoken.JwtException;
import mayankSuperApp.auth_service.dto.JwtResponse;
import mayankSuperApp.auth_service.dto.UserDto;
import mayankSuperApp.auth_service.entity.User;
import mayankSuperApp.auth_service.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    private final JwtUtil jwtUtil;

    @Autowired
    public JwtService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * Generate an access-token response payload for the given User.
     */
    public JwtResponse generateTokenResponse(User user) {
        try {
            String accessToken = jwtUtil.generateToken(
                    user.getEmail(),
                    user.getId(),
                    user.getName()
            );
            long expiresIn = jwtUtil.getExpirationTime() / 1_000; // seconds

            UserDto userDto = new UserDto(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getPictureUrl(),
                    user.getProvider(),
                    user.getCreatedAt()
            );

            logger.info("Generated JWT token for user: {}", user.getEmail());
            return new JwtResponse(accessToken, expiresIn, userDto);

        } catch (JwtException e) {
            logger.error("Failed to generate JWT token for user {}: {}", user.getEmail(), e.getMessage(), e);
            throw new RuntimeException("Could not generate JWT token", e);
        }
    }

    /**
     * Validate that this token belongs to the given email and is not expired.
     */
    public boolean validateToken(String token, String email) {
        try {
            return jwtUtil.validateToken(token, email);
        } catch (JwtException e) {
            logger.warn("Token validation failed for {}: {}", email, e.getMessage());
            return false;
        }
    }

    /**
     * Just check signature & expiry, without matching to any particular user.
     */
    public boolean validateToken(String token) {
        try {
            return jwtUtil.validateToken(token);
        } catch (JwtException e) {
            logger.warn("Token validation failed: {}", e.getMessage());
            return false;
        }
    }

    /** Extract the subject (email) from the token. */
    public String extractEmail(String token) {
        return jwtUtil.extractEmail(token);
    }

    /** Extract the userId claim from the token. */
    public Long extractUserId(String token) {
        return jwtUtil.extractUserId(token);
    }

    /** Extract the name claim from the token. */
    public String extractName(String token) {
        return jwtUtil.extractName(token);
    }

    /** Extract the raw expiration timestamp from the token. */
    public java.util.Date extractExpiration(String token) {
        return jwtUtil.extractExpiration(token);
    }

    /**
     * Convenience wrapper to pull the email out of a JWT,
     * returning null (and logging) if the token is invalid.
     */
    public String extractEmailFromToken(String jwt) {
        try {
            return jwtUtil.extractEmail(jwt);
        } catch (JwtException e) {
            logger.warn("Failed to extract email from token: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Convenience wrapper to pull the userId out of a JWT,
     * returning null (and logging) if the token is invalid.
     */
    public Long extractUserIdFromToken(String token) {
        try {
            return jwtUtil.extractUserId(token);
        } catch (JwtException e) {
            logger.warn("Failed to extract userId from token: {}", e.getMessage());
            return null;
        }
    }



    public String generateRefreshToken(String email, Long userId) {
        return jwtUtil.generateRefreshToken(email, userId);
    }
}
