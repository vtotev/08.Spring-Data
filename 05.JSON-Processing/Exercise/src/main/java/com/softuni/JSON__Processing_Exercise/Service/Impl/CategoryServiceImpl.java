package com.softuni.JSON__Processing_Exercise.Service.Impl;

import com.google.gson.Gson;
import com.softuni.JSON__Processing_Exercise.Entities.Category;
import com.softuni.JSON__Processing_Exercise.Entities.Product;
import com.softuni.JSON__Processing_Exercise.Repository.CategoryRepository;
import com.softuni.JSON__Processing_Exercise.Service.CategoryService;
import com.softuni.JSON__Processing_Exercise.Utils.ValidationUtil;
import com.softuni.JSON__Processing_Exercise.dto.CategoriesCountDto;
import com.softuni.JSON__Processing_Exercise.dto.CategorySeedDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static com.softuni.JSON__Processing_Exercise.Constants.GlobalConsts.*;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final String CATEGORIES_FILE_NAME = "categories.json";

    private final CategoryRepository categoryRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }


    @Override
    public void seedCategories() throws IOException {
        if (categoryRepository.count() > 0) {
            return;
        }

        String fileContent = Files.readString(Path.of(RESOURCE_FULL_PATH + CATEGORIES_FILE_NAME));

        CategorySeedDto[] categorySeedDtos = gson.fromJson(fileContent, CategorySeedDto[].class);

        Arrays.stream(categorySeedDtos).filter(validationUtil::isValid)
                .map(categorySeedDto -> modelMapper.map(categorySeedDto, Category.class))
                .forEach(categoryRepository::save);
    }

    @Override
    public Set<Category> findRandomCategories() {
        Set<Category> categorySet = new HashSet<>();
        int categoryCount = ThreadLocalRandom.current().nextInt(1, 4);
        long categoryRepoCount =  categoryRepository.count() + 1;
        for (int i = 0; i < categoryCount; i++) {
            long randId = ThreadLocalRandom.current().nextLong(1, categoryRepoCount);
            categorySet.add(categoryRepository.findById(randId).orElse(null));
        }
        return categorySet;
    }

    @Override
    public List<CategoriesCountDto> findAllCategoriesCount() {
        List<CategoriesCountDto> categoriesCountDtos = new ArrayList<>();
        categoryRepository.findAll().forEach(cat -> {

// to be done
//
//            if (!cat.getProducts().isEmpty() && cat.getProducts() != null) {
//                TypeMap<Category, CategoriesCountDto> typeMap = modelMapper
//                        .typeMap(Category.class, CategoriesCountDto.class)
//                        .addMapping(Category::getName, CategoriesCountDto::setCategory)
//                        .addMapping(c -> c.getProducts().size(), CategoriesCountDto::setProductsCount)
//                        .addMapping(category -> {
//                            BigDecimal avgPrice = new BigDecimal(0L);
//                            category.getProducts().stream().forEach(c -> avgPrice.add(c.getPrice()));
//                            return avgPrice.divide(BigDecimal.valueOf(category.getProducts().size()));
//                        }, CategoriesCountDto::setAveragePrice)
//                        .addMapping(category -> {
//                            BigDecimal revenue = new BigDecimal(0L);
//                            category.getProducts().stream().forEach(c -> revenue.add(c.getPrice()));
//                            return revenue;
//                        }, CategoriesCountDto::setTotalRevenue);
//                categoriesCountDtos.add(typeMap.map(cat));
//            } else {
//                CategoriesCountDto ccdto = new CategoriesCountDto();
//                ccdto.setCategory(cat.getName());
//                ccdto.setProductsCount(0L);
//                ccdto.setAveragePrice(BigDecimal.valueOf(0));
//                ccdto.setTotalRevenue(BigDecimal.valueOf(0));
//                categoriesCountDtos.add(ccdto);
//            }
        });
        return categoriesCountDtos;
    }
}
