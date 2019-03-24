package com.nk.sample.core.models;


import static org.junit.Assert.*;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.hamcrest.core.IsEqual;
import static org.hamcrest.Matcher.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.*;
import org.mockito.Mock;
import org.mockito.internal.matchers.GreaterThan;

import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import com.day.cq.wcm.api.Page;
import com.nk.sample.service.IGetUserService;

import io.wcm.testing.mock.aem.junit.AemContext;


/**
 * Simple JUnit test verifying the HelloWorldModel
 */
@RunWith(MockitoJUnitRunner.class)
public class TestHelloWorldModel {

    
    private HelloWorldModel model;
    
    
    private Resource resource;
    
    @Mock
	private IGetUserService getUserService;
    
    private String userString = "{ \"id\": 1, \"name\": \"Leanne Graham\", \"username\": \"Bret\"}";
    

    @Rule
    public final AemContext context = new AemContext();
    
    
    @Before
    public void setup() throws Exception {
    	context.addModelsForClasses(HelloWorldModel.class);
    	context.load().json("/com/nk/sample/core/models/TestHelloWorldModel.json", "/content");
    	context.currentResource("/content/helloworld");
    	when(getUserService.getUserByID(anyString())).thenReturn(new JSONObject(userString));
    	context.registerService(IGetUserService.class, getUserService, 
				org.osgi.framework.Constants.SERVICE_RANKING, Integer.MAX_VALUE);
    	
    	Resource resource = context.resourceResolver().getResource("/content/helloworld");    	
    	model = resource.adaptTo(HelloWorldModel.class);
    	
    	
    }
    
    @Test
    public void getTextShouldNotReturnNull() {
    	assertNotNull(model.getText());
    }
    
    @Test
    public void getUserNameShouldNotBeNull() {
    	assertNotNull(model.getUserName());
    }
    
    @Test
    public void getUserNameShouldReturnValue() {
    	System.out.println("string length " + model.getUserName().length());
    	assertEquals("Length should be greater than zero", model.getUserName().length() > 0, true);
    }

}
