package com.lottoland.game.challenge.rockpaperscissors.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RoundsTotal {

	private int firstWins;
	private int secondWins;
	private int draws;

}
