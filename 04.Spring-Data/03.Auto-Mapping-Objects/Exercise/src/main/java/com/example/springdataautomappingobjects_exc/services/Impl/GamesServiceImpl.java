package com.example.springdataautomappingobjects_exc.services.Impl;

import com.example.springdataautomappingobjects_exc.dao.GameDetailsDto;
import com.example.springdataautomappingobjects_exc.dao.GameDto;
import com.example.springdataautomappingobjects_exc.entities.Game;
import com.example.springdataautomappingobjects_exc.entities.User;
import com.example.springdataautomappingobjects_exc.repositories.GamesRepository;
import com.example.springdataautomappingobjects_exc.services.GamesService;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
public class GamesServiceImpl implements GamesService {
    private final GamesRepository gamesRepo;

    public GamesServiceImpl(GamesRepository gamesRepo) {
        this.gamesRepo = gamesRepo;
    }

    @Override
    public void addGame(String[] data) {
        String title = data[1];
        if (gamesRepo.findGameByTitle(title) != null) {
            throw new IllegalStateException("Game already exists in database.");
        }
        double price = Double.parseDouble(data[2]);
        double size = Double.parseDouble(data[3]);
        String trailer = data[4];
        String thumbUrl = data[5];
        String description = data[6];
        LocalDate releaseDate = LocalDate.parse(data[7], DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        Game game = new Game(title, trailer, thumbUrl, size, price, description, releaseDate);
        gamesRepo.save(game);
        System.out.printf("Added %s%n", game.getTitle());
    }

    @Override
    public void editGame(String[] data) {
        Long id = Long.parseLong(data[1]);
        Game gameToEdit = gamesRepo.findGameById(id);

        if (gameToEdit == null) {
            throw new IllegalArgumentException("Game don't exist in database.");
        }

        for (int i = 2; i < data.length; i++) {
            String[] tokens = data[i].split("=");
            switch (tokens[0].toLowerCase()) {
                case "title" -> gameToEdit.setTitle(tokens[1]);
                case "price" -> gameToEdit.setPrice(Double.valueOf(tokens[1]));
                case "size" -> gameToEdit.setSize(Double.valueOf(tokens[1]));
                case "trailer" -> gameToEdit.setTrailer(tokens[1]);
                case "thumbnailurl" -> gameToEdit.setImageThumbnail(tokens[1]);
                case "description" -> gameToEdit.setDescription(tokens[1]);
                case "releasedate" -> gameToEdit.setReleaseDate(LocalDate.parse(tokens[1], DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            }
        }
        gamesRepo.save(gameToEdit);
        System.out.printf("Edited %s%n", gameToEdit.getTitle());
    }

    @Override
    public void deleteGame(Long id) {
        Game gameToDelete = gamesRepo.findGameById(id);
        if (gameToDelete == null) {
            throw new IllegalArgumentException("Game don't exist in database");
        }
        gamesRepo.delete(gameToDelete);
        System.out.printf("Deleted %s%n", gameToDelete.getTitle());
    }

    @Override
    public void printAllGames() {
        gamesRepo.findAll().forEach(g -> {
            ModelMapper modelMapper = new ModelMapper();
            GameDto gameDto = modelMapper.map(g, GameDto.class);
            System.out.printf("%s %.2f%n", gameDto.getTitle(), gameDto.getPrice());
        });
    }

    @Override
    public void printGameDetails(String gameTitle) {
        Game game = gamesRepo.findGameByTitle(gameTitle);
        if (game == null) {
            throw new IllegalArgumentException("Game don't exist in database");
        }
        ModelMapper modelMapper = new ModelMapper();
        GameDetailsDto gdDto = modelMapper.map(game, GameDetailsDto.class);
        System.out.printf("Title: %s%nPrice: %.2f%nDescription: %s%nRelease date: %s%n",
                gdDto.getTitle(), gdDto.getPrice(), gdDto.getDescription(),
                gdDto.getReleaseDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    }

    @Override
    public void printOwnedGames(User user) {
        if (user == null) {
            throw new IllegalStateException("No logged user");
        }
        if (user.getGames().isEmpty()) {
            throw new IllegalStateException("User don't have any games.");
        }
        user.getGames().forEach(g -> {
            ModelMapper modelMapper = new ModelMapper();
            GameDto gameDto = modelMapper.map(g, GameDto.class);
            System.out.println(gameDto.getTitle());
        });
    }
}
