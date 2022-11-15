package com.aem.aemfeb.core.models;

import javax.inject.Named;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.day.cq.wcm.api.Page;

@Model(adaptables = SlingHttpServletRequest.class,
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Xyz {

	@ValueMapValue
	private String fname;

	@ValueMapValue
	private String lname;

	@ValueMapValue
	private boolean flag;

	@ScriptVariable
	@Named("currentPage")
	Page page;

	
	

	public String getDeepak() {
		return fname;
	}

	public String getLname() {
		return lname;
	}

	public boolean isFlag() {
		return flag;
	}

	public String getPagePath() {
		return page.getPath();
	}

}
