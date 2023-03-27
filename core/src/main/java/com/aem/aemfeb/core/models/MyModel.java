
package com.aem.aemfeb.core.models;

import javax.inject.Named;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy=DefaultInjectionStrategy.OPTIONAL)
public class MyModel {

	@ValueMapValue @Named("text") @Default(values ="Enter YOUR text") 
	private String title;
	
	@ValueMapValue  @Default(values ="Enter your Name") 
	private String name;


	public String getTitle() {
		return title;
	}
	
	public String getName() {
		return name;
	}
}
