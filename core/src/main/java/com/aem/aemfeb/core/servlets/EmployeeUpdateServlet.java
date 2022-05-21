
package com.aem.aemfeb.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aem.aemfeb.core.bean.Employees;
import com.aem.aemfeb.core.service.EmployeeI;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=Employee Update Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/updateservlet" })
public class EmployeeUpdateServlet extends SlingAllMethodsServlet {

	
	private static final long serialVersionUID = 1L;

	@Reference
	EmployeeI objEmp;
	
	/*@Reference
	Employees e;*/
	
	public static final Logger log = LoggerFactory.getLogger(EmployeeUpdateServlet.class);
	
	@Override
	protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		String name=req.getParameter("name");  
        String age=req.getParameter("age");  
        String email=req.getParameter("email");  
        String weight=req.getParameter("weight"); 
        
        log.info("name "+name );
          
        Employees e=new Employees();  
          
        e.setName(name);  
        e.setAge(age);
        e.setEmail(email);  
        e.setWeight(weight);  
        
        log.info("e is "+ e.toString());
          
        //Updating the employee data
        int status=objEmp.updateEmployee(e);  
        if(status>0){  
            resp.sendRedirect("/bin/viewservlet");  
        }else{  
            out.println("Sorry! unable to update record");  
        }  
          
        out.close();  
		
		

		// resp.getWriter().write("<h1>From employee viewservlet</h1>" );
		// resource.adaptTo(ValueMap.class).get("jcr:title"));
	}
}
