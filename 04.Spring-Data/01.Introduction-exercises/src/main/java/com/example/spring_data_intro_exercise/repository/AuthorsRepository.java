package com.example.spring_data_intro_exercise.repository;

import com.example.spring_data_intro_exercise.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorsRepository extends JpaRepository<Author, Long> {
    @Query("select a from Author a order by a.books.size desc")
    List<Author> findAllByBooksSizeDesc();
}
