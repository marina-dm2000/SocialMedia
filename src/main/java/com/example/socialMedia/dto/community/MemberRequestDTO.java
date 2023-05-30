package com.example.socialMedia.dto.community;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MemberRequestDTO {
    @NotNull
    private Long userId;
}
