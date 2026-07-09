package com.hectal.job.modal;

import com.hectal.job.domain.SocialPlatForm;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SocialLink {
    private SocialPlatForm socialPlatForm;
    private String url;
}
