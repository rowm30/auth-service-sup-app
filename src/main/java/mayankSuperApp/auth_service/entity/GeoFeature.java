package mayankSuperApp.auth_service.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.Id;
import org.locationtech.jts.geom.Geometry;

import java.time.LocalDate;

@Entity
@Table(name = "geo_features", schema = "auth_db")
public class GeoFeature {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pc_id")
    private Integer pcId;

    @Column(name = "st_code")
    private Integer stCode;

    @Column(name = "st_name")
    private String stateName;

    @Column(name = "pc_no")
    private Integer pcNo;

    @Column(name = "pc_name")
    private String pcName;

    @Column(name = "pc_category")
    private String pcCategory;

    @Column(name = "wikidata_qid")
    private String wikidataQid;

    @Column(name = "status")
    private String status;      // If it is an enum, map to Enum later.

    @Column(name = "election_phase")
    private Integer electionPhase;

    @Column(name = "election_date")
    private LocalDate electionDate;

    /** Spatial column (POINT / MULTIPOLYGON / …) */

    @Column(name = "geom", columnDefinition = "geometry")   // use geometry(Point,4326) if you know the SRID
    private Geometry geom;

    public GeoFeature(Long id, Integer pcId, Integer stCode, String stateName, Integer pcNo, String pcName, String pcCategory, String wikidataQid, String status, Integer electionPhase, LocalDate electionDate) {
    }

    public GeoFeature() {

    }

    public GeoFeature(Long id, Integer pcId, Integer stCode, String stateName, Integer pcNo, String pcName, String pcCategory, String wikidataQid, String status, Integer electionPhase, LocalDate electionDate, Geometry geom) {
        this.id = id;
        this.pcId = pcId;
        this.stCode = stCode;
        this.stateName = stateName;
        this.pcNo = pcNo;
        this.pcName = pcName;
        this.pcCategory = pcCategory;
        this.wikidataQid = wikidataQid;
        this.status = status;
        this.electionPhase = electionPhase;
        this.electionDate = electionDate;
        this.geom = geom;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPcId() {
        return pcId;
    }

    public void setPcId(Integer pcId) {
        this.pcId = pcId;
    }

    public Integer getStCode() {
        return stCode;
    }

    public void setStCode(Integer stCode) {
        this.stCode = stCode;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Integer getPcNo() {
        return pcNo;
    }

    public void setPcNo(Integer pcNo) {
        this.pcNo = pcNo;
    }

    public String getPcName() {
        return pcName;
    }

    public void setPcName(String pcName) {
        this.pcName = pcName;
    }

    public String getPcCategory() {
        return pcCategory;
    }

    public void setPcCategory(String pcCategory) {
        this.pcCategory = pcCategory;
    }

    public String getWikidataQid() {
        return wikidataQid;
    }

    public void setWikidataQid(String wikidataQid) {
        this.wikidataQid = wikidataQid;
    }

    public Integer getElectionPhase() {
        return electionPhase;
    }

    public void setElectionPhase(Integer electionPhase) {
        this.electionPhase = electionPhase;
    }

    public LocalDate getElectionDate() {
        return electionDate;
    }

    public void setElectionDate(LocalDate electionDate) {
        this.electionDate = electionDate;
    }

    public Geometry getGeom() {
        return geom;
    }

    public void setGeom(Geometry geom) {
        this.geom = geom;
    }

    /* --- getters & setters omitted for brevity --- */
}