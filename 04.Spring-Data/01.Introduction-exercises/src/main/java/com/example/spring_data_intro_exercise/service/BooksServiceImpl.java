package com.example.spring_data_intro_exercise.service;

import com.example.spring_data_intro_exercise.model.*;
import com.example.spring_data_intro_exercise.repository.BooksRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BooksServiceImpl implements BooksService {
    private final String booksPath = "C:\\Users\\VaL\\Desktop\\resources\\books.txt";

    private final BooksRepository booksRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BooksServiceImpl(BooksRepository booksRepository, AuthorService authorService, CategoryService categoryService) {
        this.booksRepository = booksRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws IOException {
        if (booksRepository.count() > 0) {
            return;
        }
        Files.readAllLines(Path.of(booksPath)).stream().filter(bk -> !bk.isEmpty())
                .forEach( bk -> {
                    String[] data = bk.split("\\s+");
                    Book book = CreateBookInfo(data);
                    booksRepository.save(book);
                });
    }

    @Override
    public List<Book> findAllBooksAfterYear(int year) {
        return booksRepository.findAllByReleaseDateAfter(LocalDate.of(year, 12, 31));
    }

    @Override
    public List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year) {
        return booksRepository.findAllByReleaseDateBefore(LocalDate.of(year, 1, 1))
                .stream().map(book -> String.format("%s %s", book.getAuthor().getFirstName(), book.getAuthor().getLastName()))
                .distinct().collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName) {
        return booksRepository.findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(firstName, lastName)
                .stream().map(book -> String.format("%s %s %d", book.getTitle(), book.getReleaseDate(), book.getCopies()))
                .collect(Collectors.toList());
    }

    private Book CreateBookInfo(String[] data) {
        Book book = new Book();
        EditionType editionType = EditionType.values()[Integer.parseInt(data[0])];
        LocalDate releaseDate = LocalDate.parse(data[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
        int copies = Integer.parseInt(data[2]);
        BigDecimal price = new BigDecimal(data[3]);
        AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(data[4])];
        StringBuilder titleBuilder = new StringBuilder();
        for (int i = 5; i < data.length; i++) {
            titleBuilder.append(data[i]).append(" ");
        }
        String title = titleBuilder.toString().trim();
        Author author = authorService.getRandomAuthor();
        book.setAuthor(author);
        book.setEditionType(editionType);
        book.setReleaseDate(releaseDate);
        book.setCopies(copies);
        book.setPrice(price);
        book.setAgeRestriction(ageRestriction);
        book.setTitle(title);
        Set<Category> categories = categoryService.getRandomCategories();
        book.setCategory(categories);
        return book;
    }

}
