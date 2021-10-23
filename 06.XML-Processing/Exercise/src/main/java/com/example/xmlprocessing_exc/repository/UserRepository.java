package com.example.xmlprocessing_exc.repository;

import com.example.xmlprocessing_exc.model.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u from User u where (select count(p) from Product p where p.seller.id = u.id and p.buyer is not null) > 0 " +
            "order by u.lastName, u.firstName")
    List<User> findAllUsersWithMoreThanOneSoldProduct();

    @Query("select u from User u where (select count(p) from Product p where p.seller.id = u.id and p.buyer is not null) > 0 " +
            "order by u.soldProducts.size desc, u.lastName ")
    List<User> findAllBySoldProductsNotNullOrderBySoldProductsDescLastName();
}
