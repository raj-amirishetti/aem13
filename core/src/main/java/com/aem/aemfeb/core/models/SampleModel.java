package com.aem.aemfeb.core.models;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class, defaultInjectionStrategy=DefaultInjectionStrategy.OPTIONAL)
public class SampleModel {
	
	@Inject @Default(values ="Enter your FullName")
	private String name;
	
	
	@Inject @Default(values ="Enter your ID")
	private String empid;
	
	public String getName() {
		return name;
	}

	public String getEmpid() {
		return empid;
	}
}
