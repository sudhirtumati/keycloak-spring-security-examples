package com.sudhirt.practice.security.keycloak.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class SecurityContextUtils {

	private static final String ANONYMOUS = "anonymous";

	private SecurityContextUtils() {
	}

	public static String getUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = ANONYMOUS;
		if (null != authentication) {
			if (authentication.getPrincipal() instanceof UserDetails) {
				UserDetails userDetails = (UserDetails) authentication.getPrincipal();
				username = userDetails.getUsername();
			} else if (authentication.getPrincipal() instanceof String) {
				username = String.valueOf(authentication.getPrincipal());
			} else {
				log.debug("User details not found in security context");
			}
		} else {
			log.debug("Request is not authenticated");
		}
		return username;
	}

	public static Set<String> getUserRoles() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Set<String> roles = new HashSet<>();
		if (null != authentication) {
			authentication.getAuthorities().forEach(role -> roles.add(role.getAuthority()));
		}
		return roles;
	}
}
