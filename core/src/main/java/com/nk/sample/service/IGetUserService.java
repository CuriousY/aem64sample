package com.nk.sample.service;

import org.apache.sling.commons.json.JSONObject;


public interface IGetUserService {

	public String fetchUserFromService();
	
	public JSONObject getUserByID(String id);
}
