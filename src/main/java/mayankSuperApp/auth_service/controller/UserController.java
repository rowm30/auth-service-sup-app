package mayankSuperApp.auth_service.controller;



import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import mayankSuperApp.auth_service.dto.AuthResponse;
import mayankSuperApp.auth_service.dto.UserDto;
import mayankSuperApp.auth_service.security.CustomUserPrincipal;
import mayankSuperApp.auth_service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Tag(name = "User Management", description = "User management endpoints")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get current user profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile retrieved successfully",
                    content = @Content(schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class)))
    })
    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(
            @AuthenticationPrincipal CustomUserPrincipal currentUser) {

        logger.debug("Getting current user profile for: {}", currentUser.getUsername());
        UserDto userDto = userService.getUserById(currentUser.getUserId());
        return ResponseEntity.ok(userDto);
    }

    @Operation(summary = "Get user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(
            @Parameter(description = "User ID", required = true)
            @PathVariable Long userId) {

        logger.debug("Getting user by ID: {}", userId);
        UserDto userDto = userService.getUserById(userId);
        return ResponseEntity.ok(userDto);
    }

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        logger.debug("Getting all users");
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Deactivate user account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deactivated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/{userId}/deactivate")
    public ResponseEntity<AuthResponse> deactivateUser(
            @Parameter(description = "User ID", required = true)
            @PathVariable Long userId) {

        logger.info("Deactivating user: {}", userId);
        boolean success = userService.deactivateUser(userId);

        if (success) {
            return ResponseEntity.ok(AuthResponse.success("User deactivated successfully"));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Activate user account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User activated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/{userId}/activate")
    public ResponseEntity<AuthResponse> activateUser(
            @Parameter(description = "User ID", required = true)
            @PathVariable Long userId) {

        logger.info("Activating user: {}", userId);
        boolean success = userService.activateUser(userId);

        if (success) {
            return ResponseEntity.ok(AuthResponse.success("User activated successfully"));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get user statistics by OAuth provider")
    @ApiResponse(responseCode = "200", description = "Statistics retrieved successfully")
    @GetMapping("/stats/providers")
    public ResponseEntity<Map<String, Long>> getUserStatsByProvider() {
        logger.debug("Getting user statistics by provider");
        Map<String, Long> stats = userService.getUserStatsByProvider();
        return ResponseEntity.ok(stats);
    }
}