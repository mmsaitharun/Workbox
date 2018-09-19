package com.workbox.util.component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sap.core.connectivity.api.authentication.AuthenticationHeader;
import com.sap.core.connectivity.api.authentication.AuthenticationHeaderProvider;

@Component
public class RestUtil {

	private static final Logger logger = LoggerFactory.getLogger(RestUtil.class);

	/**
	 * @param requestURL
	 *            - URL that needs to be called
	 * @param entity
	 *            - Input body if the service is a POST service
	 * @param method
	 *            - Request method type - POST/GET
	 * @param contentType
	 *            - Response/Request content type - "application/json" or
	 *            "application/xml" etc.
	 * @param isSaml
	 *            - true if the service is SAML configured service, otherwise
	 *            false
	 * @return - JSONObject - response object in org.json.JSONObject
	 */
	public static Object callRestService(String requestURL, String entity, String method, String contentType,
			Boolean isSaml) {
		Context ctx = null;
		AuthenticationHeaderProvider authHeaderProvider = null;
		AuthenticationHeader appToAppSSOHeader = null;
		if (isSaml) {
			try {
				ctx = new InitialContext();
				authHeaderProvider = (AuthenticationHeaderProvider) ctx.lookup("java:comp/env/AuthHeaderProvider");
				appToAppSSOHeader = authHeaderProvider.getAppToAppSSOHeader(requestURL);
			} catch (Exception ex) {
				logger.error("Exception while fetching auth Header Provider : " + ex.getMessage());
			}
		}
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpRequestBase httpRequestBase = null;
		HttpResponse httpResponse = null;
		StringEntity input = null;
		String json = null;
		if (requestURL != null) {
			if (method.equalsIgnoreCase("GET")) {
				httpRequestBase = new HttpGet(requestURL);
			} else if (method.equalsIgnoreCase("POST")) {
				httpRequestBase = new HttpPost(requestURL);
				try {
					input = new StringEntity(entity);
					input.setContentType(contentType);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				((HttpPost) httpRequestBase).setEntity(input);
			}
			if (appToAppSSOHeader != null) {
				httpRequestBase.addHeader(appToAppSSOHeader.getName(), appToAppSSOHeader.getValue());
			}
			httpRequestBase.addHeader("accept", contentType);
			try {
				httpResponse = httpClient.execute(httpRequestBase);
				json = EntityUtils.toString(httpResponse.getEntity());
				try {
					if(json.charAt(0) == '[') {
						return new JSONArray(json);
					} else if(json.charAt(0) == '{') {
						return new JSONObject(json);
					}
				} catch (JSONException e) {
					logger.error("JSONException : " + e + "JSON Object : " + json);
				}
				httpClient.close();
			} catch (IOException e) {
				logger.error("IOException : " + e);
			}
		}
		return null;
	}
}
