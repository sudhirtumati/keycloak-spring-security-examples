package com.sudhirt.practice.security.keycloak.controller;

import com.sudhirt.practice.security.keycloak.entity.User;
import com.sudhirt.practice.security.keycloak.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

	private UserService userService;

	UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/")
	@PreAuthorize("hasAnyAuthority('user', 'admin')")
	public List<User> getUsers() {
		return userService.getAll();
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('user', 'admin')")
	public User getUserById(@PathVariable Long id) {
		return userService.getUser(id);
	}

	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAnyAuthority('admin')")
	public User addUser(@RequestBody @Valid User user) {
		Long userId = userService.addUser(user);
		return User.builder().id(userId).build();
	}

	@PostMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAnyAuthority('admin')")
	public void updateUser(@PathVariable Long id, @RequestBody @Valid User user) {
		user.setId(id);
		userService.updateUser(user);
	}

	@PatchMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAnyAuthority('admin')")
	public void patchUser(@PathVariable Long id, @RequestBody User user) {
		user.setId(id);
		userService.patchUser(user);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAnyAuthority('admin')")
	public void deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
	}
}
