package com.example.matrixproject.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.example.matrixproject.Dao.Entity.ProductEntity;
import com.example.matrixproject.Dao.Repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {

        this.productRepository = productRepository;
    }

    public List<ProductEntity> findNotNulltop4() {
        List<ProductEntity> products =
                productRepository.findNotNulltop4();
        return products;
    }


    public List<ProductEntity> findtop8() {
        List<ProductEntity> products =
                productRepository.findbytop8();
        return products;
    }

    public ProductEntity getProduct(Long id) {
        return productRepository.findById(id).orElseGet(null);
    }



    public Page<ProductEntity> getProduct(Integer page,String direction,String property,String sort){



        if(property.equals("food")){
            return productRepository.findFood(PageRequest.of(page, 8));
        } else if(property.equals("drink")){
            return productRepository.findDrink(PageRequest.of(page, 8));
        }else if(property.equals("Personal Care")){
            return productRepository.findPersonalCare(PageRequest.of(page, 8));
        }else if(property.equals("Cleaning Supplies")){
            return productRepository.findCleaningSupplies(PageRequest.of(page, 8));
        }

        return productRepository.findAll(PageRequest.of(page, 8));

    }

    public Page<ProductEntity> getProductPrice(Integer page,String direction,String property,String min,String max,String sort){




        String findBy ="name";
        Sort.Direction order = Sort.Direction.ASC;

        if (sort.equals("Price (low to high)")){
            findBy="price";
            order=Sort.Direction.ASC;
        }else if (sort.equals("Price (high to low)")){
            findBy="price";
            order=Sort.Direction.DESC;
        }else if (sort.equals("Name Z-A")){
            findBy="price";
            order=Sort.Direction.DESC;
        }else if (sort.equals("Name A-Z")){
            findBy="name";
            order=Sort.Direction.ASC;
        }


        String first=min.substring(1);
        String second=max.substring(1);

        if(property.equals("food")){
            return productRepository.findFoodPrice(PageRequest.of(page, 8,Sort.by(order,findBy)),first,second);
        } else  if(property.equals("drink")){
            return productRepository.findDrinkPrice(PageRequest.of(page, 8,Sort.by(order,findBy)),first,second);
        }else  if(property.equals("Personal Care")){
            return productRepository.findPersonalCarePrice(PageRequest.of(page, 8,Sort.by(order,findBy)),first,second);
        }else  if(property.equals("Cleaning Supplies")){
            return productRepository.findCleaningSuppliesPrice(PageRequest.of(page, 8,Sort.by(order,findBy)),first,second);
        }


        return productRepository.findAllPrice(PageRequest.of(page, 8,Sort.by(order,findBy)),first,second);

    }



    public Page<ProductEntity> getProducts(Integer page, String productType, String min,String max,String sort){

        String findBy ="name";
        Sort.Direction order = Sort.Direction.ASC;

        if (sort.equals("Price (low to high)")){
            findBy="price";
            order=Sort.Direction.ASC;
        }else if (sort.equals("Price (high to low)")){
            findBy="price";
            order=Sort.Direction.DESC;
        }else if (sort.equals("Name Z-A")){
            findBy="price";
            order=Sort.Direction.DESC;
        }else if (sort.equals("Name A-Z")){
            findBy="name";
            order=Sort.Direction.ASC;
        }

        String first=min.substring(1);
        String second=max.substring(1);
        if (productType.equals("all")){
            return productRepository.findAllByPriceAndProductCategory(PageRequest.of(page,8),first,second);
        }
        return  productRepository.findByPriceAndProductCategory(PageRequest.of(page, 8),first,second, productType);
    }


    public Page<ProductEntity> getFood(Pageable pageable){

        return productRepository.findFood(pageable);
    }

    public void saveProduct (ProductEntity productEntity) {

        productRepository.save(productEntity);
    }


    public Page<ProductEntity> getProductSort(Integer page, String productType, String min,String max,String sort){




        String findBy = null;
        Sort.Direction order = null;

        if (sort.equals("Price (low to high)")){
            findBy="price";
            order=Sort.Direction.ASC;
        }else if (sort.equals("Price (high to low)")){
            findBy="price";
            order=Sort.Direction.DESC;
        }else if (sort.equals("Name Z-A")){
            findBy="price";
            order=Sort.Direction.DESC;
        }else if (sort.equals("Name A-Z")){
            findBy="name";
            order=Sort.Direction.ASC;
        }


        String first=min.substring(1);
        String second=max.substring(1);



        if(productType.equals("all")){
            return  productRepository.findAllByPriceAndProductCategory(PageRequest.of(page, 8,Sort.by(order,findBy)),first,second);
        }

        return  productRepository.findByPriceAndProductCategory(PageRequest.of(page, 8,Sort.by(order,findBy)),first,second, productType);
    }



    public Page<ProductEntity> getProductSortDropDown(Integer page, String direction, String productType, String min, String max, String sort){




        String findBy = null;
        Sort.Direction order = null;

        if (sort.equals("Price (low to high)")){
            findBy="price";
            order=Sort.Direction.ASC;
        }else if (sort.equals("Price (high to low)")){
            findBy="price";
            order=Sort.Direction.DESC;
        }else if (sort.equals("Name Z-A")){
            findBy="price";
            order=Sort.Direction.DESC;
        }else if (sort.equals("Name A-Z")){
            findBy="name";
            order=Sort.Direction.ASC;
        }


        String first=min.substring(1);
        String second=max.substring(1);




        if(productType.equals("food")){
            return productRepository.findFoodPrice(PageRequest.of(page, 8,Sort.by(order,findBy)),first,second);
        } else  if(productType.equals("drink")){
            return productRepository.findDrinkPrice(PageRequest.of(page, 8,Sort.by(order,findBy)),first,second);
        }else  if(productType.equals("Personal Care")){
            return productRepository.findPersonalCarePrice(PageRequest.of(page, 8,Sort.by(order,findBy)),first,second);
        }else  if(productType.equals("Cleaning Supplies")){
            return productRepository.findCleaningSuppliesPrice(PageRequest.of(page, 8,Sort.by(order,findBy)),first,second);
        }


        return productRepository.findAllPrice(PageRequest.of(page, 8,Sort.by(order,findBy)),first,second);




    }
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<ProductEntity> findAll () {
        return productRepository.findAll();
    }

     public List<ProductEntity>findByName(String productName){
        return productRepository.findByName(productName);
     }

}

