package com.softuni.JSON__Processing_Exercise.Repository;

import com.softuni.JSON__Processing_Exercise.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductsRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByPriceBetweenOrderByPriceDesc(BigDecimal lower, BigDecimal upper);
}
