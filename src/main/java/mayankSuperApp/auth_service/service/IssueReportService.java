package mayankSuperApp.auth_service.service;

import mayankSuperApp.auth_service.dto.IssueReportRequest;
import mayankSuperApp.auth_service.dto.IssueReportResponse;
import mayankSuperApp.auth_service.entity.IssueReport;
import mayankSuperApp.auth_service.repository.IssueReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class IssueReportService {

    private final IssueReportRepository repository;

    @Autowired
    public IssueReportService(IssueReportRepository repository) {
        this.repository = repository;
    }

    public IssueReportResponse createReport(IssueReportRequest request) {
        IssueReport report = new IssueReport();
        report.setTitle(request.getTitle());
        report.setDescription(request.getDescription());
        report.setRoads(request.getRoads());
        report.setLow(request.getLow());
        report.setMp(request.getMp());
        report.setImage(request.getImage());

        IssueReport saved = repository.save(report);
        return new IssueReportResponse(saved.getId(), "Report submitted successfully");
    }
}
