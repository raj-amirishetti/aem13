package com.aem.aemfeb.core.workflow;

import javax.jcr.ItemExistsException;
import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;

@Component(service = WorkflowProcess.class, property = { "process.label=" + "Adding property" })
public class CustomWorkflow implements WorkflowProcess {

	@Reference
	ResourceResolverFactory factory;

	@Override
	public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metadatamap)
			throws WorkflowException {
		// TODO Auto-generated method stub

		WorkflowData wdata = workItem.getWorkflowData();

		String payload = wdata.getPayload().toString() + "/jcr:content";

		ResourceResolver resolver = workflowSession.adaptTo(ResourceResolver.class);

		/*
		 * Map<String, Object> map = new HashMap<String, Object>();
		 * map.put(ResourceResolverFactory.SUBSERVICE, "testSystemUser");
		 */

		try {
			// ResourceResolver resolver = factory.getServiceResourceResolver(map);

			Resource resource = resolver.getResource(payload);

			Node node = resource.adaptTo(Node.class);

			node.addNode("addingNode").setPrimaryType("cq:PageContent");

			node.setProperty("city", "Hyderabad");

			resolver.adaptTo(Session.class).save();

		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchNodeTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConstraintViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LockException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ItemExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
