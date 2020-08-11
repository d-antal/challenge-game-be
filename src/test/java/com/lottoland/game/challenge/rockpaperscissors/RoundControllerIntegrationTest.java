package com.lottoland.game.challenge.rockpaperscissors;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lottoland.game.challenge.rockpaperscissors.model.Choose;
import com.lottoland.game.challenge.rockpaperscissors.model.Result;
import com.lottoland.game.challenge.rockpaperscissors.model.Round;
import com.lottoland.game.challenge.rockpaperscissors.model.RoundsTotal;
import com.lottoland.game.challenge.rockpaperscissors.service.RoundServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RoundControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RoundServiceImpl roundService;

	private static final ObjectMapper OM = new ObjectMapper();
	private static final String URI_ROUNDS = "/api/v1/games/1/rounds";
	private static final String URI_ROUNDS_TOTAL = "/api/v1/games/rounds/total";
	private static final Long GAME_ID = 1L;
	private static final Round ROUND_1 = new Round(1L, Choose.PAPER, Choose.ROCK, Result.FIRST_PLAYER_WON, null);
	private static final Round ROUND_2 = new Round(2L, Choose.SCISSORS, Choose.ROCK, Result.SECOND_PLAYER_WON, null);
	private static final List<Round> ROUND_LIST = Arrays.asList(ROUND_1, ROUND_2);
	private static final RoundsTotal ROUNDS_TOTAL = new RoundsTotal(1, 1, 1, 3);

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetRoundsByGame() throws Exception {
		when(roundService.getRounds(GAME_ID)).thenReturn(ROUND_LIST);

		mockMvc.perform(get(URI_ROUNDS).content(OM.writeValueAsString(ROUND_LIST)).header(HttpHeaders.CONTENT_TYPE,
				MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].firstPlayerChose", is(Choose.PAPER.name())))
				.andExpect(jsonPath("$[0].secondPlayerChose", is(Choose.ROCK.name())))
				.andExpect(jsonPath("$[0].result", is(Result.FIRST_PLAYER_WON.name())))
				.andExpect(jsonPath("$[1].id", is(2)))
				.andExpect(jsonPath("$[1].firstPlayerChose", is(Choose.SCISSORS.name())))
				.andExpect(jsonPath("$[1].secondPlayerChose", is(Choose.ROCK.name())))
				.andExpect(jsonPath("$[1].result", is(Result.SECOND_PLAYER_WON.name())));

		verify(roundService, times(1)).getRounds(1L);
	}

	@Test
	public void testCreateRound() throws Exception {
		when(roundService.createRound(ROUND_1, GAME_ID)).thenReturn(ROUND_1);

		mockMvc.perform(post(URI_ROUNDS).content(OM.writeValueAsString(ROUND_1)).header(HttpHeaders.CONTENT_TYPE,
				MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.firstPlayerChose", is(Choose.PAPER.name())))
				.andExpect(jsonPath("$.secondPlayerChose", is(Choose.ROCK.name())))
				.andExpect(jsonPath("$.result", is(Result.FIRST_PLAYER_WON.name())));

		verify(roundService, times(1)).createRound(ROUND_1, GAME_ID);

	}

	@Test
	public void testGetRoundsTotal() throws Exception {
		when(roundService.getRoundsTotal()).thenReturn(ROUNDS_TOTAL);

		mockMvc.perform(get(URI_ROUNDS_TOTAL).content(OM.writeValueAsString(ROUNDS_TOTAL))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstWins", is(ROUNDS_TOTAL.getFirstWins())))
				.andExpect(jsonPath("$.secondWins", is(ROUNDS_TOTAL.getSecondWins())))
				.andExpect(jsonPath("$.draws", is(ROUNDS_TOTAL.getDraws())))
				.andExpect(jsonPath("$.all", is(ROUNDS_TOTAL.getAll())));

		verify(roundService, times(1)).getRoundsTotal();
	}

}
