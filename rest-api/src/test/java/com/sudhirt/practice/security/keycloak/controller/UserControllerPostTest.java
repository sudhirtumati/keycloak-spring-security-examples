package com.sudhirt.practice.security.keycloak.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sudhirt.practice.security.keycloak.constant.Gender;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerPostTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private UserService userService;

	@Test
	@WithMockUser(authorities = {"admin"})
	public void should_throw_400_while_creating_user_without_first_name() throws Exception {
		User user = User.builder().middleName("Middle Name").lastName("Last Name")
				.dateOfBirth(LocalDate.of(1985, 6, 9)).gender(Gender.MALE).build();
		mockMvc.perform(post("/users/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(UserControllerTestHelper.asJsonString(user)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(authorities = {"admin"})
	public void should_throw_400_while_creating_user_without_last_name() throws Exception {
		User user = User.builder().firstName("First Name")
				.dateOfBirth(LocalDate.of(1985, 6, 9)).gender(Gender.MALE).build();
		mockMvc.perform(post("/users/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(UserControllerTestHelper.asJsonString(user)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(authorities = {"admin"})
	public void should_throw_400_while_creating_user_without_date_of_birth() throws Exception {
		User user = User.builder().firstName("First Name").lastName("Last Name")
				.gender(Gender.MALE).build();
		mockMvc.perform(post("/users/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(UserControllerTestHelper.asJsonString(user)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(authorities = {"admin"})
	public void should_throw_400_while_creating_user_without_gender() throws Exception {
		User user = User.builder().firstName("First Name").lastName("Last Name")
				.dateOfBirth(LocalDate.of(1985, 6, 9)).build();
		mockMvc.perform(post("/users/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(UserControllerTestHelper.asJsonString(user)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(authorities = {"admin"})
	public void should_create_user_with_admin_role() throws Exception {
		User user = User.builder().firstName("First Name").lastName("Last Name")
				.dateOfBirth(LocalDate.of(1985, 6, 9)).gender(Gender.MALE).build();
		MvcResult mvcResult = mockMvc.perform(post("/users/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(UserControllerTestHelper.asJsonString(user)))
				.andExpect(status().isCreated())
				.andReturn();
		String responseContent = mvcResult.getResponse().getContentAsString();
		User savedUser = UserControllerTestHelper.objectMapper.readValue(responseContent, User.class);
		// Retrieve user
		mockMvc.perform(get("/users/" + savedUser.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("First Name"))
				.andExpect(jsonPath("$.lastName").value("Last Name"))
				.andExpect(jsonPath("$.gender").value(Gender.MALE.toString()));
	}

	@Test
	@WithMockUser(authorities = {"admin"})
	public void should_update_user_with_admin_role() throws Exception {
		User user = User.builder().firstName("First Name").middleName("Middle Name").lastName("Last Name")
				.dateOfBirth(LocalDate.of(1985, 6, 9)).gender(Gender.MALE).build();
		MvcResult mvcResult = mockMvc.perform(post("/users/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(UserControllerTestHelper.asJsonString(user)))
				.andExpect(status().isCreated())
				.andReturn();
		String responseContent = mvcResult.getResponse().getContentAsString();
		User savedUser = UserControllerTestHelper.objectMapper.readValue(responseContent, User.class);
		// Update user
		User newUser = User.builder().firstName("First Name 1").lastName("Last Name1")
				.dateOfBirth(LocalDate.of(1981, 6, 9)).gender(Gender.FEMALE).build();
		mockMvc.perform(post("/users/" + savedUser.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(UserControllerTestHelper.asJsonString(newUser)))
				.andExpect(status().isNoContent());
		// Verify update is successful
		mockMvc.perform(get("/users/" + savedUser.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value(newUser.getFirstName()))
				.andExpect(jsonPath("$.middleName").doesNotExist())
				.andExpect(jsonPath("$.lastName").value(newUser.getLastName()))
				.andExpect(jsonPath("$.dateOfBirth").value("1981-06-09"))
				.andExpect(jsonPath("$.gender").value(Gender.FEMALE.toString()));
	}

	@Test
	public void update_should_throw_401_when_invoked_unauthenticated() throws Exception {
		User updatedUser = User.builder().firstName("First Name 1").build();
		mockMvc.perform(post("/users/123")
				.contentType(MediaType.APPLICATION_JSON)
				.content(UserControllerTestHelper.asJsonString(updatedUser)))
				.andExpect(status().isUnauthorized());
	}
}
