package com.example.advqueryingex;

import com.example.advqueryingex.models.entities.AgeRestriction;
import com.example.advqueryingex.service.AuthorService;
import com.example.advqueryingex.service.BookService;
import com.example.advqueryingex.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {


    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
//        seedData();
        Scanner scan = new Scanner(System.in);
        List.of("Exercises:", "-".repeat(35), "1.\tBooks Titles by Age Restriction", "2.\tGolden Books", "3.\tBooks by Price",
                "4.\tNot Released Books", "5.\tBooks Released Before Date", "6.\tAuthors Search",
                "7.\tBooks Search", "8.\tBook Titles Search", "9.\tCount Books", "10.\tTotal Book Copies",
                "11.\tReduced Book", "12.\t* Increase Book Copies", "13.\t* Remove Books", "-".repeat(35))
                .forEach(System.out::println);


        System.out.print("Please enter exercise number to run: ");
        int exerciseNum = Integer.parseInt(scan.nextLine());
        switch (exerciseNum) {
            case 1 -> ex1_BookTitlesByAgeRestriction(scan);
            case 2 -> ex2_GoldenBooks();
            case 3 -> ex3_BooksByPrice();
            case 4 -> ex4_NotReleasedBooks(scan);
            case 5 -> ex5_BooksReleasedBeforeDate(scan);
            case 6 -> ex6_AuthorsSearch(scan);
            case 7 -> ex7_BooksSearch(scan);
            case 8 -> ex8_BookTilesSearch(scan);
            case 9 -> ex9_CountBooks(scan);
            case 10 -> ex10_TotalBookCopiesByRandomAUthor();
            case 11 -> ex11_ReducedBook(scan);
            case 12 -> ex12_IncreaseBookCopies(scan);
            case 13 -> ex13_RemoveBooks(scan);
        }
    }

    private void ex1_BookTitlesByAgeRestriction(Scanner scan) {
        printTitle("1.\tBooks Titles by Age Restriction%n");
        bookService.printAllByAgeRestriction(AgeRestriction.valueOf(scan.nextLine().toUpperCase()));
        //bookService.printAllByAgeRestriction(AgeRestriction.MINOR);
    }

    private void ex2_GoldenBooks() {
        printTitle("2.\tGolden Books%n");
        bookService.printAllGoldenEditionBooks();
    }

    private void ex3_BooksByPrice() {
        printTitle("3.\tBooks by Price%n");
        bookService.printAllBooksByPrice();
    }

    private void ex4_NotReleasedBooks(Scanner scan) {
        printTitle("4.\tNot Released Books%n");
        System.out.print("Enter year: ");
        bookService.printBooksNotReleased(Integer.parseInt(scan.nextLine()));

    }

    private void ex5_BooksReleasedBeforeDate(Scanner scan) {
        printTitle("5.\tBooks Released Before Date%n");
        System.out.print("Enter date in format dd-mm-yyyy : ");
        String date = scan.nextLine();
        bookService.printBooksReleasedBefore(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
//        bookService.printBooksReleasedBefore(LocalDate.parse("12-04-1992", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
//        bookService.printBooksReleasedBefore(LocalDate.parse("30-12-1989", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    }

    private void ex6_AuthorsSearch(Scanner scan) {
        printTitle("6.\tAuthors Search%n");
        System.out.print("Prints authors whose first name ends with a: ");
        authorService.getAuthorsByEndString(scan.nextLine()).forEach(a -> System.out.printf("%s %s%n", a.getFirstName(), a.getLastName()));
//        authorService.getAuthorsByEndString("e").forEach(a -> System.out.printf("%s %s%n", a.getFirstName(), a.getLastName()));
//        authorService.getAuthorsByEndString("dy").forEach(a -> System.out.printf("%s %s%n", a.getFirstName(), a.getLastName()));

    }

    private void ex7_BooksSearch(Scanner scan) {
        // 7.	Books Search
        printTitle("7.\tBooks Search%n");
        System.out.print("Print titles of books, which contain a: ");
        bookService.printBooksWithTitleContaining(scan.nextLine());
//        bookService.printBooksWithTitleContaining("sK");
//        bookService.printBooksWithTitleContaining("WOR");
    }

    private void ex8_BookTilesSearch(Scanner scan) {
        // 8.	Book Titles Search
        printTitle("8.\tBook Titles Search%n");
        System.out.print("Prints the titles of books, which are written by authors, whose last name starts with a: ");
        bookService.printBooksByAuthorLastNameContains(scan.nextLine());
//        bookService.printBooksByAuthorLastNameContains("Ric");
//        bookService.printBooksByAuthorLastNameContains("gr");
    }

    private void ex9_CountBooks(Scanner scan) {
        printTitle("9.\tCount Books%n");
        System.out.print("Prints the number of books, whose title is longer than: ");
        bookService.printBooksWithTitleLengthGreaterThan(Integer.parseInt(scan.nextLine()));
//        bookService.printBooksWithTitleLengthGreaterThan(12);
//        bookService.printBooksWithTitleLengthGreaterThan(40);
    }

    private void ex10_TotalBookCopiesByRandomAUthor() {
        printTitle("10.\tTotal Book Copies (by Random Author)%n");
        bookService.printBookCopiesByAuthor();
    }

    private void ex11_ReducedBook(Scanner scan) {
        printTitle("11.\tReduced Book%n");
        System.out.print("Prints information for a book by given title: ");
        bookService.printReducedBookDetailsByTitle(scan.nextLine());
//        bookService.printReducedBookDetailsByTitle("Things Fall Apart");
    }

    // 12.	* IncreaseBookCopies
    private void ex12_IncreaseBookCopies(Scanner scan) {
        printTitle("12.\t* Increase Book Copies%n");
        System.out.print("Enter date in format dd MMM yyyy (12 Oct 2005): ");
        String date = scan.nextLine();
        System.out.print("Enter count of copies to be added: ");
        int copies = Integer.parseInt(scan.nextLine());
        bookService.increaseBookCopies(date, copies);
//        bookService.increaseBookCopies("12 Oct 2005", 100);
//        bookService.increaseBookCopies("06 Jun 2013", 44);
    }

    // 13.	* Remove Books
    private void ex13_RemoveBooks(Scanner scan) {
        printTitle("13.\t* Remove Books%n");
        System.out.print("Delete books with copies less than: ");
        bookService.removeBooks(Integer.parseInt(scan.nextLine()));
    }

    private void printTitle(String s) {
        System.out.printf(s);
        System.out.println("-".repeat(50));
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
