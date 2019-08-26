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
public class UserControllerDeleteTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private UserService userService;

	@Test
	public void should_throw_401_while_deleting_user_with_user_role() throws Exception {
		Map<Long, User> users = UserControllerTestHelper.createUsers(userService,1);
		mockMvc.perform(delete("/users/" + users.keySet().iterator().next()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser(authorities = {"user"})
	public void should_throw_403_while_deleting_user_with_user_role() throws Exception {
		Map<Long, User> users = UserControllerTestHelper.createUsers(userService,1);
		mockMvc.perform(delete("/users/" + users.keySet().iterator().next()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(authorities = {"admin"})
	public void should_delete_user_successfully_with_admin_role() throws Exception {
		Map<Long, User> users = UserControllerTestHelper.createUsers(userService,1);
		mockMvc.perform(delete("/users/" + users.keySet().iterator().next()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}
}
