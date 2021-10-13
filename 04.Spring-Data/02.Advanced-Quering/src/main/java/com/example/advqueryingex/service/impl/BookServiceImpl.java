package com.example.advqueryingex.service.impl;

import com.example.advqueryingex.models.entities.*;
import com.example.advqueryingex.repository.BookRepository;
import com.example.advqueryingex.service.AuthorService;
import com.example.advqueryingex.service.BookService;
import com.example.advqueryingex.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.advqueryingex.constants.GlobalConstants.BOOKS_FILE_NAME;
import static com.example.advqueryingex.constants.GlobalConstants.RESOURCE_PATH;

@Service
    public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;


    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    @Transactional
    public void seedBooks() throws IOException {

        if (bookRepository.count() > 0) {
            return;
        }

        Files.readAllLines(Path.of(RESOURCE_PATH + BOOKS_FILE_NAME))
                .forEach(row -> {
                    String[] data = row.split("\\s+");

                    Author author = authorService.getRandomAuthor();
                    EditionType editionType = EditionType.values()[Integer.parseInt(data[0])];
                    LocalDate releaseDate = LocalDate.parse(data[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
                    int copies = Integer.parseInt(data[2]);
                    BigDecimal price = new BigDecimal(data[3]);
                    AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(data[4])];
                    String title = Arrays.stream(data)
                            .skip(5)
                            .collect(Collectors.joining(" "));
                    Set<Category> categories = categoryService.getRandomCategories();


                    Book book = new Book(title, editionType, price, releaseDate,
                            ageRestriction, author, categories, copies);

                    bookRepository.save(book);
                });
    }

    @Override
    public List<String> findAllBooksTitlesAfter2000() {
        return bookRepository
                .findAll()
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findFirstAndLastNameOfAuthorsWithBookWithReleaseDateBefore1990() {
        return bookRepository
                .findAll()
                .stream()
                .filter(book -> book.getReleaseDate().isBefore(LocalDate.of(1990, 1, 1)))
                .map(book -> String.format("%s %s", book.getAuthor().getFirstName(), book.getAuthor().getLastName()))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> getAllBooksByAuthorOrderByReleaseDateThenByTitle(String firstName, String lastName) {
        return bookRepository
                .findByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitleAsc(firstName, lastName);
    }

    @Override
    public void printAllByAgeRestriction(AgeRestriction age) {
        bookRepository.findAllByAgeRestriction(age).forEach(b -> System.out.println(b.getTitle()));
    }

    @Override
    public void printAllGoldenEditionBooks() {
        bookRepository.findBooksByCopiesIsLessThanAndEditionType(5000, EditionType.GOLD).forEach(b -> System.out.println(b.getTitle()));
    }

    @Override
    public void printAllBooksByPrice() {
        bookRepository.findByPriceLessAndGreater(new BigDecimal(5.0), new BigDecimal(40.0))
                .forEach(b -> System.out.printf("%s - $%.2f%n", b.getTitle(), b.getPrice()));
    }

    @Override
    public void printBooksNotReleased(int year) {
        bookRepository.findNotReleasedBooks(year)
                .forEach(b -> System.out.println(b.getTitle()));
    }

    @Override
    public void printBooksReleasedBefore(LocalDate date) {
        bookRepository.findBooksByReleaseDateBefore(date)
                .forEach(b -> System.out.println(b.getTitle()));
    }

    @Override
    public void printBooksWithTitleContaining(String contains) {
        bookRepository.findBooksByTitleContaining(contains).forEach(b -> System.out.println(b.getTitle()));
    }

    @Override
    public void printBooksByAuthorLastNameContains(String contains) {
        bookRepository.findBooksByAuthorLastNameContains(contains).forEach(b ->
                System.out.printf("%s (%s %s)%n", b.getTitle(), b.getAuthor().getFirstName(), b.getAuthor().getLastName()));
    }

    @Override
    public void printBooksWithTitleLengthGreaterThan(int length) {
        System.out.printf("There are %d books with longer title than 12 symbols%n", bookRepository.countBooksByTitleLength(length));
    }

    @Override
    public void printBookCopiesByAuthor() {
        Author randAuth = authorService.getRandomAuthor();
        System.out.printf("%s %s - %d%n", randAuth.getFirstName(), randAuth.getLastName(),
                bookRepository.findAllByAuthorAndSumBooksCount(randAuth));
    }

    @Override
    public void printReducedBookDetailsByTitle(String title) {
        ReducedBook reducedBook = this.bookRepository.getBookByTitle(title);
        System.out.println((reducedBook == null) ? "Book Not Found" : reducedBook.toString());
    }

    @Override
    public void increaseBookCopies(String date, int copies) {
        int booksFound = bookRepository.increaseBookCopiesFromAndBy(LocalDate.parse(date,
                DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)), copies);
        System.out.printf("%d books are released after %s, so total of %d book copies were added%n",
                booksFound, date, (booksFound * copies));
    }

    @Transactional
    @Override
    public void removeBooks(Integer copies) {
        List<Book> booksForRemove = bookRepository.getBooksByCopiesLessThan(copies);
        booksForRemove.forEach(b -> b.getAuthor().getBooks().remove(b));
        int countBooks = booksForRemove.size();
        booksForRemove.forEach(bookRepository::delete);
        System.out.printf("Books removed from table: %d%n", booksForRemove.size());
    }
}