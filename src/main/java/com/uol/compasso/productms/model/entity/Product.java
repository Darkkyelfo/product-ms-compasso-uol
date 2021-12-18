package com.uol.compasso.productms.model.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "produto")
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;

}
