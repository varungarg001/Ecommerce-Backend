package com.varun.ecommerce.repository;

import com.varun.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {

    List<Product> findByCategoryNameAndBrand(String category, String brandName);

    List<Product> findByBrandAndName(String brandName, String name);

    Long countByBrandAndName(String brandName, String name);

}
