package mayankSuperApp.auth_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mayankSuperApp.auth_service.dto.IssueReportRequest;
import mayankSuperApp.auth_service.dto.IssueReportResponse;
import mayankSuperApp.auth_service.service.IssueReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report")
@Tag(name = "Issue Report", description = "Endpoints to submit road issue reports")
public class IssueReportController {

    private final IssueReportService service;

    @Autowired
    public IssueReportController(IssueReportService service) {
        this.service = service;
    }

    @Operation(summary = "Submit a new issue report")
    @ApiResponse(responseCode = "200", description = "Report created successfully")
    @PostMapping
    public ResponseEntity<IssueReportResponse> createReport(@Valid @RequestBody IssueReportRequest request) {
        IssueReportResponse response = service.createReport(request);
        return ResponseEntity.ok(response);
    }
}
