package com.example.matrixproject.Model.Dto;

import com.example.matrixproject.Model.Enums.ProductCategory;

import javax.persistence.Column;
import java.math.BigDecimal;

public class ProductDto {
    Long id ;
    String productName;
    BigDecimal productPrice;
    BigDecimal specialPrice;
    String productCategory;
    String productImg;
    String productInfo;
    String productIngredients;

}
