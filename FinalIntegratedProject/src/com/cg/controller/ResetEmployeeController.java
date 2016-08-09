package com.cg.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.cg.dao.DeleteResetEmployeeDAO;

import com.cg.model.DesignationDTO;
import com.cg.model.EmployeeDTO;


/**
 * <h1>RESET PASSWORD CONTROLLER</h1>
 * <P>
 * This class is the controller for reset password. When request comes for
 * reset password it returns the password or error message depending on
 * different levels of validation
 * </P>
 * 
 * @author S Sneha & A Shilpa
 * @version 1.0
 * @since 2016-05-02
 */
@Controller

@SessionAttributes({"userId","UserName","totalEmp","EmpInactiveNumber","EmpList","stateList","designationList","stateList","cityList"})
public class ResetEmployeeController {
	
	@Autowired
   DeleteResetEmployeeDAO employeeDAOimpl;
    
   
    
    @RequestMapping("/change")
    public String change()
    {
    	System.out.println("hello");
    	return "resetPassword";
    }
    
    
    @RequestMapping(value="/reset",method={RequestMethod.POST,RequestMethod.GET})
@ResponseBody
    public String resetPage(@ModelAttribute("userId") String UserId, @RequestParam("OldPopup") String OldPopup,@RequestParam("NewPopup") String NewPopup ,@RequestParam("ConfrmNewPopup") String ConfrmNewPopup,ModelMap map )
    
    {
    	System.out.println("At forgot password controller");
    	System.out.println("userIdPopup and kinIdPopup are "+UserId+"--"+OldPopup+"--"+NewPopup+ "--"+ConfrmNewPopup);
    	ArrayList<String> password = (ArrayList<String>)employeeDAOimpl.resetPassword(UserId,OldPopup,NewPopup,ConfrmNewPopup);
    	System.out.println("Password from database is "+password);
   	
   	if(password.get(0) != null && password.size() > 0){
    		System.out.println("success"+password.get(0));
      		return password.get(0);
       	
  	}else{
    		
  		return password.get(0);
   	}
    	
    		
    }
   

   
   
    
    
}
