package com.aem.aemfeb.core.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.aem.aemfeb.core.bean.ListPageDetail;
import com.day.cq.wcm.api.Page;

@Model(adaptables= {Resource.class, SlingHttpServletRequest.class}, defaultInjectionStrategy=DefaultInjectionStrategy.OPTIONAL)
public class Practice {

	@Inject
	private String rootPath;
	
	@SlingObject
	private ResourceResolver rr;
	
	

    List<ListPageDetail> list = new ArrayList<>();
	
	@PostConstruct
	public void init() {
		
     Resource resource = rr.getResource(rootPath);
     
    Page parentPage= resource.adaptTo(Page.class);
      
    Iterator<Page> pageList=parentPage.listChildren();
    while (pageList.hasNext()) {
		Page childPage = pageList.next();
		
		ListPageDetail pageDetail = new ListPageDetail();
		
		pageDetail.setTitle(childPage.getTitle());
		pageDetail.setDescription(childPage.getDescription());
		
		list.add(pageDetail);
		
	}	
	}
	
}
