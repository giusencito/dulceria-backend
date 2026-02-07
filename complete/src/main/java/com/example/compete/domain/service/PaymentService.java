package com.example.compete.domain.service;

import com.example.compete.application.dto.OrderCompleteDTO;
import com.example.compete.application.dto.PayUpResponseDTO;
import com.example.compete.application.dto.PaymentDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface PaymentService {
    PayUpResponseDTO paymentValidation (PaymentDTO request) throws JsonProcessingException;
    void paymentConfirmation(OrderCompleteDTO request) throws JsonProcessingException;
}
