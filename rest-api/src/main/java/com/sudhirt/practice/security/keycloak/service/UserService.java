package com.sudhirt.practice.security.keycloak.service;

import com.sudhirt.practice.security.keycloak.entity.User;
import com.sudhirt.practice.security.keycloak.exception.UserNotFoundException;
import com.sudhirt.practice.security.keycloak.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@Service
public class UserService {

	private UserRepository userRepository;

	UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public Long addUser(User user) {
		userRepository.save(user);
		return user.getId();
	}

	public void patchUser(User user) {
		User dbUser = userRepository.findById(user.getId()).orElseThrow(() -> new UserNotFoundException(user.getId()));
		setIfNotNull(dbUser::setFirstName, user.getFirstName());
		setIfNotNull(dbUser::setMiddleName, user.getMiddleName());
		setIfNotNull(dbUser::setLastName, user.getLastName());
		setIfNotNull(dbUser::setDateOfBirth, user.getDateOfBirth());
		setIfNotNull(dbUser::setGender, user.getGender());
		userRepository.save(dbUser);
	}

	public void updateUser(User user) {
		User dbUser = userRepository.findById(user.getId()).orElseThrow(() -> new UserNotFoundException(user.getId()));
		dbUser.setFirstName(user.getFirstName());
		dbUser.setMiddleName(user.getMiddleName());
		dbUser.setLastName(user.getLastName());
		dbUser.setDateOfBirth(user.getDateOfBirth());
		dbUser.setGender(user.getGender());
		userRepository.save(dbUser);
	}

	public void deleteUser(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
		userRepository.delete(user);
	}

	public User getUser(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
	}

	public List<User> getAll() {
		List<User> users = new ArrayList<>();
		userRepository.findAll().forEach(user -> users.add(user));
		return users;
	}

	private <T> void setIfNotNull(Consumer<T> setterMethod, T value) {
		if (value != null) {
			setterMethod.accept(value);
		}
	}
}
