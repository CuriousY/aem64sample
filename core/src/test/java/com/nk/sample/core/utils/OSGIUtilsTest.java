package com.nk.sample.core.utils;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.hamcrest.core.IsEqual;
import static org.hamcrest.Matcher.*;
import org.junit.Rule;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.*;
import org.mockito.Mock;
import org.mockito.internal.matchers.GreaterThan;

import static org.mockito.Mockito.*;

import java.util.Collection;

import org.mockito.junit.MockitoJUnitRunner;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.nk.sample.service.IGetUserService;

import io.wcm.testing.mock.aem.junit.AemContext;

@RunWith(MockitoJUnitRunner.class)
public class OSGIUtilsTest {
	
	@Mock
	private IGetUserService getUserService;
	
	 @Rule
	 public final AemContext context = new AemContext();
	 
	BundleContext bundleContext;
	
	ServiceReference osgiRef;
	
	

	@Before
	public void setUp() throws Exception {
		context.registerService(IGetUserService.class, getUserService, 
				org.osgi.framework.Constants.SERVICE_RANKING, Integer.MAX_VALUE);
		osgiRef = context.bundleContext().getServiceReference(IGetUserService.class.getName());
		
	}

	@Test
	public <T> void testSingleServiceReferenceShouldNotBeNull() {	
		T serviceRef = (T) context.bundleContext().getService(osgiRef);	
		assertNotNull(serviceRef);
	}

}
