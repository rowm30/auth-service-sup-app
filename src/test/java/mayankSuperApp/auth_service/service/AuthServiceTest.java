package mayankSuperApp.auth_service.service;

import mayankSuperApp.auth_service.dto.AuthResponse;
import mayankSuperApp.auth_service.dto.JwtResponse;
import mayankSuperApp.auth_service.dto.MobileLoginRequest;
import mayankSuperApp.auth_service.entity.User;
import mayankSuperApp.auth_service.exception.AuthenticationException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserService userService;
    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    public AuthServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void mobileGoogleLogin_success() {
        MobileLoginRequest req = new MobileLoginRequest();
        req.setEmail("test@example.com");
        req.setName("Test");
        req.setProviderId("pid");
        User user = new User("Test","test@example.com",null,"google","pid");
        JwtResponse jwt = new JwtResponse("token",3600L,null);

        when(userService.processOAuthPostLogin("test@example.com","Test",null,"google","pid"))
                .thenReturn(user);
        when(jwtService.generateTokenResponse(user)).thenReturn(jwt);

        AuthResponse resp = authService.mobileGoogleLogin(req);
        assertTrue(resp.isSuccess());
        assertEquals("token", resp.getData().getAccessToken());
    }

    @Test
    void mobileGoogleLogin_failure() {
        MobileLoginRequest req = new MobileLoginRequest();
        req.setEmail("bad@example.com");
        req.setName("Bad");
        req.setProviderId("pid");

        when(userService.processOAuthPostLogin(any(), any(), any(), any(), any()))
                .thenThrow(new RuntimeException("db error"));

        assertThrows(AuthenticationException.class, () -> authService.mobileGoogleLogin(req));
    }
}
