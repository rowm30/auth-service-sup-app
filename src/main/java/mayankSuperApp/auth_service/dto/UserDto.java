package mayankSuperApp.auth_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "User Data Transfer Object")
public class UserDto {

    @Schema(description = "User ID", example = "1")
    private Long id;

    @NotBlank(message = "Name is required")
    @Schema(description = "User's full name", example = "John Doe")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Schema(description = "User's email address", example = "john.doe@example.com")
    private String email;

    @Schema(description = "User's profile picture URL", example = "https://example.com/profile.jpg")
    private String pictureUrl;

    @Schema(description = "OAuth provider", example = "google")
    private String provider;

    @Schema(description = "User role", example = "ROLE_USER")
    private String role;

    @Schema(description = "Account creation timestamp")
    private LocalDateTime createdAt;

    // Constructors
    public UserDto() {}

    public UserDto(Long id, String name, String email, String pictureUrl, String provider, LocalDateTime createdAt, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.pictureUrl = pictureUrl;
        this.provider = provider;
        this.createdAt = createdAt;
        this.role = role;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPictureUrl() { return pictureUrl; }
    public void setPictureUrl(String pictureUrl) { this.pictureUrl = pictureUrl; }

    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
