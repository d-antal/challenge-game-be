package com.lottoland.game.challenge.rockpaperscissors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.lottoland.game.challenge.rockpaperscissors.exception.ResourceNotFoundException;
import com.lottoland.game.challenge.rockpaperscissors.model.Choose;
import com.lottoland.game.challenge.rockpaperscissors.model.Game;
import com.lottoland.game.challenge.rockpaperscissors.model.Result;
import com.lottoland.game.challenge.rockpaperscissors.model.Round;
import com.lottoland.game.challenge.rockpaperscissors.model.RoundsTotal;
import com.lottoland.game.challenge.rockpaperscissors.repository.RoundRepository;
import com.lottoland.game.challenge.rockpaperscissors.service.GameServiceImpl;
import com.lottoland.game.challenge.rockpaperscissors.service.RoundServiceImpl;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class RoundServiceTest {

	@InjectMocks
	private RoundServiceImpl roundService;

	@Mock
	private RoundRepository roundRepository;

	@Mock
	private GameServiceImpl gameService;

	private static final Game GAME = new Game();
	private static final Long GAME_ID = 1L;
	private static final Long GAME_ID_NOT_FOUND = 2L;
	private static final Round ROUND_FIRST_PLAYER_PAPER = Round.builder().firstPlayerChose(Choose.PAPER)
			.secondPlayerChose(Choose.ROCK).build();
	private static final Round ROUND_FIRST_PLAYER_SCISSORS = Round.builder().firstPlayerChose(Choose.SCISSORS)
			.secondPlayerChose(Choose.ROCK).build();
	private static final Round ROUND_FIRST_PLAYER_ROCK = Round.builder().firstPlayerChose(Choose.ROCK)
			.secondPlayerChose(Choose.ROCK).build();
	private static final List<Round> ROUND_LIST = Arrays.asList(ROUND_FIRST_PLAYER_PAPER, ROUND_FIRST_PLAYER_SCISSORS,
			ROUND_FIRST_PLAYER_ROCK);

	private Object[][] createRoundParams() {
		return new Object[][] { 
				{ ROUND_FIRST_PLAYER_PAPER, Result.FIRST_PLAYER_WON },
				{ ROUND_FIRST_PLAYER_SCISSORS, Result.SECOND_PLAYER_WON }, 
				{ ROUND_FIRST_PLAYER_ROCK, Result.DRAW } 
		};
	}

	private Object[][] getRoundsTotalParams() {
		return new Object[][] {
				{ Arrays.asList(Result.FIRST_PLAYER_WON, Result.SECOND_PLAYER_WON, Result.DRAW), new RoundsTotal(1, 1, 1) },
				{ Arrays.asList(Result.FIRST_PLAYER_WON, Result.FIRST_PLAYER_WON, Result.DRAW),	 new RoundsTotal(2, 0, 1) },
				{ Arrays.asList(Result.DRAW, Result.DRAW, Result.DRAW), new RoundsTotal(0, 0, 3) } 
		};
	}

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	@Parameters(method = "createRoundParams")
	public void testCreateRound(Round round, Result expectedResult) throws ResourceNotFoundException {
		when(gameService.getGameById(GAME_ID)).thenReturn(GAME);
		when(roundRepository.save(any(Round.class))).thenReturn(round);

		Round savedRound = roundService.createRound(round, GAME_ID);
		assertTrue(expectedResult.equals(savedRound.getResult()));

		verify(roundRepository, times(1)).save(round);
		verify(gameService, times(1)).getGameById(GAME_ID);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testCreateRoundWhenGameNotFound() throws ResourceNotFoundException {
		when(gameService.getGameById(GAME_ID_NOT_FOUND)).thenThrow(ResourceNotFoundException.class);

		roundService.createRound(ROUND_FIRST_PLAYER_PAPER, GAME_ID_NOT_FOUND);

		verify(gameService, times(1)).getGameById(GAME_ID_NOT_FOUND);
		verify(roundRepository, never()).save(any(Round.class));
	}

	@Test
	@Parameters(method = "getRoundsTotalParams")
	public void testGetRoundsTotal(List<Result> resultList, RoundsTotal expectedTotal)
			throws ResourceNotFoundException {
		when(roundRepository.getResults()).thenReturn(resultList);

		RoundsTotal generatedTotal = roundService.getRoundsTotal();
		assertEquals(expectedTotal, generatedTotal);

		verify(roundRepository, times(1)).getResults();
	}

	@Test
	public void testGetRounds() throws ResourceNotFoundException {
		when(roundRepository.findByGameId(GAME_ID)).thenReturn(ROUND_LIST);

		roundService.getRounds(GAME_ID);

		verify(roundRepository, times(1)).findByGameId(GAME_ID);
	}

}
