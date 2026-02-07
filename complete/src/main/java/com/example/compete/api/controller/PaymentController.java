package com.example.compete.api.controller;


import com.example.compete.application.dto.OrderCompleteDTO;
import com.example.compete.application.dto.PayUpResponseDTO;
import com.example.compete.application.dto.PaymentDTO;
import com.example.compete.domain.service.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@CrossOrigin
public class PaymentController {

    @Autowired
    private PaymentService paymentService;


    @ApiOperation(value = "Check Payment",notes = "Este consulta valida la compra con PAYUP")
    @PostMapping("/check")
    public PayUpResponseDTO checkPayment(@RequestBody PaymentDTO request) throws JsonProcessingException {
        return paymentService.paymentValidation(request);
    }

    @ApiOperation(value = "Confirm Payment",notes = "Este consulta para insertar la orden y su detalle en la base de datos")
    @PostMapping("/confirm")
    public void postPayments(@RequestBody OrderCompleteDTO request) throws JsonProcessingException {
        paymentService.paymentConfirmation(request);
    }
}
