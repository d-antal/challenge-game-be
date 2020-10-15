package com.lottoland.game.challenge.rockpaperscissors;

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
import org.springframework.test.context.junit4.SpringRunner;

import com.lottoland.game.challenge.rockpaperscissors.controller.RoundController;
import com.lottoland.game.challenge.rockpaperscissors.exception.ResourceNotFoundException;
import com.lottoland.game.challenge.rockpaperscissors.model.Choose;
import com.lottoland.game.challenge.rockpaperscissors.model.Result;
import com.lottoland.game.challenge.rockpaperscissors.model.Round;
import com.lottoland.game.challenge.rockpaperscissors.model.RoundsTotal;
import com.lottoland.game.challenge.rockpaperscissors.repository.RoundRepository;
import com.lottoland.game.challenge.rockpaperscissors.service.RoundServiceImpl;

@RunWith(SpringRunner.class)
public class RoundControllerTest {

	@InjectMocks
	private RoundController roundController;

	@Mock
	private RoundRepository roundRepository;

	@Mock
	private RoundServiceImpl roundService;

	private static final Long GAME_ID = 1L;
	private static final Long GAME_ID_NOT_FOUND = 2L;
	private static final Round ROUND = Round.builder().firstPlayerChose(Choose.PAPER).secondPlayerChose(Choose.ROCK)
			.result(Result.FIRST_PLAYER_WON).build();
	private static final List<Round> ROUND_LIST = Arrays.asList(ROUND);
	private static final RoundsTotal ROUNDS_TOTAL = new RoundsTotal(1, 1, 1);

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetRoundsByGame() {
		when(roundService.getRounds(GAME_ID)).thenReturn(ROUND_LIST);

		roundController.getRoundsByGame(GAME_ID);

		verify(roundService, times(1)).getRounds(GAME_ID);
	}

	@Test
	public void testCreateRound() throws ResourceNotFoundException {
		when(roundService.createRound(ROUND, GAME_ID)).thenReturn(ROUND);

		roundController.createRound(GAME_ID, ROUND);

		verify(roundService, times(1)).createRound(ROUND, GAME_ID);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testCreateRoundWhenGameNotFound() throws ResourceNotFoundException {
		when(roundService.createRound(ROUND, GAME_ID_NOT_FOUND)).thenThrow(ResourceNotFoundException.class);

		roundController.createRound(GAME_ID_NOT_FOUND, ROUND);

		verify(roundService, times(1)).createRound(ROUND, GAME_ID_NOT_FOUND);
	}

	@Test
	public void testGetRoundsTotal() {
		when(roundService.getRoundsTotal()).thenReturn(ROUNDS_TOTAL);

		roundController.getRoundsTotal();

		verify(roundService, times(1)).getRoundsTotal();
	}

}
