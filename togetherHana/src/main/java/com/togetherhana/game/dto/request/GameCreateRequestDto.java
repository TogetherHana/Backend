package com.togetherhana.game.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import com.togetherhana.game.dto.GameOptionDto;

import lombok.Getter;

@Getter
public class GameCreateRequestDto {

	private String gameTitle;
	private LocalDateTime deadLine;
	private int fine;
	private List<GameOptionDto> gameOptions;

}
