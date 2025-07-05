package mayankSuperApp.auth_service.repository;

import mayankSuperApp.auth_service.entity.SittingMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SittingMemberRepository extends JpaRepository<SittingMember, Long> {

    @Query(value = "SELECT * FROM auth_db.sitting_members_parliament WHERE constituency = :constituency", nativeQuery = true)
    List<SittingMember> findByConstituency(@Param("constituency") String constituency);
}
