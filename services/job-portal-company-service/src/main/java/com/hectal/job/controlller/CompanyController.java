package com.hectal.job.controlller;

import com.hectal.job.domain.CompanyStatus;
import com.hectal.job.domain.CompanyType;
import com.hectal.job.domain.IndustryType;
import com.hectal.job.dto.CompanyRequest;
import com.hectal.job.dto.CompanyResponse;
import com.hectal.job.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyResponse> createCompany(
            @RequestHeader("X-User-Id") Long ownerId,
            @RequestBody @Valid CompanyRequest companyRequest) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(companyService.createCompany(ownerId, companyRequest));
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyResponse> getCompanyById(@PathVariable Long companyId) throws Exception {
        return ResponseEntity.ok(companyService.getCompanyById(companyId));
    }

    @GetMapping("/my-company")
    public ResponseEntity<CompanyResponse> getMyCompany(@RequestHeader("X-User-Id") Long ownerId) throws Exception {
        return ResponseEntity.ok(companyService.getMyCompany(ownerId));
    }

    @GetMapping
    public ResponseEntity<List<CompanyResponse>> getAllCompanies(
            @RequestParam(required = false) CompanyType companyType,
            @RequestParam(required = false) IndustryType industryType,
            @RequestParam(required = false) CompanyStatus status) {
        return ResponseEntity.ok(companyService.getAllCompany(companyType, industryType, status));
    }

    @PutMapping("/{companyId}")
    public ResponseEntity<CompanyResponse> updateCompany(
            @PathVariable Long companyId,
            @RequestHeader("X-User-Id") Long ownerId,
            @RequestBody CompanyRequest companyRequest) throws Exception {
        return ResponseEntity.ok(companyService.updateCompany(companyId, ownerId, companyRequest));
    }

    @PatchMapping("/{companyId}/verify")
    public ResponseEntity<CompanyResponse> verifyCompany(@PathVariable Long companyId) throws Exception {
        return ResponseEntity.ok(companyService.verifyCompany(companyId));
    }

    @PatchMapping("/{companyId}/deactivate")
    public ResponseEntity<CompanyResponse> deactivateCompany(@PathVariable Long companyId) throws Exception {
        return ResponseEntity.ok(companyService.deActiveCompany(companyId));
    }

    @DeleteMapping("/{companyId}")
    public ResponseEntity<Void> deleteCompany(
            @PathVariable Long companyId,
            @RequestHeader("X-User-Id") Long ownerId) throws Exception {
        companyService.deleteCompany(companyId, ownerId);
        return ResponseEntity.noContent().build();
    }
}
