package com.sudhirt.practice.security.keycloak.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sudhirt.practice.security.keycloak.constant.Gender;
import com.sudhirt.practice.security.keycloak.entity.User;
import com.sudhirt.practice.security.keycloak.service.UserService;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class UserControllerTestHelper {

	public static ObjectMapper objectMapper;

	static {
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	}

	public static Map<Long, User> createUsers(UserService userService, int count) {
		Map<Long, User> users = new HashMap<>();
		int i = 0;
		Gender gender = Gender.MALE;
		while (i < count) {
			User user = User.builder().firstName("First Name" + i).middleName("Middle Name" + i).lastName("Last Name" + i)
					.dateOfBirth(LocalDate.of(1985, 6, 9)).gender(Gender.MALE).build();
			users.put(userService.addUser(user), user);
			gender = (gender == Gender.MALE) ? Gender.FEMALE : Gender.MALE;
			++i;
		}
		return users;
	}

	public static String asJsonString(final Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
