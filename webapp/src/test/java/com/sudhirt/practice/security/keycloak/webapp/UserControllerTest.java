package com.sudhirt.practice.security.keycloak.webapp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).alwaysDo(print()).apply(springSecurity()).build();
	}

	@Test
	@WithMockUser(authorities = {"user"})
	public void get_with_user_role_should_return_users() throws Exception {
		mockMvc.perform(get("/users").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void get_with_no_role_should_return_unauthorized_error() throws Exception {
		mockMvc.perform(get("/users").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser(authorities = {"admin"})
	public void get_with_admin_role_should_return_users() throws Exception {
		mockMvc.perform(get("/users").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(authorities = {"user"})
	public void delete_with_user_role_should_throw_403_error() throws Exception {
		mockMvc.perform(delete("/users/1").accept(MediaType.APPLICATION_JSON).with(csrf()))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(authorities = {"admin"})
	public void delete_with_admin_role_should_delete_user() throws Exception {
		mockMvc.perform(delete("/users/1").accept(MediaType.APPLICATION_JSON).with(csrf()))
				.andExpect(status().isNoContent());
	}

	@Test
	@WithMockUser(authorities = {"admin"})
	public void delete_with_admin_role_should_throw_404_error_when_user_not_found() throws Exception {
		mockMvc.perform(delete("/users/15").accept(MediaType.APPLICATION_JSON).with(csrf()))
				.andExpect(status().isNotFound());
	}

	@Test
	public void delete_with_no_role_should_throw_401_error() throws Exception {
		mockMvc.perform(get("/users/1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}
}
