package mayankSuperApp.auth_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Generic Authentication Response")
public class AuthResponse {

    @Schema(description = "Response message", example = "Authentication successful")
    private String message;

    @Schema(description = "Success status", example = "true")
    private boolean success;

    @Schema(description = "JWT token response")
    private JwtResponse data;

    @Schema(description = "Error details")
    private String error;

    // Constructors
    public AuthResponse() {}

    public AuthResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public AuthResponse(boolean success, String message, JwtResponse data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public AuthResponse(boolean success, String message, String error) {
        this.success = success;
        this.message = message;
        this.error = error;
    }

    // Static factory methods
    public static AuthResponse success(String message, JwtResponse data) {
        return new AuthResponse(true, message, data);
    }

    public static AuthResponse success(String message) {
        return new AuthResponse(true, message);
    }

    public static AuthResponse error(String message, String error) {
        return new AuthResponse(false, message, error);
    }

    // Getters and Setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public JwtResponse getData() { return data; }
    public void setData(JwtResponse data) { this.data = data; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
}