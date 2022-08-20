package com.aem.aemfeb.core.servlets;



import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.model.WorkflowModel;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;


@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=Execute workflow Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/executeworkflow" })

public class ExecuteWorkflow extends SlingSafeMethodsServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(ExecuteWorkflow.class);
    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws ServletException, IOException {
        String status="Workflow Executing";

        final ResourceResolver resourceResolver = req.getResourceResolver();

        String payload=req.getRequestParameter("page").getString();
        try {
            
        		WorkflowSession workflowSession = resourceResolver.adaptTo(WorkflowSession.class);

                WorkflowModel workflowModel = workflowSession.getModel("/var/workflow/models/create-page-version");

                WorkflowData workflowData = workflowSession.newWorkflowData("JCR_PATH", payload);

                status=workflowSession.startWorkflow(workflowModel, workflowData).getState();
            }

         catch (Exception e) {
            LOG.info("\n ERROR IN WORKFLOW {} ", e.getMessage());
        }
        resp.setContentType("application/json");
        resp.getWriter().write(status);
    }

}