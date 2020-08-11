package com.lottoland.game.challenge.rockpaperscissors.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottoland.game.challenge.rockpaperscissors.exception.ResourceNotFoundException;
import com.lottoland.game.challenge.rockpaperscissors.model.Game;
import com.lottoland.game.challenge.rockpaperscissors.repository.GameRepository;

@Service
@Transactional
public class GameServiceImpl implements GameService {

	@Autowired
	private GameRepository gameRepository;

	@Override
	public Game createGame(Game game) {
		return gameRepository.save(game);
	}

	@Override
	public Game getGameById(Long gameId) throws ResourceNotFoundException {
		return gameRepository.findById(gameId)
				.orElseThrow(() -> new ResourceNotFoundException("Game not found by id: " + gameId));
	}

}
