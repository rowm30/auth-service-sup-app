package mayankSuperApp.auth_service.SecurityConfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "app.oauth2")
@Validated
public class OAuth2Config {

    @NotEmpty(message = "Authorized redirect URIs cannot be empty")
    private List<String> authorizedRedirectUris;

    private String successRedirectUri = "http://localhost:3000/auth/callback";
    private String failureRedirectUri = "http://localhost:3000/auth/error";

    // Cookie settings for OAuth2 state
    private int cookieExpireSeconds = 180; // 3 minutes
    private String cookieName = "oauth2_auth_request";

    // Getters and Setters
    public List<String> getAuthorizedRedirectUris() {
        return authorizedRedirectUris;
    }

    public void setAuthorizedRedirectUris(List<String> authorizedRedirectUris) {
        this.authorizedRedirectUris = authorizedRedirectUris;
    }

    public String getSuccessRedirectUri() {
        return successRedirectUri;
    }

    public void setSuccessRedirectUri(String successRedirectUri) {
        this.successRedirectUri = successRedirectUri;
    }

    public String getFailureRedirectUri() {
        return failureRedirectUri;
    }

    public void setFailureRedirectUri(String failureRedirectUri) {
        this.failureRedirectUri = failureRedirectUri;
    }

    public int getCookieExpireSeconds() {
        return cookieExpireSeconds;
    }

    public void setCookieExpireSeconds(int cookieExpireSeconds) {
        this.cookieExpireSeconds = cookieExpireSeconds;
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    /**
     * Checks if the given URI is in the authorized redirect URIs list
     */
    public boolean isAuthorizedRedirectUri(String uri) {
        return authorizedRedirectUris != null &&
                authorizedRedirectUris.stream().anyMatch(authorizedUri ->
                        uri.matches(authorizedUri.replace("*", ".*")));
    }
}