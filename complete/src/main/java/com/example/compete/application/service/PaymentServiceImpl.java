package com.example.compete.application.service;

import com.example.compete.application.dto.OrderCompleteDTO;
import com.example.compete.application.dto.PayUpResponseDTO;
import com.example.compete.application.dto.PaymentDTO;
import com.example.compete.domain.repository.OrderProcedureRepository;
import com.example.compete.domain.service.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${payu.apiUrl}")
    private String apiUrl;

    @Value("${payu.apiLogin}")
    private String apiLogin;

    @Value("${payu.apiKey}")
    private String apiKey;

    @Value("${payu.merchantId}")
    private String merchantId;

    @Value("${payu.accountId}")
    private String accountId;


    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private  ObjectMapper objectMapper;

    @Autowired
    private OrderProcedureRepository repository;


    @Override
    public PayUpResponseDTO paymentValidation(PaymentDTO request) throws JsonProcessingException {
        String referenceCode =  UUID.randomUUID().toString();
        Map<String, Object> payload = Map.of(
                "language", "es",
                "command", "SUBMIT_TRANSACTION",
                "merchant", Map.of(
                        "apiLogin", apiLogin,
                        "apiKey", apiKey
                ),
                "transaction", Map.of(
                        "order", Map.of(
                                "accountId", accountId,
                                "referenceCode", referenceCode,
                                "description", "Compra Dulcería",
                                "language", "es",
                                "signature", generateSignature(request.getTotal(),referenceCode),
                                "additionalValues", Map.of(
                                        "TX_VALUE", Map.of(
                                                "value", request.getTotal().setScale(2, RoundingMode.HALF_UP),
                                                "currency", "PEN"
                                        )
                                )
                        ),
                        "payer", Map.of(
                                "fullName", request.getName(),
                                "emailAddress", request.getEmail(),
                                "dniNumber", request.getDocumentNumber()
                        ),
                        "creditCard", Map.of(
                                "number", request.getCardNumber(),
                                "securityCode", request.getCvv(),
                                "expirationDate", getYearMonth(request.getExpiration()),
                                "name", "APPROVED"
                        ),
                        "type", "AUTHORIZATION_AND_CAPTURE",
                        "paymentMethod", "VISA"
                ),
                "test", true
        );
        ResponseEntity<Map> response =
                restTemplate.postForEntity(apiUrl, payload, Map.class);
        Map transactionResponse =
                (Map) response.getBody().get("transactionResponse");
        PayUpResponseDTO result = new PayUpResponseDTO();
        result.setTransactionId(getAsString(transactionResponse, "transactionId"));
        result.setOperationDate(getAsString(transactionResponse, "operationDate"));
        result.setState(getAsString(transactionResponse, "state"));
        result.setStatusCodeReference("0");
        return result;
    }

    @Override
    @Transactional
    public void paymentConfirmation(OrderCompleteDTO request) throws JsonProcessingException {
        String itemsJson;
        try {
            itemsJson = objectMapper.writeValueAsString(request.getDetails());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializando items", e);
        }
        var dateTime = stringToLocalDateTime(request.getOperationDate());
        repository.createOrder(
                request.getEmail(),
                request.getName(),
                request.getDocumentType().name(),
                request.getDocumentNumber(),
                request.getTransactionId(),
                dateTime,
                itemsJson
        );
    }

    private String generateSignature(BigDecimal amount,String referenceCode) {
        String value = amount.setScale(2, RoundingMode.HALF_UP).toString();
        String raw = apiKey + "~" + merchantId + "~"+referenceCode+"~" + value + "~PEN";
        return DigestUtils.md5DigestAsHex(raw.getBytes());
    }
    private String getYearMonth(String expiration){
        YearMonth yearMonth = YearMonth.parse(expiration);
        String formatted = yearMonth.format(DateTimeFormatter.ofPattern("yyyy/MM"));
        return formatted;
    }
    private String getAsString(Map<String, Object> map, String key) {
        Object value = map.get(key);
        return value != null ? value.toString() : null;
    }
    private LocalDateTime stringToLocalDateTime(String operationDate){
        if(operationDate==null){
            return LocalDateTime.now();
        }
        long timestamp = Long.parseLong(operationDate);
        LocalDateTime dateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(timestamp),
                ZoneId.systemDefault()
        );
        return  dateTime;
    }
}
