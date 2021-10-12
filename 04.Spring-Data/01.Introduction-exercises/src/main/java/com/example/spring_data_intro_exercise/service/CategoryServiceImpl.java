package com.example.spring_data_intro_exercise.service;

import com.example.spring_data_intro_exercise.model.Category;
import com.example.spring_data_intro_exercise.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final String categoriesPath = "C:\\Users\\VaL\\Desktop\\resources\\categories.txt";

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public void seedCategories() throws IOException {
        if (categoryRepository.count() > 0) {
            return;
        }

        Files.readAllLines(Path.of(categoriesPath))
                .stream().filter(s -> !s.isEmpty())
                .forEach(catName -> {
                    Category cat = new Category(catName);
                    categoryRepository.save(cat);
                });

    }

    @Override
    public Set<Category> getRandomCategories() {
        Set<Category> categories = new HashSet<>();
        int randInt = ThreadLocalRandom.current().nextInt(1, 3);
        for (int i = 0; i < randInt; i++) {
            Category cat = categoryRepository.findById(ThreadLocalRandom.current()
                    .nextLong(1, categoryRepository.count() + 1))
                    .orElse(null);
            categories.add(cat);
        }

        return categories;

    }

}
