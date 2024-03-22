package com.aem.aemfeb.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;

@Component(service = Servlet.class, property = { 
		"sling.servlet.methods =" + HttpConstants.METHOD_GET,
		"sling.servlet.paths =" + "/bin/testslingservlet"})
public class TestSlingServlet extends SlingSafeMethodsServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/plain");
		resp.getWriter().write("Hey wassup Rajashekar");
		
	}

}
