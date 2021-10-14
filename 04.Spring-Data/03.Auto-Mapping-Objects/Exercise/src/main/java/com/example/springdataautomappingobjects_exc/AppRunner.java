package com.example.springdataautomappingobjects_exc;

import com.example.springdataautomappingobjects_exc.dao.UserDto;
import com.example.springdataautomappingobjects_exc.entities.User;
import com.example.springdataautomappingobjects_exc.services.GamesService;
import com.example.springdataautomappingobjects_exc.services.UsersService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class AppRunner implements CommandLineRunner {
    private final UsersService usersService;
    private final GamesService gamesService;
    private User loggedUser;

    public AppRunner(UsersService usersService, GamesService gamesService) {
        this.usersService = usersService;
        this.gamesService = gamesService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scan = new Scanner(System.in);
        System.out.print("Please enter command (RegisterUser/LoginUser/AddGame/EditGame/DeleteGame/Logout) ot \"Quit\" to end application: ");
        String input = scan.nextLine();
        while (!input.equalsIgnoreCase("quit")) {
            String[] tokens = input.split("\\|");
            try {
                switch (tokens[0].toLowerCase()) {
                    // 2.	Implement User Registration, Login and Logout
                    case "registeruser" -> {
                        usersService.registerUser(tokens[1], tokens[2], tokens[3], tokens[4]);
                        System.out.printf("%s was registered%n", tokens[4]);
                    }
                    case "loginuser" -> {
                        if (loggedUser != null) {
                            throw new IllegalStateException("Cannot log in. Another session is already active.");
                        }
                        loggedUser = usersService.loginUser(tokens[1], tokens[2]);
                        System.out.printf("Successfully logged in %s%n", loggedUser.getFullName());
                    }
                    case "logout" -> {
                        if (loggedUser != null) {
                            System.out.printf("User %s successfully logged out%n", loggedUser.getFullName());
                            loggedUser = null;
                        } else {
                            throw new IllegalStateException("Cannot log out. No user was logged in.");
                        }
                    }
                    // 3.	Implement Managing Games
                    case "addgame" -> {
                        checkUserRights();
                        gamesService.addGame(tokens);
                    }
                    case "editgame" -> {
                        checkUserRights();
                        gamesService.editGame(tokens);
                    }
                    case "deletegame" -> {
                        checkUserRights();
                        Long idToDelete = Long.parseLong(tokens[1]);
                        gamesService.deleteGame(idToDelete);
                    }

                    // 4.	Implement View Games
                    case "allgames" -> gamesService.printAllGames();
                    case "detailgame" -> gamesService.printGameDetails(tokens[1]);
                    case "ownedgames" -> gamesService.printOwnedGames(loggedUser);


                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }
            input = scan.nextLine();
        }
    }

    private void checkUserRights() {
        if (!loggedUser.isAdmin()) {
            throw new IllegalStateException("Current user don't have rights to make changes");
        }
    }

}
