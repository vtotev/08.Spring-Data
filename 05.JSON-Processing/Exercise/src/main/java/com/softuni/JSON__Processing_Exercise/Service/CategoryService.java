package com.softuni.JSON__Processing_Exercise.Service;

import com.softuni.JSON__Processing_Exercise.Entities.Category;
import com.softuni.JSON__Processing_Exercise.dto.CategoriesCountDto;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface CategoryService {
    void seedCategories() throws IOException;
    Set<Category> findRandomCategories();

    List<CategoriesCountDto> findAllCategoriesCount();
}
