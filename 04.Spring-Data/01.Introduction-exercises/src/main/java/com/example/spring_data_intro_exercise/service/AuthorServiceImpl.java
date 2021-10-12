package com.example.spring_data_intro_exercise.service;

import com.example.spring_data_intro_exercise.model.Author;
import com.example.spring_data_intro_exercise.repository.AuthorsRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService{
    private final String authorsPath = "C:\\Users\\VaL\\Desktop\\resources\\authors.txt";

    private final AuthorsRepository authorsRepository;

    public AuthorServiceImpl(AuthorsRepository authorsRepository) {
        this.authorsRepository = authorsRepository;
    }

    @Override
    public void seedAuthors() throws IOException {
        if (authorsRepository.count() > 0) {
            return;
        }

        Files.readAllLines(Path.of(authorsPath))
                .stream().filter(a -> !a.isEmpty())
                .forEach(auth -> {
                    Author author = new Author();
                    String[] authorData = auth.split("\\s+");
                    author.setFirstName(authorData[0]);
                    author.setLastName(authorData[1]);
                    authorsRepository.save(author);
                });

    }

    @Override
    public Author getRandomAuthor() {
        long randomId = ThreadLocalRandom.current().nextLong(1, authorsRepository.count() + 1);
        return authorsRepository.getById(randomId);
    }

    @Override
    public List<String> getAllAuthorsOrderByCountOfTheirBooksDesc() {
        return authorsRepository.findAllByBooksSizeDesc().stream()
                .map(author -> String.format("%s %s %d", author.getFirstName(),
                        author.getLastName(), author.getBooks().size()))
                .collect(Collectors.toList());
    }

}
