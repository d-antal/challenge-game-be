package com.lottoland.game.challenge.rockpaperscissors.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lottoland.game.challenge.rockpaperscissors.model.Result;
import com.lottoland.game.challenge.rockpaperscissors.model.Round;

@Repository
public interface RoundRepository extends JpaRepository<Round, Long> {

	List<Round> findByGameId(Long id);

	@Query("select r.result from Round r")
	List<Result> getResults();

}
