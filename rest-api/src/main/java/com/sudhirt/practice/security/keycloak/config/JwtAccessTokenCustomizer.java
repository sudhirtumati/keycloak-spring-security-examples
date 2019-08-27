package com.sudhirt.practice.security.keycloak.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.oauth2.resource.JwtAccessTokenConverterConfigurer;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class JwtAccessTokenCustomizer extends DefaultAccessTokenConverter implements JwtAccessTokenConverterConfigurer {

	private static final String JWT_CLIENT_NAME_ELEMENT = "resource_access";
	private final String RESOURCE_ROLES_JSON_POINTER;

	private ObjectMapper objectMapper;
	private String resourceId;

	public JwtAccessTokenCustomizer(ObjectMapper objectMapper, String resourceId) {
		this.objectMapper = objectMapper;
		this.resourceId = resourceId;
		RESOURCE_ROLES_JSON_POINTER = "/" + JWT_CLIENT_NAME_ELEMENT + "/" + resourceId + "/roles";
	}

	@Override
	public void configure(JwtAccessTokenConverter jwtAccessTokenConverter) {
		jwtAccessTokenConverter.setAccessTokenConverter(this);
	}

	@Override
	public OAuth2Authentication extractAuthentication(Map<String, ?> tokenMap) {
		OAuth2Authentication oAuth2Authentication = super.extractAuthentication(tokenMap);
		OAuth2Request oAuth2Request = oAuth2Authentication.getOAuth2Request();
		OAuth2Request oAuth2RequestNew = new OAuth2Request(oAuth2Request.getRequestParameters(),
				oAuth2Request.getClientId(), extractRoles(tokenMap), true, oAuth2Request.getScope(),
				null, null, null, null);
		Authentication usernamePasswordAuthentication = new UsernamePasswordAuthenticationToken(oAuth2Authentication.getPrincipal(), "N/A", oAuth2RequestNew.getAuthorities());
		return new OAuth2Authentication(oAuth2RequestNew, usernamePasswordAuthentication);
	}

	private List<GrantedAuthority> extractRoles(Map<String, ?> tokenMap) {
		validateTokenMap(tokenMap);
		JsonNode node = objectMapper.convertValue(tokenMap, JsonNode.class);
		Set<String> roles = new HashSet<>();
		node.at(RESOURCE_ROLES_JSON_POINTER).elements().forEachRemaining(e -> roles.add(e.asText()));
		return AuthorityUtils.createAuthorityList(roles.toArray(new String[0]));
	}

	@SuppressWarnings("unchecked")
	private void validateTokenMap(Map<String, ?> tokenMap) {
		Assert.notNull(tokenMap.get(JWT_CLIENT_NAME_ELEMENT), JWT_CLIENT_NAME_ELEMENT + " element not found in JWT token");
		Map<String, ?> resourceMap = (Map<String, ?>) tokenMap.get(JWT_CLIENT_NAME_ELEMENT);
		Assert.notNull(resourceMap.get(resourceId), resourceId + " element not found in JWT token");
	}
}
