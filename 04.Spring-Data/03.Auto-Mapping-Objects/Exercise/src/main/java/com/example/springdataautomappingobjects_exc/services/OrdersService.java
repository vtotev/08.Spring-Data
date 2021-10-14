package com.example.springdataautomappingobjects_exc.services;

import com.example.springdataautomappingobjects_exc.entities.User;

public interface OrdersService {
    void addItem(User user, String gameTitle);
}
