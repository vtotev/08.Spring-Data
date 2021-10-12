package com.example.spring_data_intro_exercise.service;

import com.example.spring_data_intro_exercise.model.Book;
import com.example.spring_data_intro_exercise.model.Category;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface BooksService {
    void seedBooks() throws IOException;
    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);
}
