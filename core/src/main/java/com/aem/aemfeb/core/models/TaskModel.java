
package com.aem.aemfeb.core.models;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import com.aem.aemfeb.core.service.TaskService;


@Model(adaptables = Resource.class, defaultInjectionStrategy=DefaultInjectionStrategy.OPTIONAL)
public class TaskModel {
	


	@OSGiService
	private TaskService testService;

	private List<String> result;
	
	@PostConstruct
	protected void init() {
		
		result=testService.getData();
	}

	
	public List<String> getResult() {
		return result;
	}
}
