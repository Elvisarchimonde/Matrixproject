package com.example.matrixproject.Dao.Repository;
import com.example.matrixproject.Dao.Entity.ProductEntity;
import com.example.matrixproject.Dao.Entity.ProductEntity;
import com.example.matrixproject.Model.Enums.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import  org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Repository

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query(value = "SELECT * FROM  products"
            +" WHERE special_price IS NOT NULL"+
            "  LIMIT 6 ",
            nativeQuery=true)
    List<ProductEntity> findNotNulltop4();


    @Query(value = "SELECT * FROM  products"
            +" WHERE special_price Is NULL"+
            "  LIMIT 8 ",
            nativeQuery=true)
    List<ProductEntity> findbytop8();

    @Query(value =" SELECT * FROM products WHERE special_price IS NOT NULL GROUP BY id ASC LIMIT 4 OFFSET 0;", nativeQuery=true)
    List<ProductEntity> findFirstElement();




//    @Query(value = "select * from products",nativeQuery = true)
    Page<ProductEntity> findAll(Pageable pageable);

    @Query(value = "select * from products WHERE category='Food'", nativeQuery = true)
    Page<ProductEntity> findFood(Pageable pageable);


    @Query(value = "select * from products WHERE category='Drink'", nativeQuery = true)
    Page<ProductEntity> findDrink(Pageable pageable);


    @Query(value = "select * from products WHERE category='Personal Care'", nativeQuery = true)
    Page<ProductEntity> findPersonalCare(Pageable pageable);


    @Query(value = "select * from products WHERE category='Cleaning Supplies'", nativeQuery = true)
    Page<ProductEntity> findCleaningSupplies(Pageable pageable);




    @Query(value = "SELECT * FROM products WHERE category = :productCategory and price Between :startPrice and :endPrice   " ,nativeQuery = true)
    Page<ProductEntity> findByPriceAndProductCategory(Pageable pageable,@Param("startPrice") String minPrice,
                                                      @Param("endPrice")String maxPrice, @Param("productCategory") String productCategory);



    @Query(value = "SELECT * FROM products WHERE price Between :startPrice and :endPrice   " ,nativeQuery = true)
    Page<ProductEntity> findAllByPriceAndProductCategory(Pageable pageable,@Param("startPrice") String minPrice,
                                                      @Param("endPrice")String maxPrice);

    @Query(value = "SELECT * FROM products WHERE price Between :startPrice and :endPrice", nativeQuery = true)
    Page<ProductEntity> findAllPrice(Pageable pageable,@Param("startPrice") String minPrice,
                                     @Param("endPrice")String maxPrice);


    @Query(value = "SELECT * FROM products WHERE category='Food' and price BETWEEN :startPrice and :endPrice", nativeQuery = true)
    Page<ProductEntity> findFoodPrice(Pageable pageable,@Param("startPrice") String minPrice,
                                      @Param("endPrice")String maxPrice);


    @Query(value = "SELECT * FROM products WHERE category='Drink' and  price BETWEEN :startPrice and :endPrice", nativeQuery = true)
    Page<ProductEntity> findDrinkPrice(Pageable pageable,@Param("startPrice") String minPrice,
                                       @Param("endPrice")String maxPrice);


    @Query(value = "SELECT * FROM products WHERE category='Personal Care' and price BETWEEN :startPrice and :endPrice", nativeQuery = true)
    Page<ProductEntity> findPersonalCarePrice(Pageable pageable,@Param("startPrice") String minPrice,
                                              @Param("endPrice")String maxPrice);


    @Query(value = "SELECT * FROM products WHERE category='Cleaning Supplies' and price BETWEEN :startPrice and :endPrice", nativeQuery = true)
    Page<ProductEntity> findCleaningSuppliesPrice(Pageable pageable,@Param("startPrice") String minPrice,
                                                  @Param("endPrice")String maxPrice);
    @Query(value = "select  * from products where name = :productName",nativeQuery = true)
    List<ProductEntity>findByName(@Param("productName")String productName);
}
