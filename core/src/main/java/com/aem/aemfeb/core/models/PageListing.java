package com.aem.aemfeb.core.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.day.cq.wcm.api.Page;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy=DefaultInjectionStrategy.OPTIONAL)
public class PageListing {

	@ValueMapValue 
	private String rootPath;
	
	@SlingObject
	private ResourceResolver resourceResolver;

	private List<ListPageDetail> datafromModelList = new ArrayList<ListPageDetail>();

	@PostConstruct
	public void init() {
		try {
			Resource resource = resourceResolver.getResource(rootPath);
			if (resource != null) {
				Page parentPage = resource.adaptTo(Page.class);
				if (parentPage != null) {
					Iterator<Page> listChildPages = parentPage.listChildren();
					while (listChildPages.hasNext()) {
						Page childPage = listChildPages.next();
						ListPageDetail detail = new ListPageDetail();
						detail.setTitle(childPage.getTitle());
						detail.setDescription(childPage.getDescription());
						datafromModelList.add(detail);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<ListPageDetail> getDatafromModelList() {
		return datafromModelList;
	}
}
