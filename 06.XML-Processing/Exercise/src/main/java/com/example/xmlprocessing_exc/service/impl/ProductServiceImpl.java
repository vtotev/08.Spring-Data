package com.example.xmlprocessing_exc.service.impl;

import com.example.xmlprocessing_exc.model.Entities.Product;
import com.example.xmlprocessing_exc.model.dto.ProductSeedDto;
import com.example.xmlprocessing_exc.model.dto.ProductViewRootDto;
import com.example.xmlprocessing_exc.model.dto.ProductWithSellerDto;
import com.example.xmlprocessing_exc.repository.ProductRepository;
import com.example.xmlprocessing_exc.service.CategoryService;
import com.example.xmlprocessing_exc.service.ProductService;
import com.example.xmlprocessing_exc.service.UserService;
import com.example.xmlprocessing_exc.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final UserService userService;
    private final CategoryService categoryService;


    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, ValidationUtil validationUtil, UserService userService, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public long getCount() {
        return productRepository.count();
    }

    @Override
    public void seedProducts(List<ProductSeedDto> products) {
        products.stream()
                .filter(validationUtil::isValid)
                .map(productSeedDto -> {
                    Product product = modelMapper.map(productSeedDto, Product.class);
                    if (product.getPrice().compareTo(BigDecimal.valueOf(700L)) > 0) {
                        product.setBuyer(userService.getRandomUser());
                    }
                    product.setSeller(userService.getRandomUser());
                    product.setCategories(categoryService.getRandomCategories());
                    return product;
                })
                .forEach(productRepository::save);
    }

    @Override
    public ProductViewRootDto findProductsInRangeWithoutBuyer() {
        ProductViewRootDto rootDto = new ProductViewRootDto();
        rootDto.setProduct(productRepository
                .findAllByPriceBetweenAndBuyerIsNull(BigDecimal.valueOf(500L), BigDecimal.valueOf(1000L))
                .stream()
                .map(product -> {
                    ProductWithSellerDto user = modelMapper.map(product, ProductWithSellerDto.class);
                    user.setSeller(String.format("%s %s", product.getSeller().getFirstName(), product.getSeller().getLastName()));
                    return user;
                })
                .collect(Collectors.toList())
        );
        return rootDto;
    }
}
