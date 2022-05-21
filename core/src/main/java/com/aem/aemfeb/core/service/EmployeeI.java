package com.aem.aemfeb.core.service;

import java.util.List;

import com.aem.aemfeb.core.bean.Employees;

public interface EmployeeI {
	
	
	public boolean registerEmployeeDetails(String name, String age, String email, String weight);
	public List<Employees>getAllEmployees();
	public Employees getEmployeeByName(String name);
	public int updateEmployee(Employees e);
	public boolean deleteEmployee(String name);
	
	
	

}
