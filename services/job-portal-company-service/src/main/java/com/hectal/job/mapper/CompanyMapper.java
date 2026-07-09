package com.hectal.job.mapper;

import com.hectal.job.domain.CompanyStatus;
import com.hectal.job.dto.CompanyResponse;
import com.hectal.job.dto.SocialLinkResponse;
import com.hectal.job.modal.Company;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class CompanyMapper {
    public static CompanyResponse mapToResponse(Company company) {
        return CompanyResponse.builder()
                .id(company.getId())
                .name(company.getName())
                .slug(company.getSlug())
                .tagline(company.getTagLine())
                .description(company.getDescription())
                .logoUrl(company.getLogoUrl())
                .coverImageUrl(company.getCoverImageUrl())
                .website(company.getWebsite())
                .email(company.getEmail())
                .phone(company.getPhone())
                .foundedYear(company.getFoundedYear())
                .companySize(company.getCompanySize())
                .companyType(company.getCompanyType())
                .industryType(company.getIndustryType())
                .status(company.getStatus())
                .verified(company.isVerified())
                .active(company.getActive())
                .ownerId(company.getOwnerId())
                .socialLinks(company.getSocialLink() != null ?
                        company.getSocialLink().stream()
                                .map(link -> SocialLinkResponse.builder()
                                        .socialPlatForm(link.getSocialPlatForm())
                                        .url(link.getUrl())
                                        .build())
                                .collect(Collectors.toList()) : new ArrayList<>())
                .createdAt(company.getCreatedAt())
                .updatedAt(company.getUpdatedAt())
                .verifiedAt(company.getVerifiedAt())
                .build();
    }
}
