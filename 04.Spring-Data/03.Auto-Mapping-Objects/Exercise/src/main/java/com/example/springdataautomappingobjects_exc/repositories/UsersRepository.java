package com.example.springdataautomappingobjects_exc.repositories;

import com.example.springdataautomappingobjects_exc.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    User findUserByEmailAndPassword(String email, String password);
}
