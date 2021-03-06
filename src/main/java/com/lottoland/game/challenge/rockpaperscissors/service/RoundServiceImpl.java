package com.lottoland.game.challenge.rockpaperscissors.service;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottoland.game.challenge.rockpaperscissors.exception.ResourceNotFoundException;
import com.lottoland.game.challenge.rockpaperscissors.model.Choose;
import com.lottoland.game.challenge.rockpaperscissors.model.Game;
import com.lottoland.game.challenge.rockpaperscissors.model.Result;
import com.lottoland.game.challenge.rockpaperscissors.model.Round;
import com.lottoland.game.challenge.rockpaperscissors.model.RoundsTotal;
import com.lottoland.game.challenge.rockpaperscissors.repository.RoundRepository;

@Service
@Transactional
public class RoundServiceImpl implements RoundService {

	@Autowired
	private RoundRepository roundRepository;

	@Autowired
	private GameService gameService;

	@Override
	public List<Round> getRounds(Long gameId) {
		return roundRepository.findByGameId(gameId);
	}

	@Override
	public Round createRound(Round round, Long gameId) throws ResourceNotFoundException {
		Game game = gameService.getGameById(gameId);
		round.setGame(game);
		round.setResult(createResult((round.getFirstPlayerChose()), (round.getSecondPlayerChose())));

		return roundRepository.save(round);
	}

	@Override
	public RoundsTotal getRoundsTotal() {
		List<Result> resultList = roundRepository.getResults();

		Map<String, Long> resultMap = resultList.stream().collect(groupingBy(Result::name, counting()));

		Arrays.stream(Result.values()).forEach(result -> {
			if (!resultMap.containsKey(result.name())) {
				resultMap.put(result.name(), 0l);
			}
		});

		return new RoundsTotal(resultMap.get(Result.FIRST_PLAYER_WON.name()).intValue(),
				resultMap.get(Result.SECOND_PLAYER_WON.name()).intValue(),
				resultMap.get(Result.DRAW.name()).intValue());
	}

	private Result createResult(Choose firstPlayer, Choose secondPlayer) {
		if (firstPlayer.equals(secondPlayer)) {
			return Result.DRAW;
		} else if (firstPlayer.equals(Choose.ROCK)) {
			return secondPlayer.equals(Choose.PAPER) ? Result.SECOND_PLAYER_WON : Result.FIRST_PLAYER_WON;
		} else if (firstPlayer.equals(Choose.PAPER)) {
			return secondPlayer.equals(Choose.SCISSORS) ? Result.SECOND_PLAYER_WON : Result.FIRST_PLAYER_WON;
		} else {
			return secondPlayer.equals(Choose.ROCK) ? Result.SECOND_PLAYER_WON : Result.FIRST_PLAYER_WON;
		}
	}

}
