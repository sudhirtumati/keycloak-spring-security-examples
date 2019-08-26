package com.sudhirt.practice.security.keycloak.service;

import com.sudhirt.practice.security.keycloak.constant.Gender;
import com.sudhirt.practice.security.keycloak.entity.User;
import com.sudhirt.practice.security.keycloak.exception.UserNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserServiceTest {

	@Autowired
	private UserService userService;

	@Test
	public void addUserSuccessfully() {
		User user = User.builder().firstName("First Name").middleName("Middle Name").lastName("Last Name")
				.dateOfBirth(LocalDate.of(1985, 6, 9)).gender(Gender.MALE).build();
		Long userId = userService.addUser(user);
		assertThat(userId).isNotNull();
		User savedUser = userService.getUser(userId);
		assertThat(savedUser).isNotNull();
		assertThat(savedUser.getFirstName()).isEqualTo(user.getFirstName());
		assertThat(savedUser.getMiddleName()).isEqualTo(user.getMiddleName());
		assertThat(savedUser.getLastName()).isEqualTo(user.getLastName());
		assertThat(savedUser.getDateOfBirth()).isEqualTo(user.getDateOfBirth());
		assertThat(savedUser.getGender()).isEqualTo(user.getGender());
	}

	@Test
	public void addUserWithoutMiddleName() {
		User user = User.builder().firstName("First Name").lastName("Last Name")
				.dateOfBirth(LocalDate.of(1985, 6, 9)).gender(Gender.MALE).build();
		Long userId = userService.addUser(user);
		assertThat(userId).isNotNull();
		User savedUser = userService.getUser(userId);
		assertThat(savedUser).isNotNull();
		assertThat(savedUser.getFirstName()).isEqualTo(user.getFirstName());
		assertThat(savedUser.getMiddleName()).isEqualTo(user.getMiddleName());
		assertThat(savedUser.getLastName()).isEqualTo(user.getLastName());
		assertThat(savedUser.getDateOfBirth()).isEqualTo(user.getDateOfBirth());
		assertThat(savedUser.getGender()).isEqualTo(user.getGender());
	}

	@Test
	public void addUserWithoutFirstName() {
		User user = User.builder().lastName("Last Name")
				.dateOfBirth(LocalDate.of(1985, 6, 9)).gender(Gender.MALE).build();
		assertThatExceptionOfType(DbActionExecutionException.class).isThrownBy(() -> userService.addUser(user));
	}

	@Test
	public void addUserWithoutLastName() {
		User user = User.builder().firstName("First Name")
				.dateOfBirth(LocalDate.of(1985, 6, 9)).gender(Gender.MALE).build();
		assertThatExceptionOfType(DbActionExecutionException.class).isThrownBy(() -> userService.addUser(user));
	}

	@Test
	public void addUserWithoutDateOfBirth() {
		User user = User.builder().firstName("First Name").lastName("Last Name")
				.gender(Gender.MALE).build();
		assertThatExceptionOfType(DbActionExecutionException.class).isThrownBy(() -> userService.addUser(user));
	}

	@Test
	public void addUserWithoutGender() {
		User user = User.builder().firstName("First Name").lastName("Last Name")
				.dateOfBirth(LocalDate.of(1985, 6, 9)).build();
		assertThatExceptionOfType(DbActionExecutionException.class).isThrownBy(() -> userService.addUser(user));
	}

	@Test
	public void deleteUserSuccessfully() {
		User user = User.builder().firstName("First Name").middleName("Middle Name").lastName("Last Name")
				.dateOfBirth(LocalDate.of(1985, 6, 9)).gender(Gender.MALE).build();
		Long userId = userService.addUser(user);
		assertThat(userId).isNotNull();
		userService.deleteUser(userId);
		assertThatExceptionOfType(UserNotFoundException.class).isThrownBy(() -> userService.getUser(userId));
	}

	@Test
	public void deleteNonExistingUser() {
		assertThatExceptionOfType(UserNotFoundException.class).isThrownBy(() -> userService.getUser(123l));
	}

	@Test
	public void modifyUserFirstName() {
		User user = User.builder().firstName("First Name").middleName("Middle Name").lastName("Last Name")
				.dateOfBirth(LocalDate.of(1985, 6, 9)).gender(Gender.MALE).build();
		Long userId = userService.addUser(user);
		User newUser = User.builder().id(userId).firstName("First Name 1").build();
		userService.patchUser(newUser);
		User savedUser = userService.getUser(userId);
		assertThat(savedUser.getFirstName()).isEqualTo(newUser.getFirstName());
		assertThat(savedUser.getMiddleName()).isEqualTo(user.getMiddleName());
		assertThat(savedUser.getLastName()).isEqualTo(user.getLastName());
		assertThat(savedUser.getDateOfBirth()).isEqualTo(user.getDateOfBirth());
		assertThat(savedUser.getGender()).isEqualTo(user.getGender());
	}

	@Test
	public void modifyUser() {
		User user = User.builder().firstName("First Name").middleName("Middle Name").lastName("Last Name")
				.dateOfBirth(LocalDate.of(1985, 6, 9)).gender(Gender.MALE).build();
		Long userId = userService.addUser(user);
		User newUser = User.builder().id(userId).firstName("First Name 1").middleName("Middle Name1").lastName("Last Name1")
				.dateOfBirth(LocalDate.of(1984, 6, 9)).gender(Gender.FEMALE).build();
		userService.updateUser(newUser);
		User savedUser = userService.getUser(userId);
		assertThat(savedUser.getFirstName()).isEqualTo(newUser.getFirstName());
		assertThat(savedUser.getMiddleName()).isEqualTo(newUser.getMiddleName());
		assertThat(savedUser.getLastName()).isEqualTo(newUser.getLastName());
		assertThat(savedUser.getDateOfBirth()).isEqualTo(newUser.getDateOfBirth());
		assertThat(savedUser.getGender()).isEqualTo(newUser.getGender());
	}

	@Test
	public void findAllUsers() {
		userService.addUser(User.builder().firstName("First Name").middleName("Middle Name").lastName("Last Name")
				.dateOfBirth(LocalDate.of(1985, 6, 9)).gender(Gender.MALE).build());
		userService.addUser(User.builder().firstName("First Name1").middleName("Middle Name1").lastName("Last Name1")
				.dateOfBirth(LocalDate.of(1980, 9, 2)).gender(Gender.FEMALE).build());
		assertThat(userService.getAll()).hasSize(2);
	}

	@Test
	public void findAllWithoutAddingAnyUsers() {
		assertThat(userService.getAll()).hasSize(0);
	}
}
