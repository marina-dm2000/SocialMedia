package com.example.socialMedia.dto.companyPage;

import lombok.Data;

@Data
public class CompanyPageRequestDTO {
    private String name;
    private String description;
    private Long ownerId;
    private String logoUrl;
}
