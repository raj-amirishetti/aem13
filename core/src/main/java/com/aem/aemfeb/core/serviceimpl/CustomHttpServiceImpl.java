package com.aem.aemfeb.core.serviceimpl;

import java.util.Map;


import org.osgi.service.component.annotations.Component;

import com.aem.aemfeb.core.service.CustomHttpService;

@Component(immediate = true, service = CustomHttpService.class)
public class CustomHttpServiceImpl implements CustomHttpService {
	
	
	

	@Override
	public <T> T makeGetCall(String url, Class<T> t, Map<String, String> params) {
		
		String response = null;
		
		try {
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
		return null;
	}

}
