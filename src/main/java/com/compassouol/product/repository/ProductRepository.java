package com.compassouol.product.repository;

import com.compassouol.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT P FROM Product P " +
            "where ((P.name like CONCAT('%', :nameOrDescription,'%') or P.description like CONCAT('%', "
            + ":nameOrDescription,'%')) or "
            + ":nameOrDescription is null)" +
            "and (:max_price is null or P.price <= :max_price) " +
            "and (:min_price is null or P.price >= :min_price)")
    List<Product> searchProduct(@Param("min_price") Double min_price,
            @Param("max_price") Double max_price,
            @Param("nameOrDescription") String nameOrDescription);

    boolean existsAllByDescriptionOrName(String name, String description);
}
