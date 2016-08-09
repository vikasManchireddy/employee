package com.cg.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.cg.dao.CreateEmployeeDAO;
import com.cg.model.DesignationDTO;
import com.cg.model.EmployeeDTO;
import com.cg.model.StateCityDTO;
import com.cg.util.ServiceLocator;
import com.cg.util.ServiceLocatorException;



/**
 * <h1>Create Employee DAO implementation</h1>
 * <P>
 * This is the Employee DAO implementation class
 * </P>
 * 
 * @author NEHA ERSAVADLA
 * @version 1.0
 * @since 2016-05-02
 */

@Repository("createEmployeeDAOimpl")
public class CreateEmployeeDAOimpl implements CreateEmployeeDAO{

private DataSource dataSource;
private Connection connection;
PreparedStatement selectStatement;
private String selectQuery;
private ResultSet result ;
	
	public CreateEmployeeDAOimpl()  {
		
		try {
			dataSource=ServiceLocator.getDataSource("jdbc/VIMDataSource");
			
		} catch (ServiceLocatorException e) {
			
			System.out.println("Container Service not available");
		}//catch block closed
		
	} //method closed

	public List<String> loginValidation(String userId, String password)
	{		
		List<String> arrayList=new ArrayList<String>();
		selectQuery="select Emp_Id from login_details where user_Id=? and user_pwd=?";
	
		
		try{
			connection=dataSource.getConnection();
			selectStatement = connection.prepareStatement(selectQuery);
			
			selectStatement.setString(1,userId);
			selectStatement.setString(2, password);
			
			result = selectStatement.executeQuery();
			
			
			if(result.next()){
			
			System.out.println(result.getInt("Emp_Id"));
			
			 selectQuery= "select emp_role,f_name from tempempdet where Emp_Id=?";
			 
			 selectStatement = connection.prepareStatement(selectQuery);
			 selectStatement.setInt(1,result.getInt("Emp_Id"));
			 result = selectStatement.executeQuery();
			 
			 if(result.next()){
					
					System.out.println(result.getString("emp_role"));
					
					arrayList.add(0, result.getString("f_name"));
					arrayList.add(1, result.getString("emp_role"));
			 }
			 
			 else{
					System.out.println("Authorisation failed");
					arrayList.add(0, "fail");
					return arrayList;
				}
			 
			}
			
			else{
				System.out.println("validation failed");
				arrayList.add(0, "fail");
				return arrayList;
			}
		} //try block closed
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//catch block closed
		finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
		}
		return arrayList;
		
	}//finally closed
	

	public List<EmployeeDTO> employeeList()
	{
		List<EmployeeDTO> empDetails=new ArrayList<EmployeeDTO>();
		EmployeeDTO emp;
		selectQuery="select f_name,l_name,kin_id,emp_edu from tempempdet where emp_status='AC' and emp_role='E'";
		try {
			connection=dataSource.getConnection();
		
		selectStatement = connection.prepareStatement(selectQuery);
	
		
		result = selectStatement.executeQuery();
		
		 for(;result.next();){
			 emp=new EmployeeDTO();
			 emp.setFirstName(result.getString("f_name"));
			 emp.setLastName(result.getString("l_name"));
			 emp.setKinId(result.getString("kin_id"));
			 emp.setEducation(result.getString("emp_edu"));
			 empDetails.add(emp);
		 }
		
		 return empDetails;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}//employeeList() closed
	
	
	public int dashbord()
	{
		
		selectQuery="SELECT COUNT(EMP_ID) FROM tempempdet where emp_status='IN' and emp_role='E'";
		try {
			connection=dataSource.getConnection();
		
		selectStatement = connection.prepareStatement(selectQuery);
		result = selectStatement.executeQuery();
		
		 if(result.next()){
			 
			 System.out.println(result.getInt(1));
			 return result.getInt(1);
		 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return 0;
	} //dashboard closed

	
	public List<StateCityDTO> loadState(){
		
		List<StateCityDTO> stateCityDetails=new ArrayList<StateCityDTO>();
		StateCityDTO stateCity;
		
		selectQuery="SELECT * FROM state_city where city_parent_id=0";
		try {
			
			connection=dataSource.getConnection();
			selectStatement = connection.prepareStatement(selectQuery);
			result = selectStatement.executeQuery();
		
			
		while(result.next()){
			 stateCity=new StateCityDTO();
			 stateCity.setCityParentId(result.getInt("city_parent_id"));
			 stateCity.setStateCityId(result.getInt("state_city_Id"));
			 stateCity.setStateCityName(result.getString("state_city_name"));
			 stateCity.setStateCityStatus(result.getString("state_city_status"));
			 stateCityDetails.add(stateCity);
		 }
		return stateCityDetails;
		}
		catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return null;
}//loadState() closed
	
	public List<DesignationDTO> loadDesignation(){
		
		
		List<DesignationDTO> desginationDetails=new ArrayList<DesignationDTO>();
		DesignationDTO desginationDTO;
		
		selectQuery="SELECT * FROM  designation";
		try {
			
			connection=dataSource.getConnection();
			selectStatement = connection.prepareStatement(selectQuery);
			result = selectStatement.executeQuery();
		
			
		while(result.next()){
			desginationDTO=new DesignationDTO();
		desginationDTO.setDesignationId(result.getInt("desig_Id"));
		desginationDTO.setDesignationName(result.getString("design_name"));
		desginationDTO.setDesignationStatus(result.getString("desig_status"));
		desginationDetails.add(desginationDTO);
			
	}
	return desginationDetails;
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}//loadDesignation() closed
	
	
	public List<EmployeeDTO> totalEmployeeList()
	{
		List<EmployeeDTO> empDetails=new ArrayList<EmployeeDTO>();
		EmployeeDTO emp;
		selectQuery="select * from tempempdet where  emp_role='E'";
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
			 empDetails.add(emp);
		 }
		
		 return empDetails;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	//totalEmployeelist closed
	
	public List<StateCityDTO> loadCity(){
		
		List<StateCityDTO> stateCityDetails=new ArrayList<StateCityDTO>();
		StateCityDTO stateCity;
		
		selectQuery="SELECT * FROM state_city where NOT city_parent_id = 0";
		try {
			
			connection=dataSource.getConnection();
			selectStatement = connection.prepareStatement(selectQuery);
			result = selectStatement.executeQuery();
		
			
		while(result.next()){
			 stateCity=new StateCityDTO();
			 stateCity.setCityParentId(result.getInt("city_parent_id"));
			 stateCity.setStateCityId(result.getInt("state_city_Id"));
			 stateCity.setStateCityName(result.getString("state_city_name"));
			 stateCity.setStateCityStatus(result.getString("state_city_status"));
			 stateCityDetails.add(stateCity);
		 }
		return stateCityDetails;
		}
		catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return null;
}//loadCity() closed
	
	
	
public List<DesignationDTO> loadActiveDesignation(){
		
		
		List<DesignationDTO> desginationDetails=new ArrayList<DesignationDTO>();
		DesignationDTO desginationDTO;
		
		selectQuery="SELECT * FROM  designation where desig_status='AC'";
		try {
			
			connection=dataSource.getConnection();
			selectStatement = connection.prepareStatement(selectQuery);
			result = selectStatement.executeQuery();
		
			
		while(result.next()){
			desginationDTO=new DesignationDTO();
		desginationDTO.setDesignationId(result.getInt("desig_Id"));
		desginationDTO.setDesignationName(result.getString("design_name"));
		desginationDTO.setDesignationStatus(result.getString("desig_status"));
		desginationDetails.add(desginationDTO);
			
	}
	return desginationDetails;
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
public List<StateCityDTO> loadActiveState(){
	
	List<StateCityDTO> stateCityDetails=new ArrayList<StateCityDTO>();
	StateCityDTO stateCity;
	
	selectQuery="SELECT * FROM state_city where city_parent_id=0 and state_city_status='AC'";
	try {
		
		connection=dataSource.getConnection();
		selectStatement = connection.prepareStatement(selectQuery);
		result = selectStatement.executeQuery();
	
		
	while(result.next()){
		 stateCity=new StateCityDTO();
		 stateCity.setCityParentId(result.getInt("city_parent_id"));
		 stateCity.setStateCityId(result.getInt("state_city_Id"));
		 stateCity.setStateCityName(result.getString("state_city_name"));
		 stateCity.setStateCityStatus(result.getString("state_city_status"));
		 stateCityDetails.add(stateCity);
	 }
	return stateCityDetails;
	}
	catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	}
		return null;
	
	}
	
	public void addEmployee(EmployeeDTO employeeDTO)
	{
		
		selectQuery="insert into tempempdet(f_name,l_name,kin_id,emp_edu,designation,emp_state,emp_city,emp_gender,emp_role,emp_status) "
				+ "values(?,?,?,?,?,?,?,?,?,?)";
		try {
			
			connection=dataSource.getConnection();
			selectStatement = connection.prepareStatement(selectQuery);
			

			selectStatement.setString(1,employeeDTO.getFirstName());
			selectStatement.setString(2, employeeDTO.getLastName());
			selectStatement.setString(3,employeeDTO.getKinId());
			selectStatement.setString(4, employeeDTO.getEducation());
			selectStatement.setString(5, employeeDTO.getEmployeeRole());
			selectStatement.setInt(6, employeeDTO.getState());
			selectStatement.setInt(7,2);
			selectStatement.setString(8, employeeDTO.getGender());
			selectStatement.setString(9,"E");
			selectStatement.setString(10, "IN");
			
			boolean n = selectStatement.execute();
			
			System.out.println(n);
		
		}
		catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
			return ;
		
		
	}

	@Override
	public List<StateCityDTO> findCity(String emp_state) {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement selectCityStatement = null;
		ResultSet result2 = null;

		String selectCity = "select * from  state_city where city_parent_id=?";
		try {
			try {
				connection = dataSource.getConnection();
				connection.setAutoCommit(true);
				selectCityStatement= connection.prepareStatement(selectCity);
				selectCityStatement.setInt(1,Integer.parseInt(emp_state));
				result2 = selectCityStatement.executeQuery();

				List<StateCityDTO> city=new ArrayList<StateCityDTO>();
				
					while(result2.next()){
						StateCityDTO cityName=new StateCityDTO();
						cityName.setCityParentId(result2.getInt("city_parent_id"));
						cityName.setStateCityName(result2.getString("state_city_name"));
						cityName.setStateCityId(result2.getInt("state_city_id"));
						cityName.setStateCityStatus(result2.getString("state_city_status"));
						city.add(cityName);
					}
					
					return city;
			} finally {
				if(connection==null)
				{
					connection.close();
				}
			}
		} catch (SQLException e) {
			System.out.println("SQL error while excecuting query: "
					+ e);

		}
		return null;
	}


}