package com.varun.ecommerce.repository;

import com.varun.ecommerce.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepo extends JpaRepository<Image,Long> {
    List<Image> findByProductId(Long id);
}
