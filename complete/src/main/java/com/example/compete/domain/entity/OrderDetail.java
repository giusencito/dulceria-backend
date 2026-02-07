package com.example.compete.domain.entity;


import com.example.compete.domain.valueObject.OrderDetailId;
import com.example.compete.shared.domain.AuditModel;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_details")
public class OrderDetail extends AuditModel {

    @EmbeddedId
    private OrderDetailId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @NotNull
    @Min(1)
    private Integer quantity;
}
