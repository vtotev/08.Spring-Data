package com.example.advqueryingex.service;

import com.example.advqueryingex.models.entities.Author;

import java.io.IOException;
import java.util.List;

public interface AuthorService {

    void seedAuthors() throws IOException;

    int getSize();

    Author getRandomAuthor();

    List<Author> getAllAuthorsAndCountOfTheirBooks();

    List<Author> getAuthorsByEndString(String endStr);
}
