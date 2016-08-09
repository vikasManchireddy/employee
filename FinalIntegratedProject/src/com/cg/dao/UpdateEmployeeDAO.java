package com.cg.dao;

import java.util.List;

import com.cg.model.DesignationDTO;
import com.cg.model.EmployeeDTO;
import com.cg.model.StateCityDTO;


public interface UpdateEmployeeDAO {
	
		public EmployeeDTO findById(int id);
		public void update(EmployeeDTO employee);
		public List<EmployeeDTO> findAll(); 
		public List<StateCityDTO> findState(); 
		public List<DesignationDTO> findDesig(); 
		public List<StateCityDTO> findCity(String stateId);
		public List<StateCityDTO> findAllCity(String empState); 

		
}
