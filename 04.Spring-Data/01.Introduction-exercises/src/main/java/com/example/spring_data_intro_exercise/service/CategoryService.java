package com.example.spring_data_intro_exercise.service;

import com.example.spring_data_intro_exercise.model.Category;

import java.io.IOException;
import java.util.Set;

public interface CategoryService {
    void seedCategories() throws IOException;

    Set<Category> getRandomCategories();
}
