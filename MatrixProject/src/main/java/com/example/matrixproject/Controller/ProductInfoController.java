package com.example.matrixproject.Controller;

import com.example.matrixproject.Dao.Entity.ProductEntity;
import com.example.matrixproject.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductInfoController {
    private ProductService productService;

    @Autowired
    public ProductInfoController(ProductService productService) {
        this.productService = productService;
    }
    @RequestMapping(value = "/product", method= RequestMethod.GET)
    public String productInfo(@RequestParam(name = "productName") String productName,
                              Model model){
        List<ProductEntity>product=productService.findByName(productName);
        model.addAttribute("product",product);
        model.addAttribute("productName",productName);
        return "ProductsInfo.html";
    }
}
