package com.cg.dao;

import java.util.ArrayList;
import java.util.List;

import com.cg.model.EmployeeDTO;

public interface EmployeeModificationDAO {

	public void updateEmployee(int employeeId, String employeeStatus);

	public List<EmployeeDTO> totalEmployeeList();
	
	

}
