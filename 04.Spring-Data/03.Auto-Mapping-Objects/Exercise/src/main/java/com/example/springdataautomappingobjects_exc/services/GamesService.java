package com.example.springdataautomappingobjects_exc.services;

import com.example.springdataautomappingobjects_exc.entities.User;

public interface GamesService {
    void addGame(String[] data);
    void editGame(String[] data);
    void deleteGame(Long id);

    void printAllGames();
    void printGameDetails(String gameTitle);
    void printOwnedGames(User user);
}
