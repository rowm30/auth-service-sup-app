package mayankSuperApp.auth_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

/**
 * Request payload for updating a user's own profile.
 */
@Schema(description = "Payload to update current user profile")
public class UpdateUserRequest {

    @Size(max = 100)
    @Schema(description = "Updated full name", example = "John Doe")
    private String name;

    @Size(max = 500)
    @Schema(description = "Updated picture URL", example = "https://example.com/img.jpg")
    private String pictureUrl;

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
}
