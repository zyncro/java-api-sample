package com.zyncro.api.samples.oauth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import com.zyncro.api.samples.ConfigPaths;

public class ZyncroOAuthService {

	/**
	 * Get a Request token from Zyncro's OAuth services
	 * 
	 * @return the request token
	 */
	public static Token getRequestToken() {
		OAuthService service = new ServiceBuilder().provider(ZyncroApiOAuthConfig.class).apiKey(ConfigPaths.API_KEY)
				.apiSecret(ConfigPaths.API_SECRET).build();
		return service.getRequestToken();
	}

	/**
	 * Authorize a Request token with an Zyncro user
	 * 
	 * @param email the email of the Zyncro user
	 * @param password the password of the Zyncro user
	 * @param requestToken the Request token to authorize
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static boolean authorizeToken(String email, String password, Token requestToken) throws ClientProtocolException, IOException {

		boolean authorize = false;
		HttpClient httpclient = new DefaultHttpClient();
		try {
			HttpPost httppost = new HttpPost(ConfigPaths.NO_BROWSER_AUTHORIZATION_URL);

			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			parameters.add(new BasicNameValuePair("username", email));
			parameters.add(new BasicNameValuePair("password", password));
			parameters.add(new BasicNameValuePair("request_token", requestToken.getToken()));

			httppost.setEntity(new UrlEncodedFormEntity(parameters, HTTP.UTF_8));

			HttpResponse response = httpclient.execute(httppost);
			authorize = response.getStatusLine().getStatusCode() == 200;

		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return authorize;
	}

	/**
	 * Get an Access token from an authorized Request token
	 * 
	 * @param requestToken the authorized Request token
	 * @return the Access token
	 */
	public static Token getAccessToken(Token requestToken) {
		OAuthService service = new ServiceBuilder().provider(ZyncroApiOAuthConfig.class).apiKey(ConfigPaths.API_KEY)
				.apiSecret(ConfigPaths.API_SECRET).build();
		Verifier verifier = new Verifier("");

		Token token = new Token(requestToken.getToken(), requestToken.getSecret());

		return service.getAccessToken(token, verifier);
	}

	/**
	 * Get an Access token for a giver email and password
	 * 
	 * @param email the email of the Zyncro user
	 * @param password the password of the Zyncro user
	 * @return the Access token
	 */
	public static Token getAccessTokenForUser(String email, String password) {
		Token accessToken = null;

		// First we get the Request token
		Token requestToken = getRequestToken();
		boolean authorized = false;
		try {
			// Then, we try to authorized it
			authorized = authorizeToken(email, password, requestToken);
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}
		if (authorized) {
			// Last, we get the Access token
			accessToken = getAccessToken(requestToken);
		}

		return accessToken;
	}

}