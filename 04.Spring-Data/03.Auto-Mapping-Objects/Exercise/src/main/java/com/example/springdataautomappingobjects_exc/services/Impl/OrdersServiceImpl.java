package com.example.springdataautomappingobjects_exc.services.Impl;

import com.example.springdataautomappingobjects_exc.entities.User;
import com.example.springdataautomappingobjects_exc.repositories.OrdersRepository;
import com.example.springdataautomappingobjects_exc.services.OrdersService;

public class OrdersServiceImpl implements OrdersService {
    private final OrdersRepository ordersRepo;

    public OrdersServiceImpl(OrdersRepository ordersRepo) {
        this.ordersRepo = ordersRepo;
    }

    @Override
    public void addItem(User user, String gameTitle) {

    }
}
