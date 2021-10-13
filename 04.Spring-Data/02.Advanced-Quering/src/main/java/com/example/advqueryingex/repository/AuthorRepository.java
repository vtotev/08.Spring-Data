package com.example.advqueryingex.repository;


import com.example.advqueryingex.models.entities.Author;
import com.example.advqueryingex.models.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findAllByFirstNameEndsWith(String endStr);
}