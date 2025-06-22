package mayankSuperApp.auth_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mayankSuperApp.auth_service.dto.UpdateUserRequest;
import mayankSuperApp.auth_service.dto.UserDto;
import mayankSuperApp.auth_service.entity.User;
import mayankSuperApp.auth_service.security.CustomUserPrincipal;
import mayankSuperApp.auth_service.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void updateCurrentUser_returnsOk() throws Exception {
        UpdateUserRequest req = new UpdateUserRequest();
        req.setName("New");
        UserDto dto = new UserDto();
        dto.setId(1L);
        dto.setName("New");
        dto.setEmail("user@example.com");
        Mockito.when(userService.updateUserProfile(anyLong(), any(), any())).thenReturn(dto);

        User principalUser = new User("Old", "user@example.com", null, "google", "pid");
        CustomUserPrincipal principal = new CustomUserPrincipal(principalUser, new HashMap<>());

        mockMvc.perform(put("/user/me").principal(principal)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    void searchUserByEmail_returnsOk() throws Exception {
        UserDto dto = new UserDto();
        dto.setId(1L);
        dto.setName("A");
        dto.setEmail("a@example.com");
        Mockito.when(userService.getUserByEmail("a@example.com")).thenReturn(dto);

        mockMvc.perform(get("/user/search").param("email", "a@example.com"))
                .andExpect(status().isOk());
    }
}
