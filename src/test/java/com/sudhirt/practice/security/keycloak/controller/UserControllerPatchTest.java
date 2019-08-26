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
public class UserControllerPatchTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private UserService userService;

	@Test
	@WithMockUser(authorities = {"admin"})
	public void should_patch_user_firstName_with_admin_role() throws Exception {
		User user = User.builder().firstName("First Name").lastName("Last Name")
				.dateOfBirth(LocalDate.of(1985, 6, 9)).gender(Gender.MALE).build();
		MvcResult mvcResult = mockMvc.perform(post("/users/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(UserControllerTestHelper.asJsonString(user)))
				.andExpect(status().isCreated())
				.andReturn();
		String responseContent = mvcResult.getResponse().getContentAsString();
		User savedUser = UserControllerTestHelper.objectMapper.readValue(responseContent, User.class);
		// Patch user
		User updatedUser = User.builder().firstName("First Name 1").build();
		mockMvc.perform(patch("/users/" + savedUser.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(UserControllerTestHelper.asJsonString(updatedUser)))
				.andExpect(status().isNoContent());
		// Retrieve user
		mockMvc.perform(get("/users/" + savedUser.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("First Name 1"))
				.andExpect(jsonPath("$.lastName").value("Last Name"))
				.andExpect(jsonPath("$.gender").value("MALE"));
	}

	@Test
	@WithMockUser(authorities = {"admin"})
	public void should_patch_user_lastName_with_admin_role() throws Exception {
		User user = User.builder().firstName("First Name").lastName("Last Name")
				.dateOfBirth(LocalDate.of(1985, 6, 9)).gender(Gender.MALE).build();
		MvcResult mvcResult = mockMvc.perform(post("/users/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(UserControllerTestHelper.asJsonString(user)))
				.andExpect(status().isCreated())
				.andReturn();
		String responseContent = mvcResult.getResponse().getContentAsString();
		User savedUser = UserControllerTestHelper.objectMapper.readValue(responseContent, User.class);
		// Patch user
		User updatedUser = User.builder().lastName("Last Name 1").build();
		mockMvc.perform(patch("/users/" + savedUser.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(UserControllerTestHelper.asJsonString(updatedUser)))
				.andExpect(status().isNoContent());
		// Retrieve user
		mockMvc.perform(get("/users/" + savedUser.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("First Name"))
				.andExpect(jsonPath("$.lastName").value("Last Name 1"))
				.andExpect(jsonPath("$.gender").value("MALE"));
	}

	@Test
	@WithMockUser(authorities = {"admin"})
	public void should_patch_user_gender_with_admin_role() throws Exception {
		User user = User.builder().firstName("First Name").lastName("Last Name")
				.dateOfBirth(LocalDate.of(1985, 6, 9)).gender(Gender.MALE).build();
		MvcResult mvcResult = mockMvc.perform(post("/users/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(UserControllerTestHelper.asJsonString(user)))
				.andExpect(status().isCreated())
				.andReturn();
		String responseContent = mvcResult.getResponse().getContentAsString();
		User savedUser = UserControllerTestHelper.objectMapper.readValue(responseContent, User.class);
		// Patch user
		User updatedUser = User.builder().gender(Gender.FEMALE).build();
		mockMvc.perform(patch("/users/" + savedUser.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(UserControllerTestHelper.asJsonString(updatedUser)))
				.andExpect(status().isNoContent());
		// Retrieve user
		mockMvc.perform(get("/users/" + savedUser.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("First Name"))
				.andExpect(jsonPath("$.lastName").value("Last Name"))
				.andExpect(jsonPath("$.gender").value("FEMALE"));
	}

	@Test
	@WithMockUser(authorities = {"user"})
	public void patch_should_throw_403_when_invoked_with_user_role() throws Exception {
		// Patch user
		User updatedUser = User.builder().firstName("First Name 1").build();
		mockMvc.perform(patch("/users/123")
				.contentType(MediaType.APPLICATION_JSON)
				.content(UserControllerTestHelper.asJsonString(updatedUser)))
				.andExpect(status().isForbidden());
	}

	@Test
	public void patch_should_throw_401_when_invoked_unauthenticated() throws Exception {
		User updatedUser = User.builder().firstName("First Name 1").build();
		mockMvc.perform(post("/users/123")
				.contentType(MediaType.APPLICATION_JSON)
				.content(UserControllerTestHelper.asJsonString(updatedUser)))
				.andExpect(status().isUnauthorized());
	}
}
