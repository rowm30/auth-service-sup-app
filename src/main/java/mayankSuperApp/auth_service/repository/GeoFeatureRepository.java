package mayankSuperApp.auth_service.repository;



import mayankSuperApp.auth_service.entity.GeoFeature;
import org.locationtech.jts.geom.Geometry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/** CRUD + a couple of geo helpers */
public interface GeoFeatureRepository extends JpaRepository<GeoFeature, Long> {

    List<GeoFeature> findByStateNameIgnoreCase(String stateName);

    List<GeoFeature> findByPcCategory(String pcCategory);

    /** Bounding-box search (PostGIS / MySQL-spatial) */
    @Query(value = """
            SELECT g.* 
            FROM auth_db.geo_features g 
            WHERE ST_Within(g.geom, ST_MakeEnvelope(:minLon, :minLat, :maxLon, :maxLat, 4326))
            """,
            nativeQuery = true)
    List<GeoFeature> findWithinBBox(@Param("minLon") double minLon,
                                    @Param("minLat") double minLat,
                                    @Param("maxLon") double maxLon,
                                    @Param("maxLat") double maxLat);

    @Query(value = """
        SELECT g.pc_name 
        FROM auth_db.geo_features g
        WHERE ST_Contains(
            g.geom,
            ST_GeomFromText(CONCAT('POINT(', :latitude, ' ', :longitude, ')'), 4326)
        )
        """,
            nativeQuery = true)
    List<String> findPcNameByCoordinates(@Param("latitude") double latitude,
                                         @Param("longitude") double longitude);

}
