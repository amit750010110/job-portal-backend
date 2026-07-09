package com.hectal.job.service;

import com.hectal.job.domain.CompanyStatus;
import com.hectal.job.domain.CompanyType;
import com.hectal.job.domain.IndustryType;
import com.hectal.job.dto.CompanyRequest;
import com.hectal.job.dto.CompanyResponse;
import com.hectal.job.modal.Company;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompanyService {
    CompanyResponse createCompany(Long ownerId, CompanyRequest companyRequest) throws Exception;

    CompanyResponse getCompanyById(Long id) throws Exception;

    CompanyResponse getMyCompany(Long ownerId) throws Exception;

    List<CompanyResponse> getAllCompany(CompanyType companyType, IndustryType industryType, CompanyStatus companyStatus);

    CompanyResponse updateCompany(Long companyId, Long ownerId, CompanyRequest req) throws Exception;

    CompanyResponse verifyCompany(Long companyId) throws Exception;

    void deleteCompany(Long companyId ,Long ownerId) throws Exception;

    CompanyResponse deActiveCompany(Long companyId) throws Exception;

    Company getCompanyEntityById(Long id) throws Exception;
}
