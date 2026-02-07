package com.example.compete.application.dto;

import com.example.compete.domain.enumeration.DocumentType;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class OrderDTO {

    private Long id;

    private String email;

    private String name;

    private String documentNumber;

    private DocumentType documentType;

    private String transactionId;

    private LocalDateTime operationDate;
}
