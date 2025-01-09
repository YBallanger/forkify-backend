package com.forkify_backend.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {
    @NotBlank
    String userId;

    @NotBlank
    String email;

    @NotBlank
    String username;
}
