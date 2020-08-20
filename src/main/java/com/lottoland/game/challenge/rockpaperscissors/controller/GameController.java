package com.lottoland.game.challenge.rockpaperscissors.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lottoland.game.challenge.rockpaperscissors.model.Game;
import com.lottoland.game.challenge.rockpaperscissors.service.GameService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class GameController {

	@Autowired
	private GameService gameService;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(path = "/games")
	public Game createGame(@Valid @RequestBody Game game) {	
		return gameService.createGame(game);
	}

}
