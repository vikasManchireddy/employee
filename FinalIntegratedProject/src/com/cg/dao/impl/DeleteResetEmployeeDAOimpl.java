package com.cg.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.cg.dao.DeleteResetEmployeeDAO;
import com.cg.model.DesignationDTO;
import com.cg.model.EmployeeDTO;
import com.cg.model.StateCityDTO;
import com.cg.util.ServiceLocator;
import com.cg.util.ServiceLocatorException;

/**
 * <h1>Reset password DAO implementation</h1>
 * <P>
 * This is the Employee dao implementation class
 * </P>
 * 
 * @author S Sneha & A Shilpa
 * @version 1.0
 * @since 2016-05-02
 */



@Repository("employeeDAOimpl")
public class DeleteResetEmployeeDAOimpl implements DeleteResetEmployeeDAO {

	private DataSource dataSource=null;
	private int result1;
	PreparedStatement selectStatement = null;
	String selectQuery = null;
	ResultSet result = null;

	Connection connection = null;
	
	public DeleteResetEmployeeDAOimpl() {

		try {
			dataSource = ServiceLocator.getDataSource("jdbc/VIMDataSource");

		} catch (ServiceLocatorException e) {

			System.out.println("Container Service not available");
		}

	}
	
	// reset Password
	@SuppressWarnings("resource")
	public List<String> resetPassword(String userId,String oldpwd,String newpwd,String Confrmpwd)
	{
		
		/**
		 * <p>resetPassword method takes the old password,new password and confirm password as the arguments
		 * checks whether the new password and confirm password is same and whether the old password entered
		 * matches with the database then establishes the connection to the MySql data base executes queries
		 * does the validation with if else conditions and returns the
		 * resetPasswordList then finally closes the result set,prepared
		 * statement and connection
		 * </p>
		 * 
		 */
		
		List<String> resetPasswordList = null;
		

		resetPasswordList=new ArrayList<String>();
		System.out.println("in dao once again "+userId+"--"+oldpwd+"--"+newpwd+ "--"+Confrmpwd);
		if(newpwd.equals(Confrmpwd))
		{
			selectQuery="select user_pwd from login_details where user_Id=? ";
			try{
				connection=dataSource.getConnection();
				selectStatement = connection.prepareStatement(selectQuery);
				
				selectStatement.setString(1,userId);
				//selectStatement.setString(2, password);
				
				result = selectStatement.executeQuery();
			if(result.next())
			{
				System.out.println("user password when user id is given "+result.getString("user_pwd"));
				if(oldpwd.equals(result.getString("user_pwd"))){
					System.out.println("Old password and data base password is same");
					selectQuery="update login_details set user_pwd=? where user_Id=? ";
					selectStatement = connection.prepareStatement(selectQuery);
					selectStatement.setString(1,newpwd);
					selectStatement.setString(2,userId);
					result1 = selectStatement.executeUpdate();
					resetPasswordList.add(0,"your password is succesfully reset");
					System.out.println("in dao result"+resetPasswordList);
					return resetPasswordList;
				}
				else
				{
					resetPasswordList.add(0,"new userid and password doesn't match");
					return resetPasswordList ;
				}
				}
			else
			{
				resetPasswordList.add(0,"failed");
				return resetPasswordList ;
			}
		}
			catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					if (connection != null)
						try {
							connection.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
		}
		else
		{
			resetPasswordList.add(0,"new password doesn't match");
			return resetPasswordList ;
		}
		return resetPasswordList;
	}

	

	public List<String> loginValidation(String userId, String password) {
		List<String> arrayList = new ArrayList<String>();
		selectQuery = "select Emp_Id from login_details where user_Id=? and user_pwd=?";

		try {
			connection = dataSource.getConnection();
			selectStatement = connection.prepareStatement(selectQuery);

			selectStatement.setString(1, userId);
			selectStatement.setString(2, password);

			result = selectStatement.executeQuery();

			if (result.next()) {

				System.out.println(result.getInt("Emp_Id"));

				selectQuery = "select emp_role,f_name from tempempdet where Emp_Id=?";

				selectStatement = connection.prepareStatement(selectQuery);
				selectStatement.setInt(1, result.getInt("Emp_Id"));
				result = selectStatement.executeQuery();

				if (result.next()) {

					System.out.println(result.getString("emp_role"));

					arrayList.add(0, result.getString("f_name"));
					arrayList.add(1, result.getString("emp_role"));
				}

				else {
					System.out.println("Authorisation failed");
					arrayList.add(0, "fail");
					return arrayList;
				}

			}

			else {
				System.out.println("validation failed");
				arrayList.add(0, "fail");
				return arrayList;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return arrayList;

	}

	public List<EmployeeDTO> employeeList()
	{
		List<EmployeeDTO> empDetails=new ArrayList<EmployeeDTO>();
		EmployeeDTO emp;
		selectQuery="select * from tempempdet where emp_status='AC' and emp_role='E'";
		try {
			connection=dataSource.getConnection();
		
		selectStatement = connection.prepareStatement(selectQuery);
	
		
		result = selectStatement.executeQuery();
		
		 for(;result.next();){
			 emp=new EmployeeDTO();
			 emp.setEmployeeId(result.getInt("EMP_ID"));
			 emp.setFirstName(result.getString("f_name"));
			 emp.setLastName(result.getString("l_name"));
			 emp.setKinId(result.getString("kin_id"));
			 emp.setEducation(result.getString("emp_edu"));
			 emp.setStatus(result.getString("emp_status"));
			 empDetails.add(emp);
		 }
		
		 return empDetails;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	public int dashbord() {

		selectQuery = "SELECT COUNT(EMP_ID) FROM tempempdet where emp_status='IN' and emp_role='E'";
		try {
			connection = dataSource.getConnection();

			selectStatement = connection.prepareStatement(selectQuery);
			result = selectStatement.executeQuery();

			if (result.next()) {

				System.out.println(result.getInt(1));
				return result.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}

	/*public List<StateCityDTO> loadState() {

		List<StateCityDTO> stateCityDetails = new ArrayList<StateCityDTO>();
		StateCityDTO stateCity;

		selectQuery = "SELECT * FROM state_city where city_parent_id=0";
		try {

			connection = dataSource.getConnection();
			selectStatement = connection.prepareStatement(selectQuery);
			result = selectStatement.executeQuery();

			while (result.next()) {
				stateCity = new StateCityDTO();
				stateCity.setCityParentId(result.getInt("city_parent_id"));
				stateCity.setStateCityId(result.getInt("state_city_Id"));
				stateCity.setStateCityName(result.getString("state_city_name"));
				stateCity.setStateCityStatus(result.getString("state_city_status"));
				stateCityDetails.add(stateCity);
			}
			return stateCityDetails;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}*/

	/*public List<DesignationDTO> loadDesignation() {

		List<DesignationDTO> desginationDetails = new ArrayList<DesignationDTO>();
		DesignationDTO desginationDTO;

		selectQuery = "SELECT * FROM  designation";
		try {

			connection = dataSource.getConnection();
			selectStatement = connection.prepareStatement(selectQuery);
			result = selectStatement.executeQuery();

			while (result.next()) {
				desginationDTO = new DesignationDTO();
				desginationDTO.setDesignationId(result.getInt("desig_Id"));
				desginationDTO.setDesignationName(result.getString("design_name"));
				desginationDTO.setDesignationStatus(result.getString("desig_status"));
				desginationDetails.add(desginationDTO);

			}
			return desginationDetails;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
*/
	/*public List<EmployeeDTO> totalEmployeeList() {
		List<EmployeeDTO> empDetails = new ArrayList<EmployeeDTO>();
		EmployeeDTO emp;
		selectQuery = "select * from tempempdet where  emp_role='E'";
		try {
			connection = dataSource.getConnection();

			selectStatement = connection.prepareStatement(selectQuery);

			result = selectStatement.executeQuery();

			for (; result.next();) {
				emp = new EmployeeDTO();
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
*/
	/*public List<StateCityDTO> loadCity() {

		List<StateCityDTO> stateCityDetails = new ArrayList<StateCityDTO>();
		StateCityDTO stateCity;

		selectQuery = "SELECT * FROM state_city where NOT city_parent_id = 0";
		try {

			connection = dataSource.getConnection();
			selectStatement = connection.prepareStatement(selectQuery);
			result = selectStatement.executeQuery();

			while (result.next()) {
				stateCity = new StateCityDTO();
				stateCity.setCityParentId(result.getInt("city_parent_id"));
				stateCity.setStateCityId(result.getInt("state_city_Id"));
				stateCity.setStateCityName(result.getString("state_city_name"));
				stateCity.setStateCityStatus(result.getString("state_city_status"));
				stateCityDetails.add(stateCity);
			}
			return stateCityDetails;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	*/
	
	/**
	 * <h1>Delete Employee DAO implementation</h1>
	 * <P>
	 * This is the Employee dao implementation class
	 * </P>
	 * 
	 * @author S Sneha & A Shilpa
	 * @version 1.0
	 * @since 2016-05-02
	 */
	

	//Delete Employee
	@SuppressWarnings("resource")
	public void deleteEmployee(int employeeId)
	{
		
		/**
		 * <p>deleteEmployee method takes employee id from welcome page controller and changes the AC to DE 
		 * and updates in the database.
		 * </p>
		 * 
		 */
		selectQuery="update tempempdet SET emp_status=? where EMP_ID=?";
		try {
			
			connection=dataSource.getConnection();
			selectStatement = connection.prepareStatement(selectQuery);
			selectStatement.setString(1,"DE");
			selectStatement.setInt(2,employeeId);
			selectStatement.execute();
			System.out.println(" delete employee db updated");
		}
	catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}

	
	

	}