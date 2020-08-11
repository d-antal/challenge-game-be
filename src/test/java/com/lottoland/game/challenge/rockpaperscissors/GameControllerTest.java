package com.lottoland.game.challenge.rockpaperscissors;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import com.lottoland.game.challenge.rockpaperscissors.controller.GameController;
import com.lottoland.game.challenge.rockpaperscissors.model.Game;
import com.lottoland.game.challenge.rockpaperscissors.service.GameServiceImpl;

@RunWith(SpringRunner.class)
public class GameControllerTest {

	@InjectMocks
	private GameController gameController;

	@Mock
	private GameServiceImpl gameService;

	private static final Game GAME = new Game();

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCreateGame() {
		when(gameService.createGame(GAME)).thenReturn(GAME);

		gameController.createGame(GAME);

		verify(gameService, times(1)).createGame(GAME);
	}

}
