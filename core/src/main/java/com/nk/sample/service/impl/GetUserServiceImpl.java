package com.nk.sample.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nk.sample.service.IGetUserService;

@Component
public class GetUserServiceImpl implements IGetUserService {
	private static Logger logger = LoggerFactory.getLogger(GetUserServiceImpl.class);

	@Override
	public String fetchUserFromService() {
		StringBuffer responseBuffer = new StringBuffer();

		try {
			URL serviceURL = new URL("https://jsonplaceholder.typicode.com/users");
			URLConnection con = serviceURL.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String response;

			while ((response = reader.readLine()) != null) {
				responseBuffer.append(response);
			}
		} catch (IOException e) {
			logger.debug("Error in fetchUserFromService ", e.getMessage());
		}
		return responseBuffer.toString().trim();
	}

	@Override
	public JSONObject getUserByID(String id) {
		String userJSONString = fetchUserFromService();
		JSONObject userJSONObject = null;
		try {
			JSONArray jsonArray = userJSONString != null ? new JSONArray(userJSONString) : null;
			if (jsonArray != null && jsonArray.length() > 0) {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject obj = jsonArray.getJSONObject(i);
					if (obj.optString("id").equalsIgnoreCase(id)) {
						userJSONObject = obj;
					}
				}
			}

		} catch (JSONException e) {
			logger.debug("Error in json response ", e.getMessage());
		}
		return userJSONObject;
	}

}
