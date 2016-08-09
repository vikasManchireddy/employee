package com.cg.dao;

import java.util.List;

/**
 * <h1>Forgot password DAO</h1>
 * <P>
 * This is the interface
 * </P>
 * 
 * @author M VINAY KUMAR
 * @version 1.0
 * @since 2016-05-02
 */
public interface ForgotEmployeeDAO {
	/**
	 * It consists forgotPassword method which takes the kin id and user id as
	 * the arguments
	 * 
	 */

	public List<String> forgotPassword(final String kinIdPopup, final String userIdPopup);
}
