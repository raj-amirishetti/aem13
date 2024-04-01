package com.aem.aemfeb.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.model.WorkflowModel;

@Component(service = Servlet.class, property = {

		Constants.SERVICE_DESCRIPTION + "= Starting Workflow Using Code",
		"sling.servlet.paths =" + "/bin/startworkflow", "sling.servlet.methods =" + HttpConstants.METHOD_GET })
public class StartWorkflow extends SlingSafeMethodsServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory.getLogger(StartWorkflow.class);

	@Override
	protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws IOException {

		String status = "Workflow Executing";

		ResourceResolver resourceResolver = req.getResourceResolver();

		String payload = req.getRequestParameter("page").toString();

		WorkflowSession workflowSession = resourceResolver.adaptTo(WorkflowSession.class);

		try {
			WorkflowModel workflowModel = workflowSession.getModel("/var/workflow/models/create-page-version");

			WorkflowData workflowData = workflowSession.newWorkflowData("JCR_PATH", payload);
			

			status = workflowSession.startWorkflow(workflowModel, workflowData).getState();

		} catch (WorkflowException e) {

			LOG.info("\n ERROR IN WORKFLOW {} ", e.getMessage());
		}

		resp.setContentType("application/json");
		resp.getWriter().write(status);
	}

}
