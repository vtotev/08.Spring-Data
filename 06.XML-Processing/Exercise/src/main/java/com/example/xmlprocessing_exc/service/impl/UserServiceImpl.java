package com.example.xmlprocessing_exc.service.impl;

import com.example.xmlprocessing_exc.model.Entities.User;
import com.example.xmlprocessing_exc.model.dto.*;
import com.example.xmlprocessing_exc.repository.UserRepository;
import com.example.xmlprocessing_exc.service.UserService;
import com.example.xmlprocessing_exc.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Component
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public long getEntityCount() {
        return userRepository.count();
    }

    @Override
    public void seedUsers(List<UserSeedDto> users) {
        users.stream()
                .filter(validationUtil::isValid)
                .map(userSeedDto -> modelMapper.map(userSeedDto, User.class))
                .forEach(userRepository::save);
    }

    @Override
    public User getRandomUser() {
        long randomId = ThreadLocalRandom.current().nextLong(1, userRepository.count() + 1);
        return userRepository.findById(randomId).orElse(null);
    }

    @Override
    public UserViewRootDto findUsersWithMoreThanOneSoldProduct() {
        UserViewRootDto userViewRootDto = new UserViewRootDto();
        userViewRootDto.setProducts(userRepository.findAllUsersWithMoreThanOneSoldProduct()
                .stream()
                .map(user -> modelMapper.map(user, UserWithProductsDto.class))
                .collect(Collectors.toList()));
        return userViewRootDto;
    }

    @Override
    public UsersCountRootDto findUsersWithSoldProductsOrderedByCount() {
        UsersCountRootDto usersCountRootDto = new UsersCountRootDto();
        usersCountRootDto.setUsers(userRepository.findAllBySoldProductsNotNullOrderBySoldProductsDescLastName()
                .stream()
                .map(user -> modelMapper.map(user, UsersCountDto.class))
                .collect(Collectors.toList()));
        usersCountRootDto.setCount(usersCountRootDto.getUsers().size());
        return usersCountRootDto;
    }
}
