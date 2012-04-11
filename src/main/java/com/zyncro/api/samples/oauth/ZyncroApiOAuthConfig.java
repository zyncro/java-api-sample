package com.zyncro.api.samples.oauth;

import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;

import com.zyncro.api.samples.ConfigPaths;

public class ZyncroApiOAuthConfig extends DefaultApi10a {

	@Override
	public String getAccessTokenEndpoint() {
		return ConfigPaths.ACCESS_TOKEN_URL;
	}

	@Override
	public String getRequestTokenEndpoint() {
		return ConfigPaths.REQUEST_TOKEN_URL;
	}

	@Override
	public String getAuthorizationUrl(Token arg0) {
		return null;
	}
}
