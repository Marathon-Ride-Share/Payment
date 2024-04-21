package com.example.payment.dto;

import java.util.List;

public class ValidationResultDto {
    private boolean isValid;
    private List<String> errors;

    // Constructors
    public ValidationResultDto() {
    }

    public ValidationResultDto(boolean isValid, List<String> errors) {
        this.isValid = isValid;
        this.errors = errors;
    }

    // Getters and setters
    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
