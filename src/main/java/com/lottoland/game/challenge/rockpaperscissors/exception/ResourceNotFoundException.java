package com.lottoland.game.challenge.rockpaperscissors.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception {

	

	private static final long serialVersionUID = 7048688743550570038L;

	public ResourceNotFoundException(String message) {
		super(message);
	}

}
