package com.example.springdataautomappingobjects_exc.repositories;

import com.example.springdataautomappingobjects_exc.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GamesRepository extends JpaRepository<Game, Long> {
    Game findGameById(Long id);
    Game findGameByTitle(String title);
}
