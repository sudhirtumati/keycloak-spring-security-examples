package com.sudhirt.practice.security.keycloak.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

	private Long id;
	private String firstName;
	private String lastName;
}
