package mayankSuperApp.auth_service.service;

import mayankSuperApp.auth_service.entity.GeoFeature;

import java.util.List;

public interface GeoFeatureService {

    GeoFeature getById(Long id);

    List<GeoFeature> getAll();

    List<GeoFeature> getByState(String stateName);

    List<GeoFeature> getByCategory(String pcCategory);

    List<GeoFeature> getWithinBBox(double minLon, double minLat,
                                      double maxLon, double maxLat);

    List<String> getPcNameByCoordinates(double latitude, double longitude);

}
