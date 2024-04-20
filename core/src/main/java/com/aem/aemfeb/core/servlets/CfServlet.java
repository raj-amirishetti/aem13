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
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.dam.cfm.ContentElement;
import com.adobe.cq.dam.cfm.ContentFragment;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Iterator;

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=CF Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/cfservlet" })

public class CfServlet extends SlingSafeMethodsServlet {

	private static final Logger log = LoggerFactory.getLogger(CfServlet.class);

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
			throws ServletException, IOException {
		ResourceResolver rr = req.getResourceResolver();

		ContentFragment cf = rr.getResource("/content/dam/aemfeb/content-fragments/en/movie-fragment")
				.adaptTo(ContentFragment.class);

		log.info("inside servlet");

		String contentFragmentName = cf.getName();
		
		log.info("Content Fragment name "+contentFragmentName);
		Iterator<ContentElement> contentElement = cf.getElements();

		
		while (contentElement.hasNext()) {

			ContentElement contentElementObj = contentElement.next();
			


			String tagElement = contentElementObj.getName().toString();
			

			log.info("Field Name  "+tagElement);

			String elementContent = contentElementObj.getContent();
			
			log.info("Value  "+elementContent);
			
			resp.getWriter().println(tagElement + "----"+elementContent);
		}

	}
}
