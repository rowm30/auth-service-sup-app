package mayankSuperApp.auth_service.dto;


public class ConstituencyResponse {
    private String constituency;
    private String state;
    private String district;
    private boolean success;
    private String message;
    private String method; // How the constituency was determined
    private double confidence; // Confidence level (0-1)

    public ConstituencyResponse() {}

    public ConstituencyResponse(String constituency, String state, String district,
                                boolean success, String message, String method, double confidence) {
        this.constituency = constituency;
        this.state = state;
        this.district = district;
        this.success = success;
        this.message = message;
        this.method = method;
        this.confidence = confidence;
    }

    // Getters and Setters
    public String getConstituency() {
        return constituency;
    }

    public void setConstituency(String constituency) {
        this.constituency = constituency;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }
}
