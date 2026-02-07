package com.example.premieres.domain.entity;

import com.example.premieres.shared.domain.AuditModel;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
@Entity
@With
@AllArgsConstructor
@Table(name="premieres")
public class Premiere extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url",unique = true)
    @NotNull
    private String imageUrl;
    @NotNull
    private String description;
}
