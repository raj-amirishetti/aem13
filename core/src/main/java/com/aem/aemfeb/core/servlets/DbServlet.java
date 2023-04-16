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
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.aem.aemfeb.core.utils.DatabaseConnectionHelper;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.Connection;


@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=DB Servlet",
												"sling.servlet.methods=" + HttpConstants.METHOD_GET, 
												"sling.servlet.paths=" + "/bin/dbservlet"})

public class DbServlet extends SlingSafeMethodsServlet {

	private static final long serialVersionUID = 1L;

	@Reference
	private DatabaseConnectionHelper helper;

	Connection con = null;

	@Override
	protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
			throws ServletException, IOException {
		//final Resource resource = req.getResource();
		resp.setContentType("text/plain");
		// resp.getWriter().write("Title = " +
		// resource.adaptTo(ValueMap.class).get("jcr:title"));
		con = helper.getDataBaseConnection("test");
		resp.getWriter().write("Connection Established " + con);

	}
}
