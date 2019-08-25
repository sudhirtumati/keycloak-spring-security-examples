package com.sudhirt.practice.security.keycloak.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	private static final String USER_TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJmWjJMbWdKZ3phM0FUbzFzaHZzeWpib2hJQm9mN294dTRRZkRSNEp6eXZZIn0.eyJqdGkiOiIzN2MyMTU2ZC0zMmM5LTRhNzAtOGE4Ni1lMzM5OWRlNjQzYTMiLCJleHAiOjE1NjY3MTUyNDYsIm5iZiI6MCwiaWF0IjoxNTY2NzE0OTQ2LCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXV0aC9yZWFsbXMvc2FtcGxlLWFwcC0xIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6Ijc2NDBmYjU3LTIyYmYtNDI1Ny05ZmI3LTZmZGY4YjAwZjZiOSIsInR5cCI6IkJlYXJlciIsImF6cCI6InNhbXBsZS1jbGllbnQtMSIsImF1dGhfdGltZSI6MCwic2Vzc2lvbl9zdGF0ZSI6ImMyNTUyODJlLTI1MDUtNDhhMy05MmFiLTIzYzI1MTNkNThlZCIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiIsInVzZXIiXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6InByb2ZpbGUgZW1haWwiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsIm5hbWUiOiJVc2VyMSB0ZXN0dXNlcjEiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJ0ZXN0dXNlcjEiLCJnaXZlbl9uYW1lIjoiVXNlcjEiLCJmYW1pbHlfbmFtZSI6InRlc3R1c2VyMSIsImVtYWlsIjoidGVzdHVzZXIxQHBlcnNvbmFsLmNvbSJ9.aYhIdVhGzs4b9umh2a723c83siKYrAIVghyty-v5RsOrP_Xt3Hce3aYPpV2j2V5CnmNSbGUZ8fpXT_CDEtJAnMG7AUIcREkrVALM1OCZHZx900mGd8CByh9Jr-so8kEHCwaTvP011OIpXzrV3UEBlO7jZqPfM3pYmA8tAmUXH8BIT_s2kvMb2bK8dKY4O0jNkWjkD7OnCZBF_tEa8Lc8K4gwrL2iUJirqFsIKeCnkferESwLmuRGm66ttXFK1LopN5wWwkso3flCS8883zVNfjd1z5V_j0cUiaI0N7q8A7WX1D9zvTmhZnaZ4YSUvyI5kH6yN398DjSLAXPjo6CXaw";

	@Autowired
	private MockMvc mockMvc;

	@Test
	@WithMockUser(authorities = {"user"})
	public void should_return_users_when_user_with_user_role_makes_the_call() throws Exception {
		mockMvc.perform(get("/users/").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)));
	}

	@Test
	@WithMockUser(authorities = {"abc"})
	public void should_throw_403_when_user_without_user_role_makes_the_call() throws Exception {
		mockMvc.perform(get("/users/").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());
	}

	@Test
	public void should_throw_401_when_unauthenticated_user_makes_the_call() throws Exception {
		mockMvc.perform(get("/users/").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}
}
