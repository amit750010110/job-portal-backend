package com.hectal.job.repository;

import com.hectal.job.domain.CompanyStatus;
import com.hectal.job.domain.CompanyType;
import com.hectal.job.domain.IndustryType;
import com.hectal.job.modal.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByOwnerId(Long ownerId);

    boolean existsByOwnerId(Long ownerId);

    boolean existsByName(String name);

    boolean existsBySlug(String slug);

    boolean existsByRegistrationNumber(String registrationNumber);

    @Query("SELECT c FROM Company c WHERE " +
            "(:companyType IS NULL OR c.companyType = :companyType) AND " +
            "(:industryType IS NULL OR c.industryType = :industryType) AND " +
            "(:status IS NULL OR c.status = :status)")
    List<Company> findByFilter(
            @Param("companyType") CompanyType companyType,
            @Param("industryType") IndustryType industryType,
            @Param("status") CompanyStatus status
    );

    Company id(Long id);

    Optional<Company> getCompaniesById(Long id);

    Company getCompanyEntityById(Long companyId);
}

