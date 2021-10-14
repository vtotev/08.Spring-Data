package com.example.springdataautomappingobjects_exc.services;

import com.example.springdataautomappingobjects_exc.entities.User;

public interface UsersService {
    User registerUser(String email, String password, String confirmPassword, String fullName);
    User loginUser(String email, String password);
}
