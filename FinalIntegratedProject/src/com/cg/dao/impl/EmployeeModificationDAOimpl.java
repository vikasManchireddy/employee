package com.cg.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.cg.dao.EmployeeModificationDAO;
import com.cg.model.EmployeeDTO;
import com.cg.util.ServiceLocator;
import com.cg.util.ServiceLocatorException;



@Repository("employeeModificationDAOimpl")
public class EmployeeModificationDAOimpl implements EmployeeModificationDAO {

	private DataSource dataSource;

	public EmployeeModificationDAOimpl() {

		try {
			dataSource = ServiceLocator.getDataSource("jdbc/VIMDataSource");

		} catch (ServiceLocatorException e) {

			System.out.println("Container Service not available");
		}

	}

	
	public List<EmployeeDTO> totalEmployeeList()
	{
		
		Connection connection = null;
		PreparedStatement selectStatement = null;
		String selectQuery = null;
		ResultSet result = null;
		
		List<EmployeeDTO> empDetails=new ArrayList<EmployeeDTO>();
		EmployeeDTO emp;
		selectQuery="select * from tempempdet where  emp_role='E' and emp_status!='DE'";
		try {
			connection=dataSource.getConnection();
		
		selectStatement = connection.prepareStatement(selectQuery);
	
		result = selectStatement.executeQuery();
		
		 for(;result.next();){
			 emp=new EmployeeDTO();
			 emp.setFirstName(result.getString("f_name"));
			 emp.setLastName(result.getString("l_name"));
			 emp.setKinId(result.getString("kin_id"));
			 emp.setEmployeeRole(result.getString("emp_role"));
			 emp.setEmployeeId(result.getInt("EMP_ID"));
			 emp.setStatus(result.getString("emp_status"));
			 emp.setDesignation(result.getString("designation"));
			 empDetails.add(emp);
		 }
		
		 return empDetails;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public void updateEmployee(int employeeId ,String status)
	{
		
		Connection connection = null;
		PreparedStatement selectStatement = null;
		String selectQuery = null;
		ResultSet result = null;
		
		selectQuery="update tempempdet SET emp_status=? where EMP_ID=?";
		try {
			
			connection=dataSource.getConnection();
			selectStatement = connection.prepareStatement(selectQuery);
			selectStatement.setString(1,status);
			selectStatement.setInt(2,employeeId);
			selectStatement.execute();
			System.out.println("db updated");
		}
	catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		finally {
			if (result != null)
				try {
					result.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (selectStatement != null)
				try {
					selectStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	
	
	
	

}
