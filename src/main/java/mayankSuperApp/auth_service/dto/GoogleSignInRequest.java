package mayankSuperApp.auth_service.dto;

import jakarta.validation.constraints.NotBlank;

public class GoogleSignInRequest {

    @NotBlank
    private String idToken;

    public String getIdToken() { return idToken; }
    public void setIdToken(String idToken) { this.idToken = idToken; }
}