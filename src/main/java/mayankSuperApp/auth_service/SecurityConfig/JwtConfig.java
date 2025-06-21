package mayankSuperApp.auth_service.SecurityConfig;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Validated
public class JwtConfig {

    @NotBlank(message = "JWT secret is required")
    private String secret;

    @Positive(message = "JWT expiration must be positive")
    private Long expiration = 86400000L; // 24 hours default

    @Positive(message = "JWT refresh expiration must be positive")
    private Long refreshExpiration = 604800000L; // 7 days default

    private String tokenType = "Bearer";
    private String tokenPrefix = "Bearer ";
    private String headerName = "Authorization";

    // Getters and Setters
    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    public Long getRefreshExpiration() {
        return refreshExpiration;
    }

    public void setRefreshExpiration(Long refreshExpiration) {
        this.refreshExpiration = refreshExpiration;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    /**
     * Validates the JWT secret length for security
     */
    public boolean isSecretValid() {
        return secret != null && secret.length() >= 32; // Minimum 256 bits
    }

    /**
     * Gets expiration time in seconds
     */
    public Long getExpirationInSeconds() {
        return expiration / 1000;
    }

    /**
     * Gets refresh expiration time in seconds
     */
    public Long getRefreshExpirationInSeconds() {
        return refreshExpiration / 1000;
    }
}