package com.sudhirt.practice.security.keycloak.dto;

import com.sudhirt.practice.security.keycloak.constant.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

	private String firstName;
	private String middleName;
	private String lastName;
	private Gender gender;
	private LocalDate dateOfBirth;
}
