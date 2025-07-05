package mayankSuperApp.auth_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class GoogleGeocodingResponse {
    private List<Result> results;
    private String status;

    public static class Result {
        @JsonProperty("address_components")
        private List<AddressComponent> addressComponents;

        @JsonProperty("formatted_address")
        private String formattedAddress;

        public static class AddressComponent {
            @JsonProperty("long_name")
            private String longName;

            @JsonProperty("short_name")
            private String shortName;

            private List<String> types;

            // Getters and Setters
            public String getLongName() { return longName; }
            public void setLongName(String longName) { this.longName = longName; }

            public String getShortName() { return shortName; }
            public void setShortName(String shortName) { this.shortName = shortName; }

            public List<String> getTypes() { return types; }
            public void setTypes(List<String> types) { this.types = types; }
        }

        // Getters and Setters
        public List<AddressComponent> getAddressComponents() { return addressComponents; }
        public void setAddressComponents(List<AddressComponent> addressComponents) {
            this.addressComponents = addressComponents;
        }

        public String getFormattedAddress() { return formattedAddress; }
        public void setFormattedAddress(String formattedAddress) { this.formattedAddress = formattedAddress; }
    }

    // Getters and Setters
    public List<Result> getResults() { return results; }
    public void setResults(List<Result> results) { this.results = results; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}