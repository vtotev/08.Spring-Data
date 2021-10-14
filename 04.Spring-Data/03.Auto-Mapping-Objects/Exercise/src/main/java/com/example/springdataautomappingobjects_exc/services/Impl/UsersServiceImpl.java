package com.example.springdataautomappingobjects_exc.services.Impl;

import com.example.springdataautomappingobjects_exc.dao.UserDto;
import com.example.springdataautomappingobjects_exc.entities.User;
import com.example.springdataautomappingobjects_exc.repositories.UsersRepository;
import com.example.springdataautomappingobjects_exc.services.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepo;

    public UsersServiceImpl(UsersRepository usersRepo) {
        this.usersRepo = usersRepo;
    }

    @Transactional
    @Override
    public User registerUser(String email, String password, String confirmPassword, String fullName) {
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords don't match.");
        }
        boolean isAdmin = usersRepo.findAll().isEmpty();
        User user = new User(email, password, fullName, isAdmin);
        return usersRepo.save(user);
    }

    @Override
    public User loginUser(String email, String password) {
        User user = usersRepo.findUserByEmailAndPassword(email, password);
        if (user == null) {
            throw new IllegalArgumentException("Incorrect username / password");
        }
        return user;
    }
}
