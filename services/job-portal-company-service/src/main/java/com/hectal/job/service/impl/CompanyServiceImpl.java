package com.hectal.job.service.impl;

import com.hectal.job.domain.CompanyStatus;
import com.hectal.job.domain.CompanyType;
import com.hectal.job.domain.IndustryType;
import com.hectal.job.dto.CompanyRequest;
import com.hectal.job.dto.CompanyResponse;
import com.hectal.job.dto.SocialLinkResponse;
import com.hectal.job.mapper.CompanyMapper;
import com.hectal.job.modal.Company;
import com.hectal.job.modal.SocialLink;
import com.hectal.job.repository.CompanyRepository;
import com.hectal.job.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;

    @Override
    public CompanyResponse createCompany(Long ownerId, CompanyRequest companyRequest) throws Exception {
        if (companyRepository.existsByOwnerId(ownerId)) {
            throw new Exception("You already have a company Registered " + "Only one company per account is allowed");

        }
        if (companyRepository.existsByName(companyRequest.getName())) {
            throw new Exception("Company name already Exist, try another name");
        }

        if (companyRequest.getRegistrationNumber() != null && companyRepository.existsByRegistrationNumber(companyRequest.getRegistrationNumber())) {
            throw new Exception("Company already exists Please chose a different registration number");
        }

        String slug = generateUniqueSlug(companyRequest.getName());
        Company company = Company.builder()
                .name(companyRequest.getName())
                .slug(slug)
                .tagLine(companyRequest.getTagline())
                .description(companyRequest.getDescription())
                .logoUrl(companyRequest.getLogoUrl())
                .coverImageUrl(companyRequest.getCoverImageUrl())
                .website(companyRequest.getWebsite())
                .email(companyRequest.getEmail())
                .phone(companyRequest.getPhone())
                .foundedYear(companyRequest.getFoundedYear())
                .companySize(companyRequest.getCompanySize())
                .companyType(companyRequest.getCompanyType())
                .industryType(companyRequest.getIndustryType())
                .registrationNumber(companyRequest.getRegistrationNumber())
                .ownerId(ownerId)
                .status(CompanyStatus.PENDING_VERIFICATION)
                .active(true)
                .socialLink(companyRequest.getSocialLinks() != null ?
                        companyRequest.getSocialLinks().stream()
                                .map(link -> SocialLink.builder()
                                        .socialPlatForm(link.getSocialPlatForm())
                                        .url(link.getUrl())
                                        .build())
                                .toList() : new ArrayList<>())
                .build();

        Company savedCompany = companyRepository.save(company);
        return CompanyMapper.mapToResponse(savedCompany);
    }

    private String generateUniqueSlug(String name) {
        String base = name.toLowerCase().replaceAll("[^a-z0-9\\s-]", "").trim().replaceAll("[\\s-]+", "-");

        if (!companyRepository.existsBySlug(base)) {
            return base;
        }

        int counter = 1;
        while (companyRepository.existsBySlug(base + "-" + counter)) {
            counter++;
        }

        return base + "-" + counter;
    }

    @Override
    public CompanyResponse getCompanyById(Long id) throws Exception {
        Company company = companyRepository.getCompaniesById(id).orElseThrow(() -> new Exception("Company not found with id"));
        return CompanyMapper.mapToResponse(company);
    }

    @Override
    public CompanyResponse getMyCompany(Long ownerId) throws Exception {
        Company company = companyRepository.findByOwnerId(ownerId).orElseThrow(() -> new Exception("Company exist for owner " + ownerId));
        return CompanyMapper.mapToResponse(company);
    }

    @Override
    public List<CompanyResponse> getAllCompany(CompanyType companyType, IndustryType industryType, CompanyStatus companyStatus) {
        return companyRepository.findByFilter(companyType, industryType, companyStatus)
                .stream()
                .map(CompanyMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CompanyResponse updateCompany(Long companyId, Long ownerId, CompanyRequest req) throws Exception {
        Company company = getCompanyEntityById(companyId);

        // Verify ownership
        if (!company.getOwnerId().equals(ownerId)) {
            throw new Exception("You are not authorized to update this company");
        }

        // Check if name is being changed and if new name already exists
        if (req.getName() != null && !req.getName().equals(company.getName())) {
            if (companyRepository.existsByName(req.getName())) {
                throw new Exception("Company name already exists, try another name");
            }
            company.setName(req.getName());
            // Regenerate slug when name changes
            company.setSlug(generateUniqueSlug(req.getName()));
        }

        // Check if registration number is being changed
        if (req.getRegistrationNumber() != null &&
                !req.getRegistrationNumber().equals(company.getRegistrationNumber())) {
            if (companyRepository.existsByRegistrationNumber(req.getRegistrationNumber())) {
                throw new Exception("Registration number already exists");
            }
            company.setRegistrationNumber(req.getRegistrationNumber());
        }

        // Update other fields
        if (req.getTagline() != null) {
            company.setTagLine(req.getTagline());
        }
        if (req.getDescription() != null) {
            company.setDescription(req.getDescription());
        }
        if (req.getLogoUrl() != null) {
            company.setLogoUrl(req.getLogoUrl());
        }
        if (req.getCoverImageUrl() != null) {
            company.setCoverImageUrl(req.getCoverImageUrl());
        }
        if (req.getWebsite() != null) {
            company.setWebsite(req.getWebsite());
        }
        if (req.getEmail() != null) {
            company.setEmail(req.getEmail());
        }
        if (req.getPhone() != null) {
            company.setPhone(req.getPhone());
        }
        if (req.getFoundedYear() != null) {
            company.setFoundedYear(req.getFoundedYear());
        }
        if (req.getCompanySize() != null) {
            company.setCompanySize(req.getCompanySize());
        }
        if (req.getCompanyType() != null) {
            company.setCompanyType(req.getCompanyType());
        }
        if (req.getIndustryType() != null) {
            company.setIndustryType(req.getIndustryType());
        }

        // Update social links
        if (req.getSocialLinks() != null) {
            List<SocialLink> socialLinks = req.getSocialLinks().stream()
                    .map(link -> SocialLink.builder()
                            .socialPlatForm(link.getSocialPlatForm())
                            .url(link.getUrl())
                            .build())
                    .collect(Collectors.toList());
            company.setSocialLink(socialLinks);
        }

        Company updatedCompany = companyRepository.save(company);
        return CompanyMapper.mapToResponse(updatedCompany);
    }

    @Override
    public CompanyResponse verifyCompany(Long companyId) throws Exception {
        Company company = getCompanyEntityById(companyId);
        company.setStatus(CompanyStatus.ACTIVE);
        company.setVerified(true);
        company.setVerifiedAt(java.time.LocalDateTime.now());
        Company verifiedCompany = companyRepository.save(company);
        return CompanyMapper.mapToResponse(verifiedCompany);
    }

    @Override
    public void deleteCompany(Long companyId, Long ownerId) throws Exception {
        Company company = getCompanyEntityById(companyId);
        assertOwner(company, ownerId);
        companyRepository.delete(company);
    }

    private void assertOwner(Company company, Long ownerId) throws Exception {
        if (!company.getOwnerId().equals(ownerId)) {
            throw new Exception("you are not the owner of this company");
        }
    }

    @Override
    public CompanyResponse deActiveCompany(Long companyId) throws Exception {
        Company company = getCompanyEntityById(companyId);
        company.setStatus(CompanyStatus.SUSPENDED);
        company.setActive(false);
        Company deactivatedCompany = companyRepository.save(company);
        return CompanyMapper.mapToResponse(deactivatedCompany);
    }

    @Override
    public Company getCompanyEntityById(Long id) throws Exception {
        return companyRepository.getCompaniesById(id)
                .orElseThrow(() -> new Exception("Company not found with id: " + id));
    }


}
