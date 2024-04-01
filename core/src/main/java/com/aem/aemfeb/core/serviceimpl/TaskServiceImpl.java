package com.aem.aemfeb.core.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aem.aemfeb.core.service.TaskService;

	@Component(immediate = true, service = TaskService.class)
	public class TaskServiceImpl implements TaskService {

	public static final Logger lOG = LoggerFactory.getLogger(TaskServiceImpl.class);

	
	
	@Override
	public List<String> getData() {

		List<String> al = new ArrayList<String>();

		al.add("Deepak Perumandla");
		al.add("AEM");
		al.add("SOFTWARE");
		al.add("DEVELOPER");

		return al;
		
		
		
		
		

	}

}
