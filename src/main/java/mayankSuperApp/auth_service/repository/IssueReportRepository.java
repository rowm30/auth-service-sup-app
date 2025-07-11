package mayankSuperApp.auth_service.repository;

import mayankSuperApp.auth_service.entity.IssueReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueReportRepository extends JpaRepository<IssueReport, Long> {
}
