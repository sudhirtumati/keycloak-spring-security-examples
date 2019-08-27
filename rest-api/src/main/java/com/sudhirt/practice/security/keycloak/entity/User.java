package com.sudhirt.practice.security.keycloak.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sudhirt.practice.security.keycloak.constant.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	private Long id;
	@NotNull
	private String firstName;
	private String middleName;
	@NotNull
	private String lastName;
	@NotNull
	@JsonSerialize
	private LocalDate dateOfBirth;
	@NotNull
	private Gender gender;
	@CreatedBy
	private String createdBy;
	@CreatedDate
	private Instant createdDate;
	@LastModifiedBy
	private String lastModifiedBy;
	@LastModifiedDate
	private Instant lastModifiedDate;
}
