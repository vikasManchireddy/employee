package com.cg.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cg.dao.UpdateEmployeeDAO;
import com.cg.model.DesignationDTO;
import com.cg.model.EmployeeDTO;
import com.cg.model.StateCityDTO;
import com.cg.util.JDBCDaoException;



@Repository("updateEmpDAO")
public class UpdateEmployeeDAOimpl implements UpdateEmployeeDAO {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private DataSource dataSource;

	
	@Autowired
	public void setDataSource(final DataSource dataSource) {
		this.dataSource = dataSource;
		// this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.setNamedParameterJdbcTemplate(new NamedParameterJdbcTemplate(
				dataSource));
	}

	// this method is used for closing connections,statement,preparedStatement and resultSet
	public void close(final Connection connection,
			final PreparedStatement statement, final ResultSet result, final Statement statement2) {
		Connection connection1 = connection;
		PreparedStatement statement1 = statement;
		ResultSet result1 = result;
		Statement statement3=statement2;
		if (connection1 != null) {
			try {
				connection1.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (statement1 != null) {
			try {
				statement1.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (result1 != null) {
			try {
				result1.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (statement3 != null) {
			try {
				statement3.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	//this method uses the Employee object as parameter to update the Emp_detail table
	@Override
	public void update(final EmployeeDTO employee) {
		// TODO Auto-generated method stub
		String updateQuery = "update Emp_Detail set f_name=?,l_name=?,kin_id=?,emp_edu=?,emp_state=?,emp_city=?,emp_gender=?,desig_Id=? where Emp_Id=?";
		Connection connection = null;
		PreparedStatement updateStatement = null;

		try {
			try {
				connection = dataSource.getConnection();
				connection.setAutoCommit(false);
				updateStatement = connection.prepareStatement(updateQuery);
				updateStatement.setString(1, employee.getFirstName());
				updateStatement.setString(2, employee.getLastName());
				updateStatement.setString(3, employee.getKinId());
				updateStatement.setString(4, employee.getEducation());
				updateStatement.setInt(5, employee.getState());
				updateStatement.setInt(6, employee.getCity());
				updateStatement.setString(7, employee.getGender());
				updateStatement.setString(8,employee.getDesignation());
				updateStatement.setInt(9, employee.getEmployeeId());
				updateStatement.executeUpdate();
				connection.commit();
			} catch (SQLException e) {
				if (connection != null)
					connection.rollback();

				throw e;
			} finally {
				close(connection, updateStatement, null, null);
			}
		} catch (SQLException e) {
			throw new JDBCDaoException("SQL error while excecuting query: "
					+ updateQuery, e);
		}

	}

	//this method takes id as a parameter to find the details of the selected employee
	@Override
	public EmployeeDTO findById(final int id) {
		Connection connection = null;
		//String StateCityDTO_name = null;
		//String designation_name = null;
		//PreparedStatement selectDesigName1 = null;
		//ResultSet result2 = null;
/*
		String selectDesigName = "select d. design_name from emp_detail e join designation d on e.desig_id=d.desig_Id where e.emp_id=?";
		try {
			try {
				connection = dataSource.getConnection();
				connection.setAutoCommit(true);
				selectDesigName1 = connection.prepareStatement(selectDesigName);
				selectDesigName1.setInt(1, id);
				result2 = selectDesigName1.executeQuery();
				if (result2.wasNull() == false) {
					result2.next();
					designation_name = result2.getString("design_name");
				}
			} finally {
				close(connection, selectDesigName1, result2,null);
			}
		} catch (SQLException e) {
			throw new JDBCDaoException("SQL error while excecuting query: "
					+ selectDesigName, e);

		}*/

	/*	String selectStateName = "select* from emp_detail e join StateCityDTO s on e.emp_state=s.StateCityDTO_id where e.emp_id=?";
		PreparedStatement selectStateNameStatement = null;
		ResultSet result1 = null;

		try {
			try {
				connection = dataSource.getConnection();
				connection.setAutoCommit(true);
				selectStateNameStatement = connection
						.prepareStatement(selectStateName);
				selectStateNameStatement.setInt(1, id);
				result1 = selectStateNameStatement.executeQuery();
				if (result1.wasNull() == false) {
					result1.next();
					StateCityDTO_name = result1.getString("StateCityDTO_name");
				}
			} finally {

				close(connection, selectStateNameStatement, result1,null);
			}
		} catch (SQLException e) {
			throw new JDBCDaoException("SQL error while excecuting query: "
					+ selectStateName, e);
		}*/

		String selectQuery = "select * from Emp_Detail where Emp_Id=?";
		EmployeeDTO employee = new EmployeeDTO();
		PreparedStatement selectStatement = null;
		ResultSet result = null;
		try {
			try {
				connection = dataSource.getConnection();
				connection.setAutoCommit(true);

				selectStatement = connection.prepareStatement(selectQuery);
				selectStatement.setInt(1, id);
				result = selectStatement.executeQuery();
				result.next();
				employee.setEmployeeId(result.getInt("Emp_Id"));
				employee.setFirstName(result.getString("f_name"));
				employee.setLastName(result.getString("l_name"));
				employee.setKinId(result.getString("kin_id"));
				employee.setEducation(result.getString("emp_edu"));
				//employee.setDesignationId(result.getInt("desig_Id"));
				employee.setState(result.getInt("emp_state"));
				employee.setCity(result.getInt("emp_city"));
				if (result.getString("emp_gender").equalsIgnoreCase("F")) {
					employee.setGender("F");
				} else {
					employee.setGender("M");
				}

			} finally {

				close(connection, selectStatement, result,null);
			}
		} catch (SQLException e) {

			throw new JDBCDaoException("SQL error while excecuting query: "
					+ selectQuery, e);
		}

		return employee;
	}

	
	//this method retrieve all the employee's detail from the database
	@Override
	public List<EmployeeDTO> findAll() {
		List<EmployeeDTO> employee1 =  new ArrayList<EmployeeDTO>();;
		ResultSet result = null;
		// PreparedStatement selectStatement=null;
		Connection connection = null;
		Statement selectStatement =null;
		String selectQuery = "select * from Emp_Detail";

		try {
			try {
				connection = dataSource.getConnection();
				connection.setAutoCommit(true);

				 selectStatement = connection.createStatement();

				result = selectStatement.executeQuery(selectQuery);

				if (result.wasNull() == false) {
					employee1 = new ArrayList<EmployeeDTO>();
				}
				while (result.next()) {
					EmployeeDTO employee = new EmployeeDTO();
					employee.setEmployeeId(result.getInt("Emp_Id"));
					employee.setFirstName(result.getString("f_name"));
					employee.setLastName(result.getString("l_name"));
					employee.setKinId(result.getString("kin_id"));
					employee.setEducation(result.getString("emp_edu"));
					employee.setState(result.getInt("emp_state"));
					employee.setCity(result.getInt("emp_city"));
					employee.setGender(result.getString("emp_gender"));
					employee.setEmployeeRole(result.getString("emp_role"));
					employee1.add(employee);
				}
			} finally {
				close(connection, null, result, selectStatement);
			}
		} catch (SQLException e) {
			throw new JDBCDaoException("SQL error while excecuting query: "
					+ selectQuery, e);
		}

		return employee1;
	}

	//this method retrieve all the State's detail from the database
	@Override
	public List<StateCityDTO> findState() {
		// TODO Auto-generated method stub
		List<StateCityDTO> state = null;
		ResultSet result = null;
		Statement selectStatement =null;

		String findState = "select * from StateCityDTO where StateCityDTO_status='AC' AND city_parent_id=0";
		Connection connection = null;

		try {
			try {
				connection = dataSource.getConnection();
				connection.setAutoCommit(true);
				 selectStatement = connection.createStatement();

				result = selectStatement.executeQuery(findState);
				if (result.wasNull() == false) {
					state = new ArrayList<StateCityDTO>();
				}
				while (result.next()) {
					StateCityDTO StateCityDTO = new StateCityDTO();
					StateCityDTO.setCityParentId(result
							.getInt("city_parent_id"));
					StateCityDTO.setStateCityId(result.getInt("StateCityDTO_Id"));
					StateCityDTO.setStateCityName(result
							.getString("StateCityDTO_name"));
					StateCityDTO.setStateCityStatus(result
							.getString("StateCityDTO_status"));
					state.add(StateCityDTO);
				}
			} finally {
				close(connection, null, result,selectStatement);
			}
		} catch (SQLException e) {
			throw new JDBCDaoException("SQL error while excecuting query: "
					+ findState, e);
		}

		return state;

	}

	//this method retrieve all the designation's detail from the database
	@Override
	public List<DesignationDTO> findDesig() {
		// TODO Auto-generated method stub
		List<DesignationDTO> designation = null;
		ResultSet result = null;
		Statement selectStatement=null;
		
		String findDesig = "select * from designation where desig_status='AC'";
		Connection connection = null;

		try {
			try {
				connection = dataSource.getConnection();
				connection.setAutoCommit(true);
				selectStatement = connection.createStatement();

				result = selectStatement.executeQuery(findDesig);

				if (result.wasNull() == false) {
					designation = new ArrayList<DesignationDTO>();
				}

				while (result.next()) {
					DesignationDTO desig = new DesignationDTO();
					desig.setDesignationId(result.getInt("desig_Id"));
					desig.setDesignationStatus(result.getString("desig_status"));
					desig.setDesignationName(result.getString("design_name"));
					designation.add(desig);
				}
			} finally {
				close(connection, null, result,selectStatement);
			}
		} catch (SQLException e) {
			throw new JDBCDaoException("SQL error while excecuting query: "
					+ findDesig, e);
		}

		return designation;

	}

	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return namedParameterJdbcTemplate;
	}

	public void setNamedParameterJdbcTemplate(
			NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public List<StateCityDTO> findCity(String stateId) {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement selectStatement = null;
		ResultSet result = null;
		List<StateCityDTO> city1 = null;
		
		String selectQuery = "select * from  StateCityDTO where city_parent_id=? AND StateCityDTO_status='AC'";
		StateCityDTO city;
		
		try {
			try {
				connection = dataSource.getConnection();
				connection.setAutoCommit(true);

				selectStatement = connection.prepareStatement(selectQuery);
				selectStatement.setInt(1, Integer.parseInt(stateId));
				result = selectStatement.executeQuery();
				if(result.wasNull()==false)
					city1=new ArrayList<StateCityDTO>();
				while(result.next()){
					city =new StateCityDTO();
				//city.setCity_parent_id(result.getInt("city_parent_id"));
				city.setStateCityId(result.getInt("StateCityDTO_id"));
				city.setStateCityName(result.getString("StateCityDTO_name"));
			//	city.setStateCityDTO_status("StateCityDTO_status");
				city1.add(city);
				}
				
			} finally {

				close(connection, selectStatement, result,null);
			}
		} catch (SQLException e) {

			throw new JDBCDaoException("SQL error while excecuting query: "
					+ selectQuery, e);
		}

		return city1;
	}

	@Override
	public List<StateCityDTO> findAllCity(String empState) {
		// TODO Auto-generated method stub
		return null;
	}

}
