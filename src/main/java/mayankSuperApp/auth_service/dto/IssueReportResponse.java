package mayankSuperApp.auth_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response after creating an issue report")
public class IssueReportResponse {

    @Schema(description = "Generated report id")
    private Long id;

    @Schema(description = "Response message")
    private String message;

    public IssueReportResponse() {
    }

    public IssueReportResponse(Long id, String message) {
        this.id = id;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
