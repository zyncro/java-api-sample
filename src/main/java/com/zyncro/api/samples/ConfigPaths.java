package com.zyncro.api.samples;

public class ConfigPaths {

	/**
	 * The URL of Zyncro environment
	 */
	public static final String ZYNCRO_URL = "https://my.sandbox.zyncro.com";

	/**
	 * The Zyncro API development key and secret for that environment
	 */
	public static final String API_KEY = "ApiKey";
	public static final String API_SECRET = "ApiSecret";

	/**
	 * The URLs to Zyncro OAuth services
	 */
	public static final String REQUEST_TOKEN_URL = ZYNCRO_URL + "/tokenservice/oauth/v1/get_request_token";
	public static final String NO_BROWSER_AUTHORIZATION_URL = ZYNCRO_URL + "/tokenservice/oauth/v1/NoBrowserAuthorization";
	public static final String ACCESS_TOKEN_URL = ZYNCRO_URL + "/tokenservice/oauth/v1/get_access_token";

	/**
	 * The URLs of Zyncro API services
	 */
	public static final String MICROBLOGGING_URL = ZYNCRO_URL + "/api/v1/rest/wall";
	public static final String PERSONAL_FEED_URL = ZYNCRO_URL + "/api/v1/rest/wall/personalfeed";

}