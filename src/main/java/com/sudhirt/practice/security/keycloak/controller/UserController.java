package com.sudhirt.practice.security.keycloak.controller;

import com.sudhirt.practice.security.keycloak.constant.Gender;
import com.sudhirt.practice.security.keycloak.dto.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

	@GetMapping("/")
	@PreAuthorize("hasAnyAuthority('user')")
	public List<User> getUsers() {
		List<User> users = new ArrayList<>();
		User user1 = User.builder()
				.firstName("First User")
				.lastName("Last User")
				.gender(Gender.MALE)
				.dateOfBirth(LocalDate.of(1972, 1, 1))
				.build();
		User user2 = User.builder()
				.firstName("Second User")
				.lastName("Second User")
				.gender(Gender.FEMALE)
				.dateOfBirth(LocalDate.of(1982, 11, 7))
				.build();
		users.add(user1);
		users.add(user2);
		return users;
	}
}
