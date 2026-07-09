package com.hectal.job.modal;

import com.hectal.job.domain.CompanySize;
import com.hectal.job.domain.CompanyStatus;
import com.hectal.job.domain.CompanyType;
import com.hectal.job.domain.IndustryType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String slug;

    private String tagLine;

    private String description;

    private String logoUrl;

    private String coverImageUrl;

    private String website;

    private Integer foundedYear;

    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    private CompanySize companySize;

    @Enumerated(EnumType.STRING)
    private CompanyType companyType;

    @Enumerated(EnumType.STRING)
    private IndustryType industryType;

    @Enumerated(EnumType.STRING)
    private CompanyStatus status;

    @Column(unique = true)
    private String registrationNumber;

    @Column(nullable = false, unique = true)
    private Long ownerId;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<SocialLink> socialLink = new ArrayList<>();

    private Boolean active = true;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime verifiedAt;

    private boolean isVerified = false;
}
