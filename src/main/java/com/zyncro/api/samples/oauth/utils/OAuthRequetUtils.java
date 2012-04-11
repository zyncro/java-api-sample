package com.zyncro.api.samples.oauth.utils;

import java.util.Collections;
import java.util.Map;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import com.zyncro.api.samples.ConfigPaths;
import com.zyncro.api.samples.oauth.ZyncroApiOAuthConfig;

public class OAuthRequetUtils {

	public static OAuthRequest getOAuthRequest(Verb verb, String url, Token accessToken) {
		Map<String, String> emptyMap = Collections.emptyMap();
		return getOAuthRequest(verb, url, emptyMap, accessToken);
	}

	public static OAuthRequest getOAuthRequest(Verb verb, String url, Map<String, String> parameters, Token accessToken) {
		OAuthService service = new ServiceBuilder().provider(ZyncroApiOAuthConfig.class).apiKey(ConfigPaths.API_KEY)
				.apiSecret(ConfigPaths.API_SECRET).build();
		OAuthRequest request = new OAuthRequest(verb, url);

		if (verb == Verb.POST) {
			for (String key : parameters.keySet()) {
				request.addBodyParameter(key, parameters.get(key));
			}
		}

		if (verb == Verb.GET || verb == Verb.DELETE) {
			for (String key : parameters.keySet()) {
				request.addQuerystringParameter(key, parameters.get(key));
			}
		}

		service.signRequest(accessToken, request);
		return request;
	}

}