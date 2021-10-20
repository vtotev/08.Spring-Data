package com.softuni.JSON__Processing_Exercise.Service.Impl;

import com.google.gson.Gson;
import com.softuni.JSON__Processing_Exercise.Constants.GlobalConsts;
import com.softuni.JSON__Processing_Exercise.Entities.User;
import com.softuni.JSON__Processing_Exercise.Repository.UsersRepository;
import com.softuni.JSON__Processing_Exercise.Service.UsersService;
import com.softuni.JSON__Processing_Exercise.Utils.ValidationUtil;
import com.softuni.JSON__Processing_Exercise.dto.UserSeedDto;
import com.softuni.JSON__Processing_Exercise.dto.UserSoldDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.softuni.JSON__Processing_Exercise.Constants.GlobalConsts.RESOURCE_FULL_PATH;

@Service
public class UsersServiceImpl implements UsersService {

    private static final String USERS_FILE_NAME = "users.json";
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final UsersRepository usersRepository;

    public UsersServiceImpl(ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil, UsersRepository usersRepository) {
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.usersRepository = usersRepository;
    }


    @Override
    public void seedUsers() throws IOException {
        if (usersRepository.count() > 0) {
            return;
        }

        Arrays.stream(gson.fromJson(Files.readString(Path.of(RESOURCE_FULL_PATH + USERS_FILE_NAME)), UserSeedDto[].class))
            .filter(validationUtil::isValid)
            .map(userSeedDto -> modelMapper.map(userSeedDto, User.class))
            .forEach(usersRepository::save);
    }

    @Override
    public User findRandomUser() {
        long randomId = ThreadLocalRandom.current().nextLong(1, usersRepository.count() + 1);
        return usersRepository.findById(randomId).orElse(null);
    }

    @Override
    public List<UserSoldDto> findAllUsersWithMoreThanOneSoldProducts() {
        return usersRepository.findAllUsersWithMoreThanOneSoldProductOrderByLastNameThenFirstName()
                .stream().map(user -> modelMapper.map(user, UserSoldDto.class))
                .collect(Collectors.toList());
    }
}
