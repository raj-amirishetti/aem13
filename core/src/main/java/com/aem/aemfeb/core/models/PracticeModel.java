
package com.aem.aemfeb.core.models;

import javax.inject.Inject;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class, defaultInjectionStrategy=DefaultInjectionStrategy.OPTIONAL)
public class PracticeModel {

	@Inject @Default(values ="Enter your First Name")
	private String fname;
	
	@Inject @Default(values ="Enter your Last Name")
	private String lname;
	
	@Inject @Default(values ="Enter your Email")
	private String email;

	
	
	public String getFirstName() {
		return fname;
	}
	
	public String getLastName() {
		return lname;
	}
	
	public String getEmail() {
		return email;
	}
}
