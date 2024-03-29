package com.sudhirt.practice.security.keycloak.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
public class MockSecurityConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(ResourceServerSecurityConfigurer resourceServerSecurityConfigurer) throws Exception {
		resourceServerSecurityConfigurer.stateless(false);
		super.configure(resourceServerSecurityConfigurer);
	}
}
