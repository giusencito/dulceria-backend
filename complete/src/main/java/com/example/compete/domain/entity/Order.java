package com.example.compete.domain.entity;


import com.example.compete.domain.enumeration.DocumentType;
import com.example.compete.shared.domain.AuditModel;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    @Column(name = "document_number")
    private String documentNumber;


    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "document_type")
    private DocumentType documentType;

    @NotNull
    @NotBlank
    @Column(name = "transaction_id")
    private String transactionId;


    @NotNull
    @Column(name = "operation_date", updatable = false)
    private LocalDateTime operationDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> details;
}

