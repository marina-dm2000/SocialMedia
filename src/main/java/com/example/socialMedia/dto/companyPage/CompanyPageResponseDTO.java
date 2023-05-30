package com.example.socialMedia.dto.companyPage;


import com.example.socialMedia.dto.user.UserResponseDTO;
import lombok.Data;

@Data
public class CompanyPageResponseDTO {
    private Long id;
    private String name;
    private String description;
    private UserResponseDTO owner;
    private String logoUrl;
}