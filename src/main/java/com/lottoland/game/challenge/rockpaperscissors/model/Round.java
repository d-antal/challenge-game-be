package com.lottoland.game.challenge.rockpaperscissors.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "round")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class Round {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "first_chose", nullable = false)
	private Choose firstPlayerChose;

	@Column(name = "second_chose", nullable = false)
	private Choose secondPlayerChose;

	@Column(name = "result", nullable = false)
	private Result result;

	@ManyToOne
	@JoinColumn(name = "game_id")
	@JsonIgnore
	private Game game;

}
