package com.nk.sample.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

import static org.apache.sling.api.servlets.HttpConstants.METHOD_POST;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Servlet;

@Component(service=Servlet.class,
property={
        Constants.SERVICE_DESCRIPTION + "=Simple Test Servlet",
        "sling.servlet.methods=" + HttpConstants.METHOD_POST,
        "sling.servlet.paths="+ "/bin/schwarzkopf/test",
        "sling.servlet.extensions=" + ""
})
public class SimpleTestServlet extends SlingAllMethodsServlet {

	private static final Logger LOG = LoggerFactory.getLogger(SimpleTestServlet.class);

	private static final String APP_ID = "schwarzkopf_de_de";

	private static final String NYRIS_API_ENDPOINT = "api_endpoint";

	@Override
	protected void doPost(SlingHttpServletRequest request,  SlingHttpServletResponse response)
			throws IOException {
			InputStream stream = null;
			String fname = request.getParameter("fname");
			String lname = request.getParameter("lname");
			try {
				final boolean isMultipart = true;
				if (isMultipart) {
					final java.util.Map<String, org.apache.sling.api.request.RequestParameter[]> params = request
							.getRequestParameterMap();
					for (final java.util.Map.Entry<String, org.apache.sling.api.request.RequestParameter[]> pairs : params
							.entrySet()) {
						final org.apache.sling.api.request.RequestParameter[] pArr = pairs.getValue();
						final org.apache.sling.api.request.RequestParameter param = pArr[0];
						stream = param.getInputStream();
					}
				}
				
				
				response.getWriter().write("post response " + fname + " " + lname);
			} catch (Exception e) {
				LOG.debug("Exception in NYRIS Api call {} ", e.getMessage());
				response.getWriter().write("Error in getting response");
			}
		
	}

	
}
