package com.sudhirt.practice.security.keycloak.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(Long id) {
		super("User with identifier '" + id + "' not found");
	}
}
