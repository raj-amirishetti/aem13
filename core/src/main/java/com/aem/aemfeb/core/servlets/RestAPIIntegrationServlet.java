package com.aem.aemfeb.core.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.aem.aemfeb.core.service.CustomHttpService;

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=WEATHER API INTEGRATION",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/restapitest",
		"sling.servlet.selectors=" + "weather", "sling.servlet.extensions=" + "json" })
public class RestAPIIntegrationServlet extends SlingSafeMethodsServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Reference
	private CustomHttpService customHttpService;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		Map<String, String> params = new HashMap<>();

		params.put("q", request.getParameter("location"));
		params.put("APPID", "239629dcd19a0a10978895b64ed1ff2c");

		response.getWriter().write(
				customHttpService.makeGetCall("https://api.openweathermap.org/data/2.5/weather", String.class, params));
		response.getWriter().close();
	}

}