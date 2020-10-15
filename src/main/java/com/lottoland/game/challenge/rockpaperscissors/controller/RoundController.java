package com.lottoland.game.challenge.rockpaperscissors.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lottoland.game.challenge.rockpaperscissors.exception.ResourceNotFoundException;
import com.lottoland.game.challenge.rockpaperscissors.model.Round;
import com.lottoland.game.challenge.rockpaperscissors.model.RoundsTotal;
import com.lottoland.game.challenge.rockpaperscissors.service.RoundService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class RoundController {

	@Autowired
	private RoundService roundService;

	@GetMapping(path = "/games/{gameId}/rounds")
	public List<Round> getRoundsByGame(@PathVariable(value = "gameId") Long gameId) {
		return roundService.getRounds(gameId);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(path = "/games/{gameId}/rounds")
	public Round createRound(@PathVariable(value = "gameId") Long gameId, @Valid @RequestBody Round round)
			throws ResourceNotFoundException {
		return roundService.createRound(round, gameId);
	}

	@GetMapping(path = "/rounds/total")
	public RoundsTotal getRoundsTotal() {
		return roundService.getRoundsTotal();
	}

}
