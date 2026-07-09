package com.hectal.job.dto;

import com.hectal.job.domain.SocialPlatForm;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SocialLinkResponse {
    private SocialPlatForm socialPlatForm;
    private String url;
}
