package com.example.compete.application.dto;


import com.example.compete.domain.enumeration.DocumentType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentDTO {
    private String email;
    private String name;
    private String documentNumber;
    private String cardNumber;
    private DocumentType documentType;
    private String expiration;
    private String cvv;
    private BigDecimal total;
}
