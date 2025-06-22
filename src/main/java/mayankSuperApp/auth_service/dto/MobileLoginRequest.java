package mayankSuperApp.auth_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Request payload for mobile/Android login.
 */
@Schema(description = "Request payload for mobile Google login")
public class MobileLoginRequest {

    @NotBlank
    @Email
    @Schema(description = "User email", example = "user@example.com")
    private String email;

    @NotBlank
    @Schema(description = "User full name", example = "John Doe")
    private String name;

    @Schema(description = "Profile picture URL", example = "https://example.com/img.jpg")
    private String pictureUrl;

    @NotBlank
    @Schema(description = "OAuth provider user id", example = "12345678901234567890")
    private String providerId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
