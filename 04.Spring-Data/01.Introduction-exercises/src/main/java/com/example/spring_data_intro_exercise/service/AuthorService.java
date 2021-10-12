package com.example.spring_data_intro_exercise.service;

import com.example.spring_data_intro_exercise.model.Author;

import java.io.IOException;
import java.util.List;

public interface AuthorService {

    void seedAuthors() throws IOException;

    Author getRandomAuthor();

    List<String> getAllAuthorsOrderByCountOfTheirBooksDesc();
}
