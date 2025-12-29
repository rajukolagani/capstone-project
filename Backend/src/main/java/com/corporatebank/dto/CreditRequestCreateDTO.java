package com.corporatebank.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreditRequestCreateDTO {

    @NotBlank(message = "Client ID is required")
    private String clientId;

    @Min(value = 1, message = "Request amount must be greater than 0")
    private double requestAmount;

    @Min(value = 1, message = "Tenure must be at least 1 month")
    private int tenureMonths;

    @NotBlank(message = "Purpose is required")
    private String purpose;

    // getters & setters
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public double getRequestAmount() {
        return requestAmount;
    }

    public void setRequestAmount(double requestAmount) {
        this.requestAmount = requestAmount;
    }

    public int getTenureMonths() {
        return tenureMonths;
    }

    public void setTenureMonths(int tenureMonths) {
        this.tenureMonths = tenureMonths;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}
