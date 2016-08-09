package com.cg.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;



import org.springframework.stereotype.Repository;

import com.cg.dao.ForgotEmployeeDAO;
import com.cg.util.ServiceLocator;
import com.cg.util.ServiceLocatorException;

/**
 * <h1>Forgot password DAO implementation</h1>
 * <P>
 * This is the Employee dao implementation class
 * </P>
 * 
 * @author M VINAY KUMAR
 * @version 1.0
 * @since 2016-05-02
 */


@Repository("forgotEmployeeDAOimpl")
public class ForgotEmployeeDAOimpl implements ForgotEmployeeDAO {

	private DataSource dataSource = null;

	public ForgotEmployeeDAOimpl() {

		try {
			dataSource = ServiceLocator.getDataSource("jdbc/VIMDataSource");

		} catch (ServiceLocatorException e) {

			System.out.println("Container Service not available");
		}

	}

	/// forgot password
	@SuppressWarnings("resource")
	public List<String> forgotPassword(final String kinIdPopup, final String userIdPopup)

	{
		/**
		 * <p>forgotPassword method takes the kin id and user id as the arguments
		 * establishes the connection to the MySql data base executes queries
		 * does the validation with if else conditions and returns the
		 * forgotPasswordList then finally closes the result set,prepared
		 * statement and connection
		 * </p>
		 * 
		 */
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

			e.printStackTrace();
		} finally {

			if (connection != null)
				try {
					connection.close();

				} catch (SQLException e) {

					e.printStackTrace();
				}
			if (selectStatement != null)
				try {
					selectStatement.close();

				} catch (SQLException e) {

					e.printStackTrace();
				}

			if (result != null)
				try {
					result.close();

				} catch (SQLException e) {

					e.printStackTrace();
				}
		}
		return forgotPasswordList;

	}

}