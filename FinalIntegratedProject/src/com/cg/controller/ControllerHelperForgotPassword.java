package com.cg.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



import com.cg.dao.ForgotEmployeeDAO;
import com.cg.dao.impl.ForgotEmployeeDAOimpl;

/**
 * <h1>FORGOT PASSWORD CONTROLLER</h1>
 * <P>
 * This class is the controller for forgot password. When request comes for
 * forgot password it returns the password or error message depending on
 * different levels of validation
 * </P>
 * 
 * @author M VINAY KUMAR
 * @version 1.0
 * @since 2016-05-02
 */
@Controller
public class ControllerHelperForgotPassword {
	
	@Autowired
	ForgotEmployeeDAO forgotEmployeeDAOimpl;

	@RequestMapping(value ="/forgot", method = RequestMethod.POST)
	@ResponseBody
	public String forgotPage(/*@RequestParam("userIdPopup") String userIdPopup,
			@RequestParam("kinIdPopup") String kinIdPopup*/) {
		/**
		 * gets user id and kin id from requested jsp page does null check if it
		 * pass then by using employeeDAOimpl class forgotPassword method it
		 * gets password list which contains password in the first index with
		 * that list again does the null check of the password and if satisfies
		 * returns the password
		 */
		
		System.out.println("hello in forgot con");

//		if (userIdPopup.equals("") || kinIdPopup.equals("")) {
//			return "can't display please enter left fields";
//
//		} else {
//
//			forgotEmployeeDAOimpl = new ForgotEmployeeDAOimpl();
//			System.out.println("At forgot password controller");
//			System.out.println("userIdPopup and kinIdPopup are " + userIdPopup + "--" + kinIdPopup);
//			ArrayList<String> password = (ArrayList<String>) forgotEmployeeDAOimpl.forgotPassword(kinIdPopup, userIdPopup);
//			System.out.println("Password from database is " + password);
//
//			if (password != null && password.size() > 0) {
//
//				System.out.println(password.get(0));
//				return password.get(0);
//
//			} else {
//
//				return "password is not present in database";
//			}

		//}
		
		return "hey working";
	}
}
