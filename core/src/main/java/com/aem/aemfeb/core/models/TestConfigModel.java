package com.aem.aemfeb.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

import com.aem.aemfeb.core.serviceimpl.TestConfigService;


@Model(adaptables= SlingHttpServletRequest.class)
public class TestConfigModel {

	@OSGiService
	TestConfigService testConfigService;

	public String getServiceID() {

		return testConfigService.getServiceID();
	}

	public String getServiceName() {

		return testConfigService.getServiceName();
	}

	public String getServiceDescription() {

		return testConfigService.getServiceDescription();
	}

}
