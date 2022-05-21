
package com.aem.aemfeb.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aem.aemfeb.core.service.EmployeeI;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=Employee delete Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/deleteservlet" })
public class EmployeeDeleteServlet extends SlingAllMethodsServlet {

	
	private static final long serialVersionUID = 1L;

	@Reference
	EmployeeI objEmp;
	
	public static final Logger log = LoggerFactory.getLogger(EmployeeDeleteServlet.class);
	
	@Override
	protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");
		log.info("inside doget delete servlet");

		String name = req.getParameter("id");
		log.info("input name is "+name);
		
		//Deleting the data from EmployeeList by Name 
		objEmp.deleteEmployee(name);
		resp.sendRedirect("/bin/viewservlet");

		// resp.getWriter().write("<h1>From employee viewservlet</h1>" );
		// resource.adaptTo(ValueMap.class).get("jcr:title"));
	}
}
