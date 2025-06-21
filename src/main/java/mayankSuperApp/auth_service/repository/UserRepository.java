package mayankSuperApp.auth_service.repository;


import mayankSuperApp.auth_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByProviderAndProviderId(String provider, String providerId);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.isActive = true")
    Optional<User> findActiveUserByEmail(@Param("email") String email);

    @Modifying
    @Query("UPDATE User u SET u.isActive = :isActive WHERE u.id = :id")
    int updateUserActiveStatus(@Param("id") Long id, @Param("isActive") Boolean isActive);

    @Query("SELECT COUNT(u) FROM User u WHERE u.provider = :provider")
    long countByProvider(@Param("provider") String provider);
}