package com.cg.dao;

import java.util.List;

import com.cg.model.DesignationDTO;
import com.cg.model.EmployeeDTO;
import com.cg.model.StateCityDTO;

/**
 * <h1>Create Employee DAO </h1>
 * <P>
 * This is the Employee DAO  class
 * </P>
 * 
 * @author NEHA ERSAVADLA
 * @version 1.0
 * @since 2016-05-02
 */


/*EmployeeDAO is an object/interface, which is used to access data from database of data storage*/
public interface CreateEmployeeDAO {
	public List<StateCityDTO> loadCity();
	public List<DesignationDTO> loadActiveDesignation();
	public List<StateCityDTO> loadActiveState();
	public void addEmployee(EmployeeDTO employeeDTO);
	public List<EmployeeDTO> employeeList();
	public List<StateCityDTO> findCity(String emp_state);

}
