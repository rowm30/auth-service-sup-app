package mayankSuperApp.auth_service.service.impl;

import mayankSuperApp.auth_service.entity.GeoFeature;
import mayankSuperApp.auth_service.repository.GeoFeatureRepository;
import mayankSuperApp.auth_service.service.GeoFeatureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class GeoFeatureServiceImpl implements GeoFeatureService {

    private static final Logger log = LoggerFactory.getLogger(GeoFeatureServiceImpl.class);

    private final GeoFeatureRepository repo;

    public GeoFeatureServiceImpl(GeoFeatureRepository repo) {
        this.repo = repo;
    }

    // ---------- public API ----------

    @Override
    public GeoFeature getById(Long id) {
        log.debug("Fetching GeoFeature id={}", id);
        return repo.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new IllegalArgumentException("GeoFeature not found"));
    }

    @Override
    public List<GeoFeature> getAll() {
        log.debug("Fetching all GeoFeatures");
        return repo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<GeoFeature> getByState(String stateName) {
        log.debug("Fetching GeoFeatures for state '{}'", stateName);
        return repo.findByStateNameIgnoreCase(stateName)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<GeoFeature> getByCategory(String pcCategory) {
        log.debug("Fetching GeoFeatures for category '{}'", pcCategory);
        return repo.findByPcCategory(pcCategory)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<GeoFeature> getWithinBBox(double minLon, double minLat,
                                             double maxLon, double maxLat) {
        log.debug("Fetching GeoFeatures within BBox [{},{}]-[{},{}]",
                minLon, minLat, maxLon, maxLat);
        return repo.findWithinBBox(minLon, minLat, maxLon, maxLat)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    // ---------- mapper ----------

    private GeoFeature toDto(GeoFeature g) {
        return new GeoFeature(
                g.getId(),
                g.getPcId(),
                g.getStCode(),
                g.getStateName(),
                g.getPcNo(),
                g.getPcName(),
                g.getPcCategory(),
                g.getWikidataQid(),
                g.getStatus(),
                g.getElectionPhase(),
                g.getElectionDate()
        );
    }

    @Override
    public List<String> getPcNameByCoordinates(double latitude, double longitude) {
        log.debug("Fetching PC name for coordinates: ({}, {})", latitude, longitude);
        return repo.findPcNameByCoordinates(latitude, longitude);
    }

}