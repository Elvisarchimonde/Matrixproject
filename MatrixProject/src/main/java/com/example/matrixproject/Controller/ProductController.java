package com.example.matrixproject.Controller;


import com.example.matrixproject.Dao.Entity.ProductEntity;
import com.example.matrixproject.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {


    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }



    @RequestMapping( value="/slider", method = RequestMethod.GET)
    public List<ProductEntity> slider(@RequestParam String category){
        List<ProductEntity> productSlider =
                productService.findNotNulltop4();
        return productSlider;
    }

}
