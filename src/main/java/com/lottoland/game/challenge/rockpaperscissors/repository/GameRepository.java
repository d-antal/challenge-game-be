package com.lottoland.game.challenge.rockpaperscissors.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lottoland.game.challenge.rockpaperscissors.model.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

}
