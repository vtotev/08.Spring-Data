package com.example.xmlprocessing_exc.service.impl;

import com.example.xmlprocessing_exc.model.Entities.Category;
import com.example.xmlprocessing_exc.model.Entities.Product;
import com.example.xmlprocessing_exc.model.dto.CategoryCountDto;
import com.example.xmlprocessing_exc.model.dto.CategoryCountRootDto;
import com.example.xmlprocessing_exc.model.dto.CategorySeedDto;
import com.example.xmlprocessing_exc.repository.CategoryRepository;
import com.example.xmlprocessing_exc.service.CategoryService;
import com.example.xmlprocessing_exc.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }


    @Override
    public void seedCategories(List<CategorySeedDto> categories) {
        categories.stream()
                .filter(validationUtil::isValid)
                .map(categorySeedDto -> modelMapper.map(categorySeedDto, Category.class))
                .forEach(categoryRepository::save);
    }

    @Override
    public long getEntityCount() {
        return categoryRepository.count();
    }

    @Override
    public Set<Category> getRandomCategories() {
        Set<Category> categories = new HashSet<>();
        long categoriesCount = categoryRepository.count();
        for (int i = 0; i < 2; i++) {
            long randId = ThreadLocalRandom.current().nextLong(1, categoriesCount + 1);
            categories.add(categoryRepository.findById(randId).orElse(null));
        }
        return categories;
    }

    @Override
    public CategoryCountRootDto findCategoriesByProductsCount() {
        CategoryCountRootDto categoryCountRootDto = new CategoryCountRootDto();
        categoryCountRootDto.setCategories(categoryRepository.findAllOrderByProductsCountDesc()
                .stream()
                .map(category -> {
                    CategoryCountDto cat = modelMapper.map(category, CategoryCountDto.class);
                    BigDecimal totalRevenue = category.getProducts().stream()
                            .map(Product::getPrice)
                            .reduce(BigDecimal::add)
                            .orElse(BigDecimal.ZERO);
                    cat.setTotalRevenue(totalRevenue);
                    Long productsCount = Long.valueOf(category.getProducts().size());
                    cat.setCount(productsCount);
                    cat.setAveragePrice(totalRevenue.divide(BigDecimal.valueOf(productsCount), RoundingMode.CEILING));
                    return cat;
                }).collect(Collectors.toList()));
        return categoryCountRootDto;
    }
}
