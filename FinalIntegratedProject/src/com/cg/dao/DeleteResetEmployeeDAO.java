package com.cg.dao;

import java.util.List;

import com.cg.model.EmployeeDTO;

public interface DeleteResetEmployeeDAO {
	
	public List<EmployeeDTO> employeeList();
	public void deleteEmployee(int employeeId);
	public List<String> resetPassword(String userId,String oldpwd,String newpwd,String Confrmpwd);

}
