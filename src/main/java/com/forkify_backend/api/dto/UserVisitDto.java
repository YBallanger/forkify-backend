package com.forkify_backend.api.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserVisitDto {

    @NotNull
    private String userId; //TODO a remplacer quand on pourra le recuperer avec un token de connexion

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotNull
    private LocalDate visitDate;

    @NotNull
    @PositiveOrZero
    private Double amountSpent;

    @NotNull
    @PositiveOrZero
    private Double rating;
}
