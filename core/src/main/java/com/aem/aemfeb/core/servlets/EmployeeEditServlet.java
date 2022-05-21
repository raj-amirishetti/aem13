
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

import com.aem.aemfeb.core.bean.Employees;
import com.aem.aemfeb.core.service.EmployeeI;
import com.aem.aemfeb.core.serviceimpl.EmployeeImpl;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=Employee Edit Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/editservlet" })
public class EmployeeEditServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;
	
	
	@Reference
	EmployeeI objEmp;

	public static final Logger log = LoggerFactory.getLogger(EmployeeEditServlet.class);

	@Override
	protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<h1>Update Employee</h1>");
		String sid = req.getParameter("id");
		
		log.info("sid is " + sid);
		
		// Getting the Employee Data into Table in editable way

		Employees e = objEmp.getEmployeeByName(sid);

		out.print("<form action='/bin/updateservlet' method='get'>");
		out.print("<table>");
		out.print(
				"<tr><td> Name:</td><td><input type='text' name='name' value='" + e.getName() + "'/></td></tr>");
		out.print("<tr><td>Age:</td><td><input type='text' name='age' value='" + e.getAge() + "'/></td></tr>");
		out.print("<tr><td>Email:</td><td><input type='text' name='email' value='" + e.getEmail() + "'/></td></tr>");
		out.print("<tr><td>Weight:</td><td><input type='text' name='weight' value='" + e.getWeight() + "'/></td></tr>");
		out.print("</td></tr>");
		out.print("<tr><td colspan='2'><input type='submit' value= 'Save '/></td></tr>");
		out.print("</table>");
		out.print("</form>");
		out.close();

	}
}
