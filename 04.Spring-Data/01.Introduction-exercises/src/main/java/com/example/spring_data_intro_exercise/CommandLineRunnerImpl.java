package com.example.spring_data_intro_exercise;

import com.example.spring_data_intro_exercise.model.Book;
import com.example.spring_data_intro_exercise.service.AuthorService;
import com.example.spring_data_intro_exercise.service.BooksService;
import com.example.spring_data_intro_exercise.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BooksService booksService;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BooksService booksService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.booksService = booksService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();

        printAllBooksAfterYear(2000);
        printAuthorsWithAtLeastOneBookBeforeYear(1990);
        printAllAuthorsAndNumberOfTheirBooks();
        printBooksByAuthorNameOrderByReleaseDate("George", "Powell");

    }

    private void printBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        booksService.findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
        .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService.getAllAuthorsOrderByCountOfTheirBooksDesc().forEach(System.out::println);
    }

    private void printAuthorsWithAtLeastOneBookBeforeYear(int year) {
        booksService.findAllAuthorsWithBooksWithReleaseDateBeforeYear(year).forEach(System.out::println);
    }

    private void printAllBooksAfterYear(int year) {
        booksService.findAllBooksAfterYear(year)
                .stream().map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        booksService.seedBooks();
    }
}
