package mayankSuperApp.auth_service.service;


import mayankSuperApp.auth_service.dto.AuthResponse;
import mayankSuperApp.auth_service.dto.JwtResponse;
import mayankSuperApp.auth_service.entity.User;
import mayankSuperApp.auth_service.exception.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * Handle login from mobile/Android clients using Google account data.
     * The client is expected to perform the Google sign in and send the
     * resulting user information to this service.
     */
    public AuthResponse mobileGoogleLogin(mayankSuperApp.auth_service.dto.MobileLoginRequest request) {
        try {
            User user = userService.processOAuthPostLogin(
                    request.getEmail(),
                    request.getName(),
                    request.getPictureUrl(),
                    "google",
                    request.getProviderId()
            );

            JwtResponse jwtResponse = jwtService.generateTokenResponse(user);

            logger.info("Mobile login successful for user: {}", request.getEmail());
            return AuthResponse.success("Login successful", jwtResponse);

        } catch (Exception e) {
            logger.error("Mobile login error", e);
            throw new AuthenticationException("Mobile login failed: " + e.getMessage());
        }
    }
}