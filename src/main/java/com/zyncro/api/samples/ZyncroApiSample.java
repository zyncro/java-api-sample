package com.zyncro.api.samples;

import java.util.HashMap;
import java.util.Map;

import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;

import com.zyncro.api.samples.oauth.ZyncroOAuthService;
import com.zyncro.api.samples.oauth.utils.OAuthRequetUtils;

public class ZyncroApiSample {

	public static void main(String[] args) {

		String email = "Email";
		String password = "Password";

		// Get an Access token for a user
		Token accessToken = ZyncroOAuthService.getAccessTokenForUser(email, password);

		// Get the main Microblogging for a user
		String mainFeed = getMainFeed(accessToken);
		System.out.println("Main feed: " + mainFeed);

		// Publish a new message in User's Personal feed
		String eventId = publishOnPersonalFeed(accessToken, "Hello world, Zyncro!");
		System.out.println("New Event published: " + eventId);
	}

	private static String getMainFeed(Token accessToken) {
		OAuthRequest request = OAuthRequetUtils.getOAuthRequest(Verb.GET, ConfigPaths.MICROBLOGGING_URL, accessToken);
		Response response = request.send();
		return response.getBody();
	}

	private static String publishOnPersonalFeed(Token accessToken, String comment) {
		Map<String, String> bodyParameters = new HashMap<String, String>();
		bodyParameters.put("comment", comment);

		OAuthRequest request = OAuthRequetUtils.getOAuthRequest(Verb.POST, ConfigPaths.PERSONAL_FEED_URL, bodyParameters, accessToken);
		Response response = request.send();
		return response.getBody();
	}

}