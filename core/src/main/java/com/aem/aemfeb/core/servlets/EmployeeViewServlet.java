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

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=Employee View Servlet",
													  "sling.servlet.methods=" + HttpConstants.METHOD_GET, 
													  "sling.servlet.paths=" + "/bin/viewservlet" })
public class EmployeeViewServlet extends SlingAllMethodsServlet {

	public static final Logger log = LoggerFactory.getLogger(EmployeeViewServlet.class);
	private static final long serialVersionUID = 1L;

	@Reference
	EmployeeI objEmp;

	@Override
	protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
			throws ServletException, IOException {
		
		log.info("inside viewservlet");
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		
		out.println("<h1>Employee List</h1>");
		// Getting the Employees Data into list 
		List<Employees> list =  objEmp.getAllEmployees();
		// Displaying the Employees data in Table
		out.print("<table border='1' width='100%'");
		out.print(
				"<tr><th> Name</th><th>Age</th><th>Email</th><th>Weight</th><th>Edit</th> <th>Delete</th> </tr>");
		for (Employees e : list) {
			out.print("<tr><td>" + e.getName() + "</td><td>" + e.getAge() + "</td><td>" + e.getEmail() + "</td><td>"
					+ e.getWeight() + "</td><td><a href='/bin/editservlet?id=" + e.getName()
					+ "'>edit</a></td>  <td><a href='/bin/deleteservlet?id=" + e.getName() + "'>delete</a></td></tr>");
		}
		out.print("</table>");

		out.close();

	

		//resp.setContentType("text/html");
		//resp.getWriter().write("<h1>From employee viewservlet</h1>" );
		// resource.adaptTo(ValueMap.class).get("jcr:title"));
	}
}
