package com.example.xmlprocessing_exc.service;

import com.example.xmlprocessing_exc.model.Entities.User;
import com.example.xmlprocessing_exc.model.dto.*;

import java.util.List;

public interface UserService {
    long getEntityCount();

    void seedUsers(List<UserSeedDto> users);

    User getRandomUser();

    UserViewRootDto findUsersWithMoreThanOneSoldProduct();

    UsersCountRootDto findUsersWithSoldProductsOrderedByCount();
}
