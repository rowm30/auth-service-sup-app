package mayankSuperApp.auth_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import mayankSuperApp.auth_service.dto.AuthResponse;
import mayankSuperApp.auth_service.entity.GeoFeature;
import mayankSuperApp.auth_service.service.GeoFeatureService;
import org.hibernate.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/geo-features")
@Tag(name = "Geo-Features", description = "Parliament constituency geometry endpoints")
public class GeoFeatureController {

    private static final Logger logger = LoggerFactory.getLogger(GeoFeatureController.class);

    private final GeoFeatureService service;

    public GeoFeatureController(GeoFeatureService service) {
        this.service = service;
    }

    // ---------- basic CRUD ----------

    @Operation(summary = "List all features")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping
    public ResponseEntity<List<GeoFeature>> listAll() {
        logger.debug("GET /geo-features");
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Get feature by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = GeoFeature.class))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class)))
    })


    // ---------- filters ----------


    @GetMapping("/state/{stateName}")
    public ResponseEntity<List<GeoFeature>> byState(@PathVariable String stateName) {
        logger.debug("GET /geo-features/state/{}", stateName);
        return ResponseEntity.ok(service.getByState(stateName));
    }

    @Operation(summary = "Search by category (GEN/SC/ST)")
    @GetMapping("/category/{pcCategory}")
    public ResponseEntity<List<GeoFeature>> byCategory(@PathVariable String pcCategory) {
        logger.debug("GET /geo-features/category/{}", pcCategory);
        return ResponseEntity.ok(service.getByCategory(pcCategory));
    }

    @Operation(summary = "Search within bounding box")
    @GetMapping("/bbox")
    public ResponseEntity<List<GeoFeature>> withinBBox(
            @RequestParam double minLon,
            @RequestParam double minLat,
            @RequestParam double maxLon,
            @RequestParam double maxLat) {

        logger.debug("GET /geo-features/bbox");
        return ResponseEntity.ok(
                service.getWithinBBox(minLon, minLat, maxLon, maxLat));
    }

    @Operation(summary = "Get PC name by latitude and longitude")
    @GetMapping("/pc-name")
    public ResponseEntity<List<String>> getPcNameByCoordinates(
            @RequestParam double latitude,
            @RequestParam double longitude) {

        logger.debug("GET /geo-features/pc-name?latitude={}&longitude={}", latitude, longitude);
        return ResponseEntity.ok(service.getPcNameByCoordinates(latitude, longitude));
    }

}