package com.softuni.JSON__Processing_Exercise;

import com.google.gson.Gson;
import com.softuni.JSON__Processing_Exercise.Entities.Category;
import com.softuni.JSON__Processing_Exercise.Service.CategoryService;
import com.softuni.JSON__Processing_Exercise.Service.ProductService;
import com.softuni.JSON__Processing_Exercise.Service.UsersService;
import com.softuni.JSON__Processing_Exercise.dto.CategoriesCountDto;
import com.softuni.JSON__Processing_Exercise.dto.ProductNameAndPriceDto;
import com.softuni.JSON__Processing_Exercise.dto.UserSoldDto;
import org.modelmapper.TypeMap;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

@Component
public class AppRunner implements CommandLineRunner {
    private static final String OUTPUT_FILE_PATH = "src/main/resources/files/Out/";
    private static final String PRODUCT_IN_RANGE_FILE_NAME = "products-in-range.json";
    private static final String USERS_AND_SOLD_PRODUCTS = "users-and-sold-products.json";
    private static final String CATEGORIES_BY_PRODUCTS = "categories-by-products.json";
    private final CategoryService categoryService;
    private final UsersService usersService;
    private final ProductService productService;
    private final BufferedReader bufferedReader;
    private final Gson gson;

    public AppRunner(CategoryService categoryService, UsersService usersService, ProductService productService, Gson gson) {
        this.categoryService = categoryService;
        this.usersService = usersService;
        this.productService = productService;
        this.gson = gson;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();
        System.out.print("Enter exercise: ");
        int exNum = Integer.parseInt(bufferedReader.readLine());
        switch (exNum) {
            case 1 -> productsInRange();
            case 2 -> soldProducts();
            case 3 -> CategoriesByProductsCount();
        }
    }

    private void CategoriesByProductsCount() {
        List<CategoriesCountDto> categoriesCountDtos = categoryService.findAllCategoriesCount();
        String s = gson.toJson(categoriesCountDtos);
        System.out.println(s);
    }

    private void soldProducts() throws IOException {
        List<UserSoldDto> userSoldDtos = usersService.findAllUsersWithMoreThanOneSoldProducts();
        String content = gson.toJson(userSoldDtos);
        writeToFile(OUTPUT_FILE_PATH + USERS_AND_SOLD_PRODUCTS, content);
    }

    private void productsInRange() throws IOException {
        List<ProductNameAndPriceDto> productDtos = productService
                .findAllProductsInRangeOrderByPrice(BigDecimal.valueOf(500L), BigDecimal.valueOf(1000L));
        String content = gson.toJson(productDtos);
        writeToFile(OUTPUT_FILE_PATH + PRODUCT_IN_RANGE_FILE_NAME, content);

    }

    private void writeToFile(String path, String content) throws IOException {
        Files.write(Path.of(path), Collections.singleton(content));
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        usersService.seedUsers();
        productService.seedProducts();
    }
}
