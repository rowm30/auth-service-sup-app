package mayankSuperApp.auth_service.repository;


import mayankSuperApp.auth_service.entity.Constituency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConstituencyRepository extends JpaRepository<Constituency, Long> {

    // Find constituency by exact name
    Optional<Constituency> findByConstituencyIgnoreCase(String constituency);

    // Find constituencies by state
    List<Constituency> findByStateIgnoreCase(String state);

    // Find constituencies by district
    List<Constituency> findByDistrictIgnoreCase(String district);

    // Find constituency containing specific text
    @Query("SELECT c FROM Constituency c WHERE LOWER(c.constituency) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Constituency> findByConstituencyContaining(@Param("name") String name);

    // Find constituency by state and district
    @Query("SELECT c FROM Constituency c WHERE LOWER(c.state) = LOWER(:state) AND LOWER(c.district) = LOWER(:district)")
    List<Constituency> findByStateAndDistrict(@Param("state") String state, @Param("district") String district);

    // Custom query to find nearest constituency by coordinates (if you have lat/lng data)
    @Query(value = "SELECT * FROM sitting_members_parliament c " +
            "WHERE c.latitude IS NOT NULL AND c.longitude IS NOT NULL " +
            "ORDER BY (6371 * acos(cos(radians(:lat)) * cos(radians(c.latitude)) * " +
            "cos(radians(c.longitude) - radians(:lng)) + sin(radians(:lat)) * " +
            "sin(radians(c.latitude)))) ASC LIMIT 1", nativeQuery = true)
    Optional<Constituency> findNearestConstituency(@Param("lat") double latitude, @Param("lng") double longitude);

    // Get all constituency names
    @Query("SELECT DISTINCT c.constituency FROM Constituency c ORDER BY c.constituency")
    List<String> findAllConstituencyNames();
}