package com.example.advqueryingex.service.impl;

import com.example.advqueryingex.models.entities.Author;
import com.example.advqueryingex.repository.AuthorRepository;
import com.example.advqueryingex.service.AuthorService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.example.advqueryingex.constants.GlobalConstants.AUTHORS_FILE_NAME;
import static com.example.advqueryingex.constants.GlobalConstants.RESOURCE_PATH;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    @Override
    public void seedAuthors() throws IOException {
        if (authorRepository.count() > 0) {
            return;
        }

        Files.readAllLines(Path.of(RESOURCE_PATH + AUTHORS_FILE_NAME))
                .stream()
                .filter(s -> !"".equals(s))
                .forEach(author -> {
                    String[] info = author.split("\\s+");
                    authorRepository
                            .save(new Author(info[0], info[1]));
                });

    }

    @Override
    public int getSize() {
        return (int) this.authorRepository.count();
    }

    @Override
    public Author getRandomAuthor() {
        Random random = new Random();
        long randomId = random.nextInt((int) authorRepository.count()) + 1;

        return authorRepository.findById(randomId)
                .orElseThrow(() -> new EntityNotFoundException("Author not found"));
    }

    @Override
    public List<Author> getAllAuthorsAndCountOfTheirBooks() {
        return authorRepository
                .findAll()
                .stream()
                .sorted((a,b) -> b.getBooks().size() - a.getBooks().size())
                .collect(Collectors.toList());
    }

    @Override
    public List<Author> getAuthorsByEndString(String endStr) {
        return authorRepository.findAllByFirstNameEndsWith(endStr);
    }

}
