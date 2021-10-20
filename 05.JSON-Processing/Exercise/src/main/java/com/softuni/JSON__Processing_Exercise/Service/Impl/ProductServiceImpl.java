package com.softuni.JSON__Processing_Exercise.Service.Impl;

import com.google.gson.Gson;
import com.softuni.JSON__Processing_Exercise.Entities.Product;
import com.softuni.JSON__Processing_Exercise.Repository.ProductsRepository;
import com.softuni.JSON__Processing_Exercise.Service.CategoryService;
import com.softuni.JSON__Processing_Exercise.Service.ProductService;
import com.softuni.JSON__Processing_Exercise.Service.UsersService;
import com.softuni.JSON__Processing_Exercise.Utils.ValidationUtil;
import com.softuni.JSON__Processing_Exercise.dto.ProductNameAndPriceDto;
import com.softuni.JSON__Processing_Exercise.dto.ProductSeedDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.softuni.JSON__Processing_Exercise.Constants.GlobalConsts.RESOURCE_FULL_PATH;

@Service
public class ProductServiceImpl implements ProductService {
    private static final String PRODUCTS_FILE_NAME = "products.json";
    private final ProductsRepository productsRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final UsersService usersService;
    private final CategoryService categoryService;

    public ProductServiceImpl(ProductsRepository productsRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil, UsersService usersService, CategoryService categoryService) {
        this.productsRepository = productsRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.usersService = usersService;
        this.categoryService = categoryService;
    }


    @Override
    public void seedProducts() throws IOException {
        if (productsRepository.count() > 0) {
            return;
        }
        Arrays.stream(gson.fromJson(Files.readString(Path.of(RESOURCE_FULL_PATH + PRODUCTS_FILE_NAME)), ProductSeedDto[].class))
                .filter(validationUtil::isValid)
                .map(productSeedDto -> {
                    Product product = modelMapper.map(productSeedDto, Product.class);
                    product.setSeller(usersService.findRandomUser());
                    if (product.getPrice().compareTo(BigDecimal.valueOf(900L)) > 0) {
                        product.setBuyer(usersService.findRandomUser());
                    }
                    product.setCategories(categoryService.findRandomCategories());
                    return product;
                }).forEach(productsRepository::save);
    }

    @Override
    public List<ProductNameAndPriceDto> findAllProductsInRangeOrderByPrice(BigDecimal lower, BigDecimal upper) {
        return productsRepository.findAllByPriceBetweenOrderByPriceDesc(lower, upper)
                .stream().map(product -> {
                    ProductNameAndPriceDto productNameAndPriceDto = modelMapper.map(product, ProductNameAndPriceDto.class);
                    productNameAndPriceDto.setSeller(String.format("%s %s",
                            product.getSeller().getFirstName(),
                            product.getSeller().getLastName()));
                    return productNameAndPriceDto;
                })
                .collect(Collectors.toList());
    }
}
