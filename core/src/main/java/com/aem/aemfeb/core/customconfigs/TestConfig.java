package com.aem.aemfeb.core.customconfigs;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Practice Configuration", description = "Hey This is Practice Configuration")
public @interface TestConfig {

	@AttributeDefinition(name = "Service ID", description = "Enter the Service ID", type = AttributeType.STRING)
	public String getServiceID() default "100";

	@AttributeDefinition(name = "Service Name", description = "Enter the Service Name", type = AttributeType.STRING)
	public String getServiceName() default "Test";

	@AttributeDefinition(name = "Service Description", description = "Enter the Service Desc", type = AttributeType.STRING)
	public String getServiceDescription() default "Test Description";

}
