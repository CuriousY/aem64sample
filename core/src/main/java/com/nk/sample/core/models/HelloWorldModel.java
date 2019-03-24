/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.nk.sample.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.settings.SlingSettingsService;

import com.nk.sample.service.IGetUserService;

@Model(adaptables = {Resource.class},defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HelloWorldModel {

	protected static final String RESOURCE_TYPE = "AEM64Sample/components/content/helloworld";
	@Inject
	private SlingSettingsService settings;

	@Inject
	@Named("sling:resourceType")
	@Default(values = "No resourceType")
	protected String resourceType;

	@Inject
	@Named("text")
	private String text;

	@Inject
	private IGetUserService getUserService;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	private String message;
	
	private String userName;

	@PostConstruct
	protected void init() {
		message = "User ID " + text;
		message += " Hello user from service " + getUserName();
	}

	public String getUserName() {
		
		JSONObject userObj = getUserService.getUserByID(text);
		userName = userObj != null ? userObj.optString("name") : "";

		return userName;
	}

	public String getMessage() {
		return message;
	}
}
