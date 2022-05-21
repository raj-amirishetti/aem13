
package com.aem.aemfeb.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class)
public class MyModel {

	@Inject @Default(values ="Enter your text")
	private String text;

	@PostConstruct
	public String getText() {
		return text;
	}
}
