
package com.aem.aemfeb.core.models;

import javax.inject.Inject;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class)
public class PracticeModel {

	@Inject @Default(values ="Enter your First Name")
	private String firstName;
	
	@Inject @Default(values ="Enter your Last Name")
	private String lastName;
	
	@Inject @Default(values ="Enter your Email")
	private String email;

	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getEmail() {
		return email;
	}
}
