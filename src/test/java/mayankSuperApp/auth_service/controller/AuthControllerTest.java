package mayankSuperApp.auth_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mayankSuperApp.auth_service.dto.AuthResponse;
import mayankSuperApp.auth_service.dto.MobileLoginRequest;
import mayankSuperApp.auth_service.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    private MobileLoginRequest request;

    @BeforeEach
    void setUp() {
        request = new MobileLoginRequest();
        request.setEmail("test@example.com");
        request.setName("Test");
        request.setProviderId("pid");
    }

    @Test
    void mobileGoogleLogin_returnsOk() throws Exception {
        Mockito.when(authService.mobileGoogleLogin(any())).thenReturn(AuthResponse.success("ok", null));

        mockMvc.perform(post("/auth/mobile/google")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}
