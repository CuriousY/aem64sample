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
import org.mockito.Matchers;
import org.mockito.internal.matchers.GreaterThan;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.api.mockito.PowerMockito;
import static org.mockito.Mockito.*;

import java.util.Collection;

import org.mockito.junit.MockitoJUnitRunner;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import com.nk.sample.service.IGetUserService;
import com.nk.sample.service.impl.GetUserServiceImpl;

import io.wcm.testing.mock.aem.junit.AemContext;

@RunWith(PowerMockRunner.class)
public class OSGIUtilsTest {
	
	@Mock
	private IGetUserService getUserService;
	
	@Mock
	private GetUserServiceImpl userimpl;
	
	@Mock
	private FrameworkUtil frameWorkUtil;
	
	 @Rule
	 public final AemContext context = new AemContext();
	 
	BundleContext bundleContext;
	
	@Mock
	Bundle bundle;
	
	ServiceReference osgiRef;
	
	

	@Before
	public <T> void setUp() throws Exception {
		context.registerService(IGetUserService.class, getUserService, 
				org.osgi.framework.Constants.SERVICE_RANKING, Integer.MAX_VALUE);
//		PowerMockito.mockStatic(FrameworkUtil.class);
//		Class<T> userServiceGeneric1 = (Class<T>)new OSGIUtilsTest();
		osgiRef = context.bundleContext().getServiceReference(IGetUserService.class.getName());
		bundleContext = context.bundleContext();
		bundle = context.bundleContext().getBundle();	
//		doReturn(bundle).when(FrameworkUtil.getBundle(any(Class.class)));
		when(FrameworkUtil.getBundle(any(Class.class)))
        .thenReturn(bundle);
//		when(frameWorkUtil.getBundle(a)).thenReturn(bundle);
//		when(frameWorkUtil.getBundle(getUserService.getClass()).getBundleContext()).thenReturn(bundleContext);
		
		
	}

	@Test
	public <T> void testSingleServiceReferenceShouldNotBeNull() {
		System.out.println("Inside testsingleserviceregference");
		BundleContext bundleContext = frameWorkUtil.getBundle(getUserService.getClass()).getBundleContext();
		T serviceRef = (T) context.bundleContext().getService(osgiRef);	
		assertNotNull(serviceRef);
	}

}
