package com.example.candystore.domain.entity;


import com.example.candystore.shared.domain.AuditModel;
import lombok.*;


import javax.persistence.*;
import javax.validation.constraints.*;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product  extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 30)
    @Column(unique = true)
    private String name;


    @NotNull
    @NotBlank
    @Size(max = 100)
    private String description;

    @NotNull
    @Min(0)
    private Float price;


}
