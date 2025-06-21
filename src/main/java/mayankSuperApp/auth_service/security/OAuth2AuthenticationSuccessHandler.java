package mayankSuperApp.auth_service.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mayankSuperApp.auth_service.dto.JwtResponse;
import mayankSuperApp.auth_service.entity.User;
import mayankSuperApp.auth_service.service.JwtService;
import mayankSuperApp.auth_service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(OAuth2AuthenticationSuccessHandler.class);

    private final UserService userService;
    private final JwtService jwtService;

    @Value("${app.oauth2.authorized-redirect-uris:http://localhost:3000/auth/callback}")
    private String redirectUri;

    @Autowired
    public OAuth2AuthenticationSuccessHandler(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {

        try {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

            // Extract user info from OAuth2 provider
            String email = oAuth2User.getAttribute("email");
            String name = oAuth2User.getAttribute("name");
            String picture = oAuth2User.getAttribute("picture");
            String providerId = oAuth2User.getAttribute("sub");

            // Create or update user
            User user = userService.processOAuthPostLogin(email, name, picture, "google", providerId);

            // Generate JWT token
            JwtResponse jwtResponse = jwtService.generateTokenResponse(user);

            // Build redirect URL with token
            String targetUrl = UriComponentsBuilder.fromUriString(redirectUri)
                    .queryParam("token", jwtResponse.getAccessToken())
                    .queryParam("success", "true")
                    .build().toUriString();

            logger.info("OAuth2 authentication successful for user: {}", email);
            return targetUrl;

        } catch (Exception e) {
            logger.error("Error during OAuth2 authentication success handling", e);

            String errorUrl = UriComponentsBuilder.fromUriString(redirectUri)
                    .queryParam("error", URLEncoder.encode("Authentication failed", StandardCharsets.UTF_8))
                    .queryParam("success", "false")
                    .build().toUriString();

            return errorUrl;
        }
    }
}