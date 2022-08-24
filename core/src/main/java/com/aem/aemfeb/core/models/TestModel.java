
package com.aem.aemfeb.core.models;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = SlingHttpServletRequest.class,
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL )
public class TestModel {

	@ValueMapValue 
	private String fname;
	
	@ValueMapValue 
	private String lname;
	
	@ValueMapValue 
	private String email;
	


	   @Self
	   Node node;

	    public String getNodePath() throws RepositoryException
	   {

	       return node.getPath();

	   }

	

	
	public String getFname() {
		return fname;
	}
	
	public String getLname() {
		return lname;
	}
	
	public String getEmail() {
		return email;
	}
}
