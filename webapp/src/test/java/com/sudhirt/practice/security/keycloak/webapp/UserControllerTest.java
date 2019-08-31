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
	public void get_should_return_users_with_user_role() throws Exception {
		mockMvc.perform(get("/users").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void get_should_return_unauthorized_error_with_no_role() throws Exception {
		mockMvc.perform(get("/users").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser(authorities = {"admin"})
	public void get_should_return_users_with_admin_role() throws Exception {
		mockMvc.perform(get("/users").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(authorities = {"admin"})
	public void delete_should_delete_user_with_admin_role() throws Exception {
		mockMvc.perform(delete("/users/1").accept(MediaType.APPLICATION_JSON).with(csrf()))
				.andExpect(status().isNoContent());
	}

	@Test
	public void delete_should_return_unauthorized_user_with_no_role() throws Exception {
		mockMvc.perform(get("/users/1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}
}
