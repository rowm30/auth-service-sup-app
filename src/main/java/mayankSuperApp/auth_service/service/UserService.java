package mayankSuperApp.auth_service.service;


import mayankSuperApp.auth_service.dto.UserDto;
import mayankSuperApp.auth_service.entity.User;
import mayankSuperApp.auth_service.exception.ResourceNotFoundException;
import mayankSuperApp.auth_service.repository.UserRepository;
import mayankSuperApp.auth_service.security.CustomUserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findActiveUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new CustomUserPrincipal(user, new HashMap<>());
    }

    public User processOAuthPostLogin(String email, String name, String picture, String provider, String providerId) {
        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            User user = existingUser.get();

            // Update user information if changed
            boolean isUpdated = false;

            if (!user.getName().equals(name)) {
                user.setName(name);
                isUpdated = true;
            }

            if (picture != null && !picture.equals(user.getPictureUrl())) {
                user.setPictureUrl(picture);
                isUpdated = true;
            }

            if (isUpdated) {
                user = userRepository.save(user);
                logger.info("Updated existing user: {}", email);
            }

            return user;
        } else {
            // Create new user
            User newUser = new User(name, email, picture, provider, providerId);
            User savedUser = userRepository.save(newUser);

            logger.info("Created new user: {}", email);
            return savedUser;
        }
    }

    @Transactional(readOnly = true)
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        return convertToDto(user);
    }

    @Transactional(readOnly = true)
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findActiveUserByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        return convertToDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public boolean deactivateUser(Long userId) {
        int updated = userRepository.updateUserActiveStatus(userId, false);
        if (updated > 0) {
            logger.info("Deactivated user with id: {}", userId);
            return true;
        }
        return false;
    }

    public boolean activateUser(Long userId) {
        int updated = userRepository.updateUserActiveStatus(userId, true);
        if (updated > 0) {
            logger.info("Activated user with id: {}", userId);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public Map<String, Long> getUserStatsByProvider() {
        List<User> allUsers = userRepository.findAll();

        return allUsers.stream()
                .collect(Collectors.groupingBy(
                        User::getProvider,
                        Collectors.counting()
                ));
    }

    private UserDto convertToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPictureUrl(),
                user.getProvider(),
                user.getCreatedAt()
        );
    }
}
