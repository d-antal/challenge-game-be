package com.lottoland.game.challenge.rockpaperscissors.service;

import java.util.List;

import com.lottoland.game.challenge.rockpaperscissors.exception.ResourceNotFoundException;
import com.lottoland.game.challenge.rockpaperscissors.model.Round;
import com.lottoland.game.challenge.rockpaperscissors.model.RoundsTotal;

public interface RoundService {

	List<Round> getRounds(Long gameId);

	Round createRound(Round round, Long gameId) throws ResourceNotFoundException;

	RoundsTotal getRoundsTotal();

}
