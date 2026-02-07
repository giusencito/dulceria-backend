package com.example.compete.application.dto;

import com.example.compete.domain.enumeration.DocumentType;
import lombok.Data;


import java.util.List;

@Data
public class OrderCompleteDTO {

    private String email;

    private String name;

    private String documentNumber;

    private DocumentType documentType;

    private String transactionId;

    private String operationDate;

    private List<OrderDetailDTO> details;
}
