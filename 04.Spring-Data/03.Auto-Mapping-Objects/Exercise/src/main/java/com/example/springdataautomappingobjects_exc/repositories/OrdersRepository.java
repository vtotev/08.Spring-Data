package com.example.springdataautomappingobjects_exc.repositories;

import com.example.springdataautomappingobjects_exc.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Long> {

}
