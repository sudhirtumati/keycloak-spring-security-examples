package com.sudhirt.practice.security.keycloak.webapp.controller;

import com.sudhirt.practice.security.keycloak.webapp.dto.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/users")
public class UserController {

	Map<Long, User> users = new HashMap<>();

	@PostConstruct
	void initialize() {
		users.put(1L, User.builder().id(1L).firstName("First Name 1").lastName("Last Name 1").build());
		users.put(2L, User.builder().id(2L).firstName("First Name 2").lastName("Last Name 2").build());
	}

	@GetMapping
	@PreAuthorize("hasAnyAuthority('user', 'admin')")
	String getAll(Model model) {
		model.addAttribute("users", users.values());
		return "users";
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('admin')")
	ResponseEntity delete(@PathVariable Long id) {
		User user = users.remove(id);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
