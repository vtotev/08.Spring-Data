package com.example.advqueryingex.repository;

import com.example.advqueryingex.models.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitleAsc(String firstName, String lastName);

    List<Book> findAllByAgeRestriction(AgeRestriction age);

    List<Book> findBooksByCopiesIsLessThanAndEditionType(Integer copies, EditionType editionType);

    @Query("select b from Book b where b.price not between :less and :greater")
    List<Book> findByPriceLessAndGreater(@Param("less") BigDecimal lessThan,
                                         @Param("greater") BigDecimal greaterThan);

    @Query("select b from Book b where year(b.releaseDate) != :year")
    List<Book> findNotReleasedBooks(@Param("year") int year);

    List<Book> findBooksByReleaseDateBefore(LocalDate date);

    List<Book> findBooksByTitleContaining(String contains);

    @Query("select b from Book b join b.author where b.author.lastName like concat('%', :str, '%')")
    List<Book> findBooksByAuthorLastNameContains(@Param("str") String contains);

    @Query("select count(b) from Book b where length(b.title) > :leng")
    Integer countBooksByTitleLength(@Param("leng") int length);

    @Query("select sum(b.copies) from Book b where b.author = :auth")
    Integer findAllByAuthorAndSumBooksCount(@Param("auth") Author author);

    ReducedBook getBookByTitle(String title);

    @Transactional
    @Modifying
    @Query("update Book b set b.copies = b.copies + :cop where b.releaseDate > :date")
    Integer increaseBookCopiesFromAndBy(@Param("date") LocalDate date, @Param("cop") int copies);

    List<Book> getBooksByCopiesLessThan(Integer copies);

    @Transactional
    @Modifying
    Integer removeBooksByCopiesLessThan(Integer copies);
}
