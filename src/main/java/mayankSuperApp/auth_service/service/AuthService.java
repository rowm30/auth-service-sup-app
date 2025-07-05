package mayankSuperApp.auth_service.service;


import mayankSuperApp.auth_service.dto.AuthResponse;
import mayankSuperApp.auth_service.dto.JwtResponse;
import mayankSuperApp.auth_service.entity.User;
import mayankSuperApp.auth_service.exception.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.util.Collections;
import java.util.Map;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public AuthService(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Value("${google.client-id}")
    private String googleClientId;

    public AuthResponse validateToken(String token) {
        try {
            if (!jwtService.validateToken(token)) {
                return AuthResponse.error("Token validation failed", "INVALID_TOKEN");
            }

            String email = jwtService.extractEmailFromToken(token);
            Long userId = jwtService.extractUserIdFromToken(token);

            // Verify user still exists and is active
            User user = (User) userService.loadUserByUsername(email);

            if (user == null) {
                return AuthResponse.error("User not found", "USER_NOT_FOUND");
            }

            logger.debug("Token validated successfully for user: {}", email);
            return AuthResponse.success("Token is valid");

        } catch (Exception e) {
            logger.error("Token validation error", e);
            return AuthResponse.error("Token validation failed", e.getMessage());
        }
    }

    public AuthResponse refreshToken(String refreshToken) {
        try {
            if (!jwtService.validateToken(refreshToken)) {
                throw new AuthenticationException("Invalid refresh token");
            }

            String email = jwtService.extractEmailFromToken(refreshToken);
            User user = (User) userService.loadUserByUsername(email);

            JwtResponse newTokenResponse = jwtService.generateTokenResponse(user);

            logger.info("Token refreshed successfully for user: {}", email);
            return AuthResponse.success("Token refreshed successfully", newTokenResponse);

        } catch (Exception e) {
            logger.error("Token refresh error", e);
            throw new AuthenticationException("Failed to refresh token: " + e.getMessage());
        }
    }

    public AuthResponse authenticateWithGoogle(String idTokenString) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(), JacksonFactory.getDefaultInstance())
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken == null) {
                return AuthResponse.error("Invalid ID token", "INVALID_ID_TOKEN");
            }

            Payload p = idToken.getPayload();
            String email   = p.getEmail();
            String name    = (String) p.get("name");
            String picture = (String) p.get("picture");
            String sub     = p.getSubject();          // Google UID

            User user = userService.processOAuthPostLogin(
                    email, name, picture, "google", sub);

            JwtResponse jwt = jwtService.generateTokenResponse(user);
            String refresh = jwtService.generateRefreshToken(email, user.getId());
            jwt.setRefreshToken(refresh);

            return AuthResponse.success("Authenticated", jwt);
        } catch (Exception e) {
            logger.error("Google sign-in failed", e);
            return AuthResponse.error("Authentication failed", e.getMessage());
        }
    }
}