package com.example.advqueryingex.service;

import com.example.advqueryingex.models.entities.Category;
import com.example.advqueryingex.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.advqueryingex.constants.GlobalConstants.CATEGORIES_FILE_NAME;
import static com.example.advqueryingex.constants.GlobalConstants.RESOURCE_PATH;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public void seedCategories() throws IOException {
        if (categoryRepository.count() > 0) {
            return;
        }

        List<String> rows = Files.readAllLines(Path.of(RESOURCE_PATH + CATEGORIES_FILE_NAME))
                .stream()
                .filter(s -> !"".equals(s))
                .collect(Collectors.toList());

        rows.forEach(row -> {
            Category category = new Category(row);
            categoryRepository.save(category);
        });
    }

    @Override
    public Set<Category> getRandomCategories() {
        Set<Category> categories = new HashSet<>();
        Random random = new Random();
        int countOfEntities = (int) categoryRepository.count();
        int countOfCategories = random.nextInt(4);

        for (int i = 0; i < countOfCategories; i++) {
            long randomId = random.nextInt(countOfEntities) + 1;
            categories.add(categoryRepository.getById(randomId));
        }

        return categories;
    }
}