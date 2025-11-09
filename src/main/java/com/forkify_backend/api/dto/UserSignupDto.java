package com.forkify_backend.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserSignupDto {
    @NotBlank
    private String id;
    
    @NotBlank
    private String email;

    @NotBlank
    private String username;

}
