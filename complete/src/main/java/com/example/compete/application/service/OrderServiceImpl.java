package com.example.compete.application.service;

import com.example.compete.application.dto.*;
import com.example.compete.application.mapping.entity.OrderMapper;
import com.example.compete.domain.entity.Order;
import com.example.compete.domain.entity.OrderDetail;
import com.example.compete.domain.repository.OrderDetailRepository;
import com.example.compete.domain.repository.OrderRepository;
import com.example.compete.domain.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class OrderServiceImpl implements OrderService {

    @Value("${candystore.service.url}")
    private String candyUrl;
    @Autowired
    private OrderRepository orderRepository;


    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrderMapper mapper;


    @Autowired
    private HttpServletRequest request;



    @Override
    public List<OrderDTO> ordersByEmail(String email) {
        List<Order> orderList = orderRepository.findByEmail(email);
        return orderList
                .stream()
                .map(mapper::toResource)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResultDetailDTO> detailByOrderId(Long orderId) throws JsonProcessingException {
        List<OrderDetail> orderDetails = orderDetailRepository.findByIdOrderId(orderId);



        List<Long> productIds = orderDetails.stream()
                .map(od -> od.getId().getProductId())
                .collect(Collectors.toList());

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String json = mapper.writeValueAsString(productIds);
        System.out.println("productIds JSON:");
        System.out.println(json);

        DetailProductDTO body = new DetailProductDTO();
        body.setIds(productIds);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(getToken(request));
        HttpEntity<DetailProductDTO> request =
                new HttpEntity<>(body, headers);
        ResponseEntity<List<ProducTODetailDTO>> response =
                restTemplate.exchange(
                        candyUrl,
                        HttpMethod.POST,
                        request,
                        new ParameterizedTypeReference<>() {})
                ;
        List<ProducTODetailDTO> productDetails = response.getBody();
        Map<Long, Integer> productQuantities = orderDetails.stream()
                .collect(Collectors.toMap(
                        od -> od.getId().getProductId(),
                        OrderDetail::getQuantity
                ));
        List<ResultDetailDTO> result = productDetails.stream()
                .map(pd -> {
                    ResultDetailDTO dto = new ResultDetailDTO();
                    dto.setId(pd.getId());
                    dto.setName(pd.getName());
                    dto.setPrice(pd.getPrice());
                    dto.setQuantity(productQuantities.getOrDefault(pd.getId(), 0));
                    return dto;
                })
                .collect(Collectors.toList());
        return result;
    }
    private String getToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer"))
            return header.replace("Bearer ", "");
        return null;
    }
}
