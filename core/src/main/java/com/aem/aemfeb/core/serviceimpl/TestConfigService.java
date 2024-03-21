package com.aem.aemfeb.core.serviceimpl;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;
import com.aem.aemfeb.core.customconfigs.TestConfig;

@Component(service=TestConfigService.class, immediate=true)
@Designate(ocd=TestConfig.class)
public class TestConfigService {
	
	private String serviceID;
	private String serviceName;
	private String serviceDescription;
	
	@Activate
	@Modified
	void activate(TestConfig testConfig) {
		
		serviceID=testConfig.getServiceID();
		serviceName=testConfig.getServiceName();
		serviceDescription=testConfig.getServiceDescription();
		
	}
	
	public String getServiceID() {
		return serviceID;
	}


	public String getServiceName() {
		return serviceName;
	}


	public String getServiceDescription() {
		return serviceDescription;
	}

	
	
	

}
