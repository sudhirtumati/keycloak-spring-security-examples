package com.sudhirt.practice.security.keycloak.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sudhirt.practice.security.keycloak.entity.User;
import com.sudhirt.practice.security.keycloak.service.UserService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerGetTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private UserService userService;

	@Test
	@WithMockUser(authorities = {"user"})
	public void should_return_users_with_user_role() throws Exception {
		UserControllerTestHelper.createUsers(userService, 3);
		mockMvc.perform(get("/users/").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(3)));
	}

	@Test
	@WithMockUser(authorities = {"admin"})
	public void should_return_users_with_admin_role() throws Exception {
		UserControllerTestHelper.createUsers(userService, 3);
		mockMvc.perform(get("/users/").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(3)));
	}

	@Test
	@WithMockUser(authorities = {"user"})
	public void should_get_one_user_with_user_role() throws Exception {
		Map<Long, User> users = UserControllerTestHelper.createUsers(userService, 1);
		User user = users.values().iterator().next();
		mockMvc.perform(get("/users/" + users.keySet().iterator().next()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value(user.getFirstName()))
				.andExpect(jsonPath("$.middleName").value(user.getMiddleName()))
				.andExpect(jsonPath("$.lastName").value(user.getLastName()));
	}

	@Test
	@WithMockUser(authorities = {"admin"})
	public void should_get_one_user_with_admin_role() throws Exception {
		Map<Long, User> users = UserControllerTestHelper.createUsers(userService, 1);
		User user = users.values().iterator().next();
		mockMvc.perform(get("/users/" + users.keySet().iterator().next()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value(user.getFirstName()))
				.andExpect(jsonPath("$.middleName").value(user.getMiddleName()))
				.andExpect(jsonPath("$.lastName").value(user.getLastName()));
	}

	@Test
	@WithMockUser(authorities = {"abc"})
	public void should_throw_403_while_retrieving_list_of_users_without_proper_authority() throws Exception {
		mockMvc.perform(get("/users/").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(authorities = {"abc"})
	public void should_throw_403_while_retrieving_user_without_proper_authority() throws Exception {
		mockMvc.perform(get("/users/10").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());
	}

	@Test
	public void should_throw_403_while_retrieving_list_of_users_without_authentication() throws Exception {
		mockMvc.perform(get("/users/").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void should_throw_403_while_retrieving_user_without_authentication() throws Exception {
		mockMvc.perform(get("/users/").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}
}
