package mayankSuperApp.auth_service.controller;



import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import mayankSuperApp.auth_service.dto.AuthResponse;
import mayankSuperApp.auth_service.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Authentication endpoints for OAuth2 and JWT")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Initiate Google OAuth2 authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Redirect to Google OAuth2"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/google")
    public void authenticateWithGoogle(HttpServletResponse response) throws IOException {
        logger.info("Redirecting to Google OAuth2 authentication");
        response.sendRedirect("/oauth2/authorization/google");
    }

    @Operation(summary = "Validate JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token validation result",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid token",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class)))
    })
    @PostMapping("/validate")
    public ResponseEntity<AuthResponse> validateToken(
            @Parameter(description = "JWT token to validate", required = true)
            @RequestHeader("Authorization") String authorizationHeader) {

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest()
                    .body(AuthResponse.error("Invalid authorization header", "MISSING_BEARER_TOKEN"));
        }

        String token = authorizationHeader.substring(7);
        AuthResponse response = authService.validateToken(token);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(response);
        }
    }

    @Operation(summary = "Refresh JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token refreshed successfully",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid refresh token",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class)))
    })
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(
            @Parameter(description = "Refresh token", required = true)
            @RequestHeader("Authorization") String authorizationHeader) {

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest()
                    .body(AuthResponse.error("Invalid authorization header", "MISSING_BEARER_TOKEN"));
        }

        String refreshToken = authorizationHeader.substring(7);
        AuthResponse response = authService.refreshToken(refreshToken);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Logout user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logout successful",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class)))
    })
    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logout() {
        // In a stateless JWT setup, logout is handled client-side by removing the token
        // You could implement token blacklisting here if needed
        logger.info("User logout requested");
        return ResponseEntity.ok(AuthResponse.success("Logout successful"));
    }

    @Operation(summary = "Health check for authentication service")
    @ApiResponse(responseCode = "200", description = "Service is healthy")
    @GetMapping("/health")
    public ResponseEntity<AuthResponse> healthCheck() {
        return ResponseEntity.ok(AuthResponse.success("Authentication service is healthy"));
    }
}