package com.cg.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.cg.dao.LoginEmployeeDAO;
import com.cg.model.DesignationDTO;
import com.cg.model.EmployeeDTO;
import com.cg.model.StateCityDTO;
import com.cg.util.ServiceLocator;
import com.cg.util.ServiceLocatorException;


@Repository("loginemployeeDAOimpl")
public class LoginEmployeeDAOimpl implements LoginEmployeeDAO{

	private DataSource dataSource;

	public LoginEmployeeDAOimpl() {

		try {
			dataSource = ServiceLocator.getDataSource("jdbc/VIMDataSource");

		} catch (ServiceLocatorException e) {

			System.out.println("Container Service not available");
		}

	}

	
	
	public List<String> loginValidation(String userId, String password)
	{		
		
		Connection connection = null;
		PreparedStatement selectStatement = null;
		String selectQuery = null;
		ResultSet result = null;
		
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
					arrayList.add(1, result.getString("emp_role"))  ;
			 }
			 
			 else{
					System.out.println("Authorisation failed");
					arrayList.add(0, "You are not authorized to login.....! impl ");
					return arrayList;
				}
			 
			}
			
			else{
				System.out.println("validation failed  impl");
				arrayList.add(0,"Incorrect Username or Password....!  impl");
				return arrayList;
			}
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
		return arrayList;
		
	}
	
	
	
	
	
	
	
	public List<EmployeeDTO> totalEmployeeList() {

		Connection connection = null;
		PreparedStatement selectStatement = null;
		String selectQuery = null;
		ResultSet result = null;

		List<EmployeeDTO> empDetails = null;
		EmployeeDTO emp;
		selectQuery = "select * from tempempdet where  emp_role='E'";
		try {
			connection = dataSource.getConnection();

			selectStatement = connection.prepareStatement(selectQuery);

			result = selectStatement.executeQuery();
			empDetails = new ArrayList<EmployeeDTO>();

			for (; result.next();) {

				emp = new EmployeeDTO();
				emp.setFirstName(result.getString("f_name"));
				emp.setLastName(result.getString("l_name"));
				emp.setKinId(result.getString("kin_id"));
				emp.setEmployeeRole(result.getString("emp_role"));
				emp.setEmployeeId(result.getInt("EMP_ID"));
				emp.setStatus(result.getString("emp_status"));
				empDetails.add(emp);
			}

			return empDetails;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
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

		return null;
	}

	public List<EmployeeDTO> employeeList() {

		Connection connection = null;
		PreparedStatement selectStatement = null;
		String selectQuery = null;
		ResultSet result = null;

		List<EmployeeDTO> empDetails = new ArrayList<EmployeeDTO>();
		EmployeeDTO emp;
		selectQuery = "select* from tempempdet where emp_status='AC' and emp_role='E'";
		try {
			connection = dataSource.getConnection();

			selectStatement = connection.prepareStatement(selectQuery);

			result = selectStatement.executeQuery();

			for (; result.next();) {
				emp = new EmployeeDTO();
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
		} finally {
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

		return null;
	}

	public int dashbord() {

		Connection connection = null;
		PreparedStatement selectStatement = null;
		String selectQuery = null;
		ResultSet result = null;

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
		} finally {
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

		return 0;
	}

	
	// forgot password
		public List<String> forgotPassword(final String kinIdPopup, final String userIdPopup)

		{
			List<String> forgotPasswordList = null;
			PreparedStatement selectStatement = null;
			String selectQuery = null;
			ResultSet result = null;

			Connection connection = null;

			forgotPasswordList = new ArrayList<String>();
			selectQuery = "select Emp_Id,emp_role from tempempdet where kin_id=?";

			try {
				connection = dataSource.getConnection();

				selectStatement = connection.prepareStatement(selectQuery);

				selectStatement.setString(1, kinIdPopup);

				result = selectStatement.executeQuery();

				selectStatement = null;
				String admin = "A";
				String inputter = "I";

				if (result.next()) {

					System.out.println("emp id and employee role when kin id is given is " + result.getInt("Emp_Id") + "-"
							+ result.getString("emp_role"));
					if ("A".equals(result.getString("emp_role")) || "I".equals(result.getString("emp_role"))) {

						selectQuery = "select user_Id,user_pwd from login_details where Emp_Id=?";

						selectStatement = connection.prepareStatement(selectQuery);
						selectStatement.setInt(1, result.getInt("Emp_Id"));
						result = selectStatement.executeQuery();
						selectStatement = null;
						if (result.next()) {

							System.out.println("password for given emp id is " + result.getString("user_pwd"));
							System.out.println("user id for given emp id is " + result.getString("user_Id"));

							if (userIdPopup.equals(result.getString("user_Id"))) {

								forgotPasswordList.add(0, result.getString("user_pwd"));

							} else {
								forgotPasswordList.add(0,
										"can't display because given user ID didn't match with data base user id");
							}
						}

						else {
							System.out.println("Authorisation failed");
							forgotPasswordList.add(0,
									"Can't display since user Id or kin Id is incorrect...! Try with correct credentials");
							return forgotPasswordList;
						}
					} else {
						System.out.println("you are not admin or inputter");
						forgotPasswordList.add(0, "Can't display since you are not admin or inputter");
						return forgotPasswordList;

					}

				}

				else {
					System.out.println("validation failed");
					forgotPasswordList.add(0,
							"Can't display since user Id or kin Id is incorrect...! Try with correct credentials");
					return forgotPasswordList;
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
				if (selectStatement != null)
					try {
						selectStatement.close();

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				if (result != null)
					try {
						result.close();

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			return forgotPasswordList;

		}
	
	
	
	

	public List<StateCityDTO> loadState() {

		Connection connection = null;
		PreparedStatement selectStatement = null;
		String selectQuery = null;
		ResultSet result = null;

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
				stateCity.setStateCityStatus(result
						.getString("state_city_status"));
				stateCityDetails.add(stateCity);
			}
			return stateCityDetails;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
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
		return null;
	}

	public List<DesignationDTO> loadDesignation() {

		Connection connection = null;
		PreparedStatement selectStatement = null;
		String selectQuery = null;
		ResultSet result = null;

		List<DesignationDTO> desginationDetails = new ArrayList<DesignationDTO>();
		DesignationDTO desginationDTO;

		selectQuery = "SELECT * FROM  designation where desig_status != 'DE'";
		try {

			connection = dataSource.getConnection();
			selectStatement = connection.prepareStatement(selectQuery);
			result = selectStatement.executeQuery();

			while (result.next()) {
				desginationDTO = new DesignationDTO();
				desginationDTO.setDesignationId(result.getInt("desig_Id"));
				desginationDTO.setDesignationName(result
						.getString("design_name"));
				desginationDTO.setDesignationStatus(result
						.getString("desig_status"));
				desginationDetails.add(desginationDTO);

			}

			return desginationDetails;
		} catch (SQLException e) {
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
		return null;
	}

	public List<StateCityDTO> loadCity() {

		Connection connection = null;
		PreparedStatement selectStatement = null;
		String selectQuery = null;
		ResultSet result = null;

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
				stateCity.setStateCityStatus(result
						.getString("state_city_status"));
				stateCityDetails.add(stateCity);
			}
			return stateCityDetails;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
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
		return null;
	}

	public List<DesignationDTO> loadActiveDesignation() {

		Connection connection = null;
		PreparedStatement selectStatement = null;
		String selectQuery = null;
		ResultSet result = null;

		List<DesignationDTO> desginationDetails = new ArrayList<DesignationDTO>();
		DesignationDTO desginationDTO;

		selectQuery = "SELECT * FROM  designation where desig_status='AC'";
		try {

			connection = dataSource.getConnection();
			selectStatement = connection.prepareStatement(selectQuery);
			result = selectStatement.executeQuery();

			while (result.next()) {
				desginationDTO = new DesignationDTO();
				desginationDTO.setDesignationId(result.getInt("desig_Id"));
				desginationDTO.setDesignationName(result
						.getString("design_name"));
				desginationDTO.setDesignationStatus(result
						.getString("desig_status"));
				desginationDetails.add(desginationDTO);

			}
			return desginationDetails;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
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
		return null;
	}

	public List<StateCityDTO> loadActiveState() {

		Connection connection = null;
		PreparedStatement selectStatement = null;
		String selectQuery = null;
		ResultSet result = null;

		List<StateCityDTO> stateCityDetails = new ArrayList<StateCityDTO>();
		StateCityDTO stateCity;

		selectQuery = "SELECT * FROM state_city where city_parent_id=0 and state_city_status='IN'";
		try {

			connection = dataSource.getConnection();
			selectStatement = connection.prepareStatement(selectQuery);
			result = selectStatement.executeQuery();

			while (result.next()) {
				stateCity = new StateCityDTO();
				stateCity.setCityParentId(result.getInt("city_parent_id"));
				stateCity.setStateCityId(result.getInt("state_city_Id"));
				stateCity.setStateCityName(result.getString("state_city_name"));
				stateCity.setStateCityStatus(result
						.getString("state_city_status"));
				stateCityDetails.add(stateCity);
			}
			return stateCityDetails;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
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
		return null;

	}

	public void addEmployee(EmployeeDTO employeeDTO) {

		Connection connection = null;
		PreparedStatement selectStatement = null;
		String selectQuery = null;
		ResultSet result = null;

		selectQuery = "insert into tempempdet(f_name,l_name,kin_id,emp_edu,designation,emp_state,emp_city,emp_gender,emp_role,emp_status) "
				+ "values(?,?,?,?,?,?,?,?,?,?)";
		try {

			connection = dataSource.getConnection();
			selectStatement = connection.prepareStatement(selectQuery);

			selectStatement.setString(1, employeeDTO.getFirstName());
			selectStatement.setString(2, employeeDTO.getLastName());
			selectStatement.setString(3, employeeDTO.getKinId());
			selectStatement.setString(4, employeeDTO.getEducation());
			selectStatement.setString(5, employeeDTO.getEmployeeRole());
			selectStatement.setInt(6, employeeDTO.getState());
			selectStatement.setInt(7, 2);
			selectStatement.setString(8, employeeDTO.getGender());
			selectStatement.setString(9, "E");
			selectStatement.setString(10, "IN");

			boolean n = selectStatement.execute();

			System.out.println(n);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
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
		return;

	}

	//
	// public List<EmployeeDTO> totalInactiveEmployeeList()
	// {
	// List<EmployeeDTO> empDetails=new ArrayList<EmployeeDTO>();
	// EmployeeDTO emp;
	// selectQuery="select * from tempempdet where  emp_role='E' and emp_status='IN'";
	// try {
	// connection=dataSource.getConnection();
	//
	// selectStatement = connection.prepareStatement(selectQuery);
	//
	//
	// result = selectStatement.executeQuery();
	//
	// for(;result.next();){
	// emp=new EmployeeDTO();
	// emp.setFirstName(result.getString("f_name"));
	// emp.setLastName(result.getString("l_name"));
	// emp.setKinId(result.getString("kin_id"));
	// emp.setEmployeeRole(result.getString("emp_role"));
	// emp.setEmployeeId(result.getInt("EMP_ID"));
	// emp.setStatus(result.getString("emp_status"));
	// empDetails.add(emp);
	// }
	//
	// return empDetails;
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// if (result != null)
	// try {
	// result.close();
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// if (selectStatement != null)
	// try {
	// selectStatement.close();
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// if (connection != null)
	// try {
	// connection.close();
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return null;
	// }
	//

}