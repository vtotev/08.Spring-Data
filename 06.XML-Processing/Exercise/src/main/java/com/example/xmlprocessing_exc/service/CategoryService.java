package com.example.xmlprocessing_exc.service;

import com.example.xmlprocessing_exc.model.Entities.Category;
import com.example.xmlprocessing_exc.model.dto.CategoryCountRootDto;
import com.example.xmlprocessing_exc.model.dto.CategorySeedDto;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    void seedCategories(List<CategorySeedDto> categories);
    long getEntityCount();
    Set<Category> getRandomCategories();

    CategoryCountRootDto findCategoriesByProductsCount();
}

