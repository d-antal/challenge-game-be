package com.lottoland.game.challenge.rockpaperscissors;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import com.lottoland.game.challenge.rockpaperscissors.exception.ResourceNotFoundException;
import com.lottoland.game.challenge.rockpaperscissors.model.Game;
import com.lottoland.game.challenge.rockpaperscissors.repository.GameRepository;
import com.lottoland.game.challenge.rockpaperscissors.service.GameServiceImpl;

@RunWith(SpringRunner.class)
public class GameServiceTest {

	@InjectMocks
	private GameServiceImpl gameService;

	@Mock
	private GameRepository gameRepository;

	private static final Game GAME = new Game();
	private static final Long GAME_ID = 1L;
	private static final Long GAME_ID_NOT_FOUND = 2L;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCreateGame() {
		when(gameRepository.save(GAME)).thenReturn(GAME);

		gameService.createGame(GAME);

		verify(gameRepository, times(1)).save(GAME);
	}

	@Test
	public void testFindGameById() throws ResourceNotFoundException {
		when(gameRepository.findById(GAME_ID)).thenReturn(Optional.of(GAME));

		gameService.getGameById(GAME_ID);

		verify(gameRepository, times(1)).findById(GAME_ID);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testFindGameByIdWhenNotFound() throws ResourceNotFoundException {
		when(gameRepository.findById(GAME_ID_NOT_FOUND)).thenReturn(Optional.empty());

		gameService.getGameById(GAME_ID_NOT_FOUND);

		verify(gameRepository, times(1)).findById(GAME_ID_NOT_FOUND);
	}

}
