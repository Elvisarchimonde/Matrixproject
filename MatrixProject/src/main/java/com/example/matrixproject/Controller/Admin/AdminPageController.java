package com.example.matrixproject.Controller.Admin;

import com.example.matrixproject.Dao.Entity.ProductEntity;
import com.example.matrixproject.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.util.stream.IntStream;

@Controller
@RequestMapping
public class AdminPageController {

    private ProductService productService;

    @Autowired
    public AdminPageController(ProductService productService) {
        this.productService = productService;
    }






    @RequestMapping (value = "/admin/products", produces = {MimeTypeUtils.APPLICATION_JSON_VALUE},
            headers = {"Accept=application/json"})
    public String productPageAdmin(@RequestParam(name = "direction", defaultValue = "DESC") String direction,
                                   @RequestParam(name = "property", defaultValue = "all") String property,
                                   @RequestParam(name = "sort", defaultValue = "sort") String sort,
                                   @RequestParam(name = "min", defaultValue = "$0") String min,
                                   @RequestParam(name = "max", defaultValue = "$100") String max,
                                   @RequestParam(value = "page",
                                           required = false,
                                           defaultValue = "0")
                                           Integer page,
                                   Model model) {


        if(!min.equals("$0") || !max.equals("$100")){

            Page<ProductEntity> productPage =
                    productService.getProductPrice(page, direction, property,min,max,sort);

            model.addAttribute("min",min);
            model.addAttribute("max",max);
            model.addAttribute("sort", sort);
            model.addAttribute("property", property);
            model.addAttribute("products", productPage);
            model.addAttribute("numbers", IntStream.range(0, productPage.getTotalPages()).toArray());
        }else if(!sort.equals("sort")){
            Page<ProductEntity> productPage =
                    productService.getProductSortDropDown(page, direction, property,min,max,sort);

            model.addAttribute("min",min);
            model.addAttribute("max",max);
            model.addAttribute("sort", sort);
            model.addAttribute("property", property);
            model.addAttribute("products", productPage);
            model.addAttribute("numbers", IntStream.range(0, productPage.getTotalPages()).toArray());
        } else {

            Page<ProductEntity> productPage =
                    productService.getProduct(page, direction, property,sort);

            model.addAttribute("min",min);
            model.addAttribute("max",max);
            model.addAttribute("sort", sort);
            model.addAttribute("property", property);
            model.addAttribute("products", productPage);
            model.addAttribute("numbers", IntStream.range(0, productPage.getTotalPages()).toArray());
        }
        return "AdminPage.html";
    }





    @GetMapping("/addNewProduct")
    public String redirectToNewProductPage() {
        return "AddProduct";
    }



    @PostMapping(value = "/addNewProduct")
    public String createProduct(){
        return "AddProduct";
    }

    @GetMapping("/editProduct/{productId}")
    public String redirectToEditProductPage(
            @PathVariable Long productId,
            Model model
    ) {
        ProductEntity product = productService.getProduct(productId);
        model.addAttribute("product", product);

        return "EditProduct";
    }

    @PostMapping("/saveProduct")
    public String saveProduct(ProductEntity productEntity) {
        productService.saveProduct(productEntity);

        return "redirect:/admin/products/";
    }

    @GetMapping("/deleteProduct/{productId}")
    public String deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);

        return "redirect:/admin/products/";
    }

}
