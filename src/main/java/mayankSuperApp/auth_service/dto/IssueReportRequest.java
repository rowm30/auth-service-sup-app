package mayankSuperApp.auth_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request payload for reporting a road issue")
public class IssueReportRequest {

    @NotBlank
    @Schema(description = "Title of the issue", example = "Pothole on Main Street")
    private String title;

    @NotBlank
    @Schema(description = "Detailed description", example = "Large pothole causing traffic disruption near the intersection")
    private String description;

    @NotBlank
    @Schema(description = "Road or location", example = "Main Street")
    private String roads;

    @NotBlank
    @Schema(description = "Priority level", example = "High Priority")
    private String low;

    @NotBlank
    @Schema(description = "Responsible MP", example = "John Smith")
    private String mp;

    @NotBlank
    @Schema(description = "Base64 encoded image string")
    private String image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRoads() {
        return roads;
    }

    public void setRoads(String roads) {
        this.roads = roads;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getMp() {
        return mp;
    }

    public void setMp(String mp) {
        this.mp = mp;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
