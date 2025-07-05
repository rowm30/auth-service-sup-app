package mayankSuperApp.auth_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sitting_members_parliament", schema = "auth_db")
public class SittingMember {

    @Id
    private Long id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "name_of_member")
    private String nameOfMember;

    @Column(name = "party_name")
    private String partyName;

    private String constituency;

    private String state;

    @Column(name = "membership_status")
    private String membershipStatus;

    @Column(name = "lok_sabha_terms")
    private Integer lokSabhaTerms;

    private String district;

    private Double latitude;

    private Double longitude;

    // --- Constructors ---

    public SittingMember() {}

    public SittingMember(Long id, String lastName, String nameOfMember, String partyName,
                         String constituency, String state, String membershipStatus,
                         Integer lokSabhaTerms, String district, Double latitude, Double longitude) {
        this.id = id;
        this.lastName = lastName;
        this.nameOfMember = nameOfMember;
        this.partyName = partyName;
        this.constituency = constituency;
        this.state = state;
        this.membershipStatus = membershipStatus;
        this.lokSabhaTerms = lokSabhaTerms;
        this.district = district;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // --- Getters and Setters ---

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getNameOfMember() { return nameOfMember; }

    public void setNameOfMember(String nameOfMember) { this.nameOfMember = nameOfMember; }

    public String getPartyName() { return partyName; }

    public void setPartyName(String partyName) { this.partyName = partyName; }

    public String getConstituency() { return constituency; }

    public void setConstituency(String constituency) { this.constituency = constituency; }

    public String getState() { return state; }

    public void setState(String state) { this.state = state; }

    public String getMembershipStatus() { return membershipStatus; }

    public void setMembershipStatus(String membershipStatus) { this.membershipStatus = membershipStatus; }

    public Integer getLokSabhaTerms() { return lokSabhaTerms; }

    public void setLokSabhaTerms(Integer lokSabhaTerms) { this.lokSabhaTerms = lokSabhaTerms; }

    public String getDistrict() { return district; }

    public void setDistrict(String district) { this.district = district; }

    public Double getLatitude() { return latitude; }

    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }

    public void setLongitude(Double longitude) { this.longitude = longitude; }
}