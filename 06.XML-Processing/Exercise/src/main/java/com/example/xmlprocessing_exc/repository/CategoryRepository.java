package com.example.xmlprocessing_exc.repository;

import com.example.xmlprocessing_exc.model.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select c from Category c order by c.products.size desc")
    List<Category> findAllOrderByProductsCountDesc();
}
