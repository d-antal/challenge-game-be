package com.lottoland.game.challenge.rockpaperscissors.service;

import com.lottoland.game.challenge.rockpaperscissors.exception.ResourceNotFoundException;
import com.lottoland.game.challenge.rockpaperscissors.model.Game;

public interface GameService {

	Game createGame(Game game);

	Game getGameById(Long gameId) throws ResourceNotFoundException;

}
