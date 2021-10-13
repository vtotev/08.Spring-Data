package com.example.advqueryingex.service;

import com.example.advqueryingex.models.entities.AgeRestriction;
import com.example.advqueryingex.models.entities.Book;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface BookService {

    void seedBooks() throws IOException;

    List<String> findAllBooksTitlesAfter2000();

    List<String> findFirstAndLastNameOfAuthorsWithBookWithReleaseDateBefore1990();

    List<Book> getAllBooksByAuthorOrderByReleaseDateThenByTitle(String george, String powell);

    void printAllByAgeRestriction(AgeRestriction age);

    void printAllGoldenEditionBooks();

    void printAllBooksByPrice();

    void printBooksNotReleased(int year);

    void printBooksReleasedBefore(LocalDate date);

    void printBooksWithTitleContaining(String contains);

    void printBooksByAuthorLastNameContains(String contains);

    void printBooksWithTitleLengthGreaterThan(int length);

    void printBookCopiesByAuthor();

    void printReducedBookDetailsByTitle(String title);

    void increaseBookCopies(String date, int copies);

    void removeBooks(Integer copies);
}
