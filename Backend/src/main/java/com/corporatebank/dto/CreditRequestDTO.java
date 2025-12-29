package com.corporatebank.dto;

import jakarta.validation.constraints.*;

public class CreditRequestDTO {

    @NotBlank
    private String clientId;

    @Positive
    private double requestAmount;

    @Positive
    private int tenureMonths;

    @NotBlank
    private String purpose;
}
