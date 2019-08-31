package com.sudhirt.practice.security.keycloak.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2114458713476835516L;

	public UserNotFoundException(Long id) {
		super("User with identifier '" + id + "' not found");
	}
}
