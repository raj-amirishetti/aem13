  package com.aem.aemfeb.core.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.day.cq.commons.jcr.JcrConstants;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=Json Data in dynamic Dropdown",
		"sling.servlet.paths=" + "/bin/jsonDataDropdown", "sling.servlet.methods=" + HttpConstants.METHOD_GET })
public class JsonDataDropdownServlet extends SlingSafeMethodsServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonDataDropdownServlet.class);

	transient ResourceResolver resourceResolver;
	transient Resource pathResource;
	transient ValueMap valueMap;
	transient List<Resource> resourceList;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {

		resourceResolver = request.getResourceResolver();
		pathResource = request.getResource();
		resourceList = new ArrayList<>();

		try {
			/* Getting AEM Tags Path given on datasource Node */
			String jsonDataPath = Objects.requireNonNull(pathResource.getChild("datasource")).getValueMap()
					.get("jsonDataPath", String.class);
			assert jsonDataPath != null;
			// Getting Tag Resource using JsonData
			Resource jsonResource = request.getResourceResolver()
					.getResource(jsonDataPath + "/" + JcrConstants.JCR_CONTENT);
			assert jsonResource != null;
			// Getting Node from jsonResource
			Node jsonNode = jsonResource.adaptTo(Node.class);
			assert jsonNode != null;
			// Converting input stream to JSON Object
			InputStream inputStream = jsonNode.getProperty("jcr:data").getBinary().getStream();

			StringBuilder stringBuilder = new StringBuilder();
			String eachLine;
			assert inputStream != null;
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(inputStream, StandardCharsets.UTF_8));

			while ((eachLine = bufferedReader.readLine()) != null) {
				stringBuilder.append(eachLine);
			}

			JsonObject jsonObject = new Gson().fromJson(stringBuilder.toString(), JsonObject.class);

			Set<Entry<String, JsonElement>> entrySet = jsonObject.entrySet();

			for (Entry<String, JsonElement> entry : entrySet) {

				String jsonKey = entry.getKey();
				String jsonValue = entry.getValue().getAsString();

				/*
				 * JSONObject jsonObject = new JSONObject(stringBuilder.toString());
				 * 
				 * 
				 * Iterator<String> jsonKeys = jsonObject.keys();
				 * 
				 *  //Iterating JSON Objects over key 
				 *  while (jsonKeys.hasNext()) 
				 *  { 
				 *  String jsonKey = jsonKeys.next(); 
				 *  String jsonValue = jsonObject.getString(jsonKey);
				 
				 */

				valueMap = new ValueMapDecorator(new HashMap<>());
				valueMap.put("value", jsonKey);
				valueMap.put("text", jsonValue);
				resourceList.add(
						new ValueMapResource(resourceResolver, new ResourceMetadata(), "nt:unstructured", valueMap));

			}

			/* Create a DataSource that is used to populate the drop-down control */
			DataSource dataSource = new SimpleDataSource(resourceList.iterator());
			request.setAttribute(DataSource.class.getName(), dataSource);

		} catch (IOException | RepositoryException e) {
			LOGGER.error("Error in Json Data Exporting : {}", e.getMessage());
		}
	}

}
