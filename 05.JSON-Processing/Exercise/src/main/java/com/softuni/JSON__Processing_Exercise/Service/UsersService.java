package com.softuni.JSON__Processing_Exercise.Service;

import com.softuni.JSON__Processing_Exercise.Entities.User;
import com.softuni.JSON__Processing_Exercise.dto.UserSoldDto;

import java.io.IOException;
import java.util.List;

public interface UsersService {
    void seedUsers() throws IOException;
    User findRandomUser();

    List<UserSoldDto> findAllUsersWithMoreThanOneSoldProducts();
}
