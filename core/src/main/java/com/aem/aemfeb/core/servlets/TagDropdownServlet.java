package com.aem.aemfeb.core.servlets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javax.servlet.Servlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.day.cq.tagging.Tag;

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "= Tags value in dynamic Dropdown",
		"sling.servlet.paths=" + "/bin/colorTagLists", "sling.servlet.methods=" + HttpConstants.METHOD_GET })

public class TagDropdownServlet extends SlingSafeMethodsServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = LoggerFactory.getLogger(TagDropdownServlet.class);

	ResourceResolver resourceResolver;
	Resource pathResource;
	ValueMap valueMap;
	List<Resource> resourceList;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {

		resourceResolver = request.getResourceResolver();
		pathResource = request.getResource();
		resourceList = new ArrayList<>();

		/* Getting AEM Tags Path given on datasource Node */
		String tagsPath = Objects.requireNonNull(pathResource.getChild("datasource")).getValueMap().get("tagsPath", String.class);
		assert tagsPath != null;
		// Getting Tag Resource using Tag Path
		Resource tagsResource = request.getResourceResolver().getResource(tagsPath);
		assert tagsResource != null;
		// Iterating over child tag resource
		for (Resource childTags : tagsResource.getChildren()) {
			valueMap = new ValueMapDecorator(new HashMap<>());
			// Adopting Tag resource into Tag
			Tag colorTag = childTags.adaptTo(Tag.class);
			assert colorTag != null;
			String tagFullName = colorTag.getTagID();
			String tagName = tagFullName.substring(tagFullName.lastIndexOf("/") + 1);
			String tagTitle = colorTag.getTitle();
			valueMap.put("value", tagName);
			valueMap.put("text", tagTitle);
			resourceList
					.add(new ValueMapResource(resourceResolver, new ResourceMetadata(), "nt:unstructured", valueMap));
		}

		/* Create a DataSource that is used to populate the drop-down control */
		DataSource dataSource = new SimpleDataSource(resourceList.iterator());
		request.setAttribute(DataSource.class.getName(), dataSource);
		log.info("Tags successfully exported using DataSource!!!");
	}

}
