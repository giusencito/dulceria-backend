package com.example.compete.application.dto;

import lombok.Data;

@Data
public class PayUpResponseDTO {
    private String transactionId;
    private String operationDate;
    private String state;
    private String statusCodeReference;
}
