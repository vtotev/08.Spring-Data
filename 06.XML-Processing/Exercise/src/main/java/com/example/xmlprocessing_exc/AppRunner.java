package com.example.xmlprocessing_exc;

import com.example.xmlprocessing_exc.model.dto.*;
import com.example.xmlprocessing_exc.service.CategoryService;
import com.example.xmlprocessing_exc.service.ProductService;
import com.example.xmlprocessing_exc.service.UserService;
import com.example.xmlprocessing_exc.utils.XmlParser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

@Component
public class AppRunner implements CommandLineRunner {
    private static final String RESOURCES_FILE_PATH = "src/main/resources/files/";
    private static final String OUTPUT_FILE_PATH = "src/main/resources/files/out/";
    private static final String CATEGORIES_FILE_NAME = "categories.xml";
    private static final String USERS_FILE_NAME = "users.xml";
    private static final String PRODUCTS_FILE_NAME = "products.xml";
    private static final String PRODUCTS_IN_RANGE_FILE_NAME = "products-in-range.xml";
    private static final String USERS_WITH_SOLD_PRODUCTS_FILE_NAME = "users-with-sold-products.xml";
    private static final String USERS_WITH_SOLD_PRODUCTS_ORDERED_FILE_NAME = "users-with-sold-products-ex4.xml";
    private static final String CATEGORIES_BY_PRODUCTS_COUNT_FILE_NAME = "categories-by-products-count.xml";

    private final XmlParser xmlParser;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;
    private final BufferedReader bufferedReader;

    public AppRunner(XmlParser xmlParser, CategoryService categoryService, UserService userService, ProductService productService) {
        this.xmlParser = xmlParser;
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();
        System.out.print("Select ex: ");
        int exNum = Integer.parseInt(bufferedReader.readLine());
        switch (exNum) {
            case 1 -> productsInRange();
            case 2 -> usersWithSoldProducts();
            case 3 -> categoriesByProductsCount();
            case 4 -> usersAndProducts();
        }
    }

    private void usersAndProducts() throws JAXBException {
        UsersCountRootDto userWithProductsRootDto = userService.findUsersWithSoldProductsOrderedByCount();
        xmlParser.writeToFile(OUTPUT_FILE_PATH + USERS_WITH_SOLD_PRODUCTS_ORDERED_FILE_NAME, userWithProductsRootDto);
    }

    private void categoriesByProductsCount() throws JAXBException {
        CategoryCountRootDto categoryCountRootDto = categoryService.findCategoriesByProductsCount();
        xmlParser.writeToFile(OUTPUT_FILE_PATH + CATEGORIES_BY_PRODUCTS_COUNT_FILE_NAME, categoryCountRootDto);
    }


    private void usersWithSoldProducts() throws JAXBException {
        UserViewRootDto userViewRootDto = userService.findUsersWithMoreThanOneSoldProduct();
        xmlParser.writeToFile(OUTPUT_FILE_PATH + USERS_WITH_SOLD_PRODUCTS_FILE_NAME, userViewRootDto);
    }

    private void productsInRange() throws JAXBException {
        ProductViewRootDto rootDto = productService.findProductsInRangeWithoutBuyer();
        xmlParser.writeToFile(OUTPUT_FILE_PATH + PRODUCTS_IN_RANGE_FILE_NAME, rootDto);
    }

    private void seedData() throws JAXBException, FileNotFoundException {
        if (categoryService.getEntityCount() == 0) {
            CategorySeedRootDto categorySeedRootDto = xmlParser.fromFile(RESOURCES_FILE_PATH + CATEGORIES_FILE_NAME,
                    CategorySeedRootDto.class);
            categoryService.seedCategories(categorySeedRootDto.getCategories());
        }

        if (userService.getEntityCount() == 0) {
            UserSeedRootDto userSeedRootDto = xmlParser.fromFile(RESOURCES_FILE_PATH + USERS_FILE_NAME,
                    UserSeedRootDto.class);
            userService.seedUsers(userSeedRootDto.getUsers());
        }

        if (productService.getCount() == 0) {
            ProductSeedRootDto productSeedRootDto = xmlParser.fromFile(RESOURCES_FILE_PATH + PRODUCTS_FILE_NAME,
                    ProductSeedRootDto.class);
            productService.seedProducts(productSeedRootDto.getProducts());
        }
    }
}
