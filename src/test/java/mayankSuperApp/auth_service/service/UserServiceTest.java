package mayankSuperApp.auth_service.service;

import mayankSuperApp.auth_service.dto.UserDto;
import mayankSuperApp.auth_service.entity.User;
import mayankSuperApp.auth_service.exception.ResourceNotFoundException;
import mayankSuperApp.auth_service.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateUserProfile_updatesFields() {
        User user = new User("Old", "email", null, "google", "pid");
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        UserDto dto = userService.updateUserProfile(1L, "New", "pic");

        assertEquals("New", dto.getName());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateUserProfile_notFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> userService.updateUserProfile(1L, "Name", null));
    }
}
