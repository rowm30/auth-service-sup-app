package mayankSuperApp.auth_service.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "JWT Authentication Response")
public class JwtResponse {

    @JsonProperty("access_token")
    @Schema(description = "JWT access token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String accessToken;

    @JsonProperty("token_type")
    @Schema(description = "Token type", example = "Bearer")
    private String tokenType = "Bearer";

    @JsonProperty("expires_in")
    @Schema(description = "Token expiration time in seconds", example = "86400")
    private Long expiresIn;

    @JsonProperty("user")
    @Schema(description = "User information")
    private UserDto user;

    // Constructors
    public JwtResponse() {}

    public JwtResponse(String accessToken, Long expiresIn, UserDto user) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.user = user;
    }

    // Getters and Setters
    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }

    public String getTokenType() { return tokenType; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }

    public Long getExpiresIn() { return expiresIn; }
    public void setExpiresIn(Long expiresIn) { this.expiresIn = expiresIn; }

    public UserDto getUser() { return user; }
    public void setUser(UserDto user) { this.user = user; }
}
