package com.example.matrixproject.Dao.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class ProductEntity   {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @Column(name = "name")
    String productName;
    @Column(name = "image")
    String productImage;
    @Column(name = "price")
    BigDecimal productPrice;
    @Column(name = "special_price")
    BigDecimal specialPrice;
    @Column(name = "category")
    String productCategory;
    @Column(name = "product_info")
    String productInfo;
    @Column(name = "product_ingredient")
    String productIngredient;



}

