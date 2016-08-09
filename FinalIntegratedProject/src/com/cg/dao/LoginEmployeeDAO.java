package com.cg.dao;

import java.util.ArrayList;
import java.util.List;

import com.cg.model.EmployeeDTO;

public interface LoginEmployeeDAO {

	public List<String> loginValidation(String userId, String password);

	public int dashbord();

	public List<EmployeeDTO> employeeList();

	public List<EmployeeDTO> totalEmployeeList();
		

}
