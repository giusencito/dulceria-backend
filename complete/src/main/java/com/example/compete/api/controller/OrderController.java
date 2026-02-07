package com.example.compete.api.controller;


import com.example.compete.application.dto.OrderDTO;
import com.example.compete.application.dto.ResultDetailDTO;
import com.example.compete.domain.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;


    @ApiOperation(value = "Get Orders BY ID",notes = "Este consulta consiste en obtener ordenes segun el correo")
    @GetMapping("email/{email}")
    public List<OrderDTO> getOrdersByEmail(@PathVariable String email) {
        return orderService.ordersByEmail(email);
    }


    @ApiOperation(value = "Get Details By OrderId",notes = "Este consulta consiste en obtener ordenes según orderId")
    @GetMapping("orderId/{orderId}")
    public List<ResultDetailDTO> getOrdersByEmail(@PathVariable Long orderId) throws JsonProcessingException {
        return orderService.detailByOrderId(orderId);
    }
}
