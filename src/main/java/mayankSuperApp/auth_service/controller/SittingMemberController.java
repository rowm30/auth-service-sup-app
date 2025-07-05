package mayankSuperApp.auth_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import mayankSuperApp.auth_service.entity.SittingMember;
import mayankSuperApp.auth_service.service.SittingMemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sitting-members")
@Tag(name = "Sitting Members", description = "Parliament Members Info")
public class SittingMemberController {

    private final SittingMemberService service;

    public SittingMemberController(SittingMemberService service) {
        this.service = service;
    }

    @Operation(summary = "Get sitting MPs by constituency name")
    @GetMapping("/by-constituency")
    public ResponseEntity<List<SittingMember>> getByConstituency(
            @RequestParam String constituency) {
        return ResponseEntity.ok(service.getMembersByConstituency(constituency));
    }
}
