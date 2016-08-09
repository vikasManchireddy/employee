package com.cg.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.cg.dao.LoginEmployeeDAO;
import com.cg.dao.impl.LoginEmployeeDAOimpl;
import com.cg.model.DesignationDTO;
import com.cg.model.EmployeeDTO;
import com.cg.model.StateCityDTO;
import com.google.gson.Gson;

@Controller
@SessionAttributes({ "userId", "UserName", "totalEmp", "EmpList", "stateList",
		"designationList","EmpInactiveNumber","password" })

public class LoginControllerHelper{

	
	
	
	
	@Autowired
	LoginEmployeeDAO loginemployeeDAOimpl;

	


	

	 @RequestMapping(value="/Welcome",method={RequestMethod.POST,RequestMethod.GET})
	    public String welcomePage(@RequestParam("userId") String userId,@RequestParam("password") String password,ModelMap model)
	    {
	    	 
	    	System.out.println("Vikas in controller");
	    	System.out.println(userId+"  "+password);
	    	ArrayList<String> al=(ArrayList<String>) loginemployeeDAOimpl.loginValidation(userId,password);
	    	
	    	if(al!=null && !(al.isEmpty()))
	    	{
	    		if(al.size()==0)
	    		{
	    			model.addAttribute("reasonObject", al.get(0));
	    		}
	    		else if(al.size()==2)
	    		{
		    		if("A".equalsIgnoreCase(al.get(1)))
		    		{
		    			System.out.println("admin");
		    			model.addAttribute("EmpInactiveNumber", loginemployeeDAOimpl.dashbord());
		    			model.addAttribute("userId", userId);
		    	    	model.addAttribute("UserName", al.get(0));
		    			return "Dashboard";
		    		}
		    		else if("I".equalsIgnoreCase(al.get(1)))
		    		{
		    			System.out.println("Inputter");
		    			
		    			  
		    			   
		    			
		    			//model.addAttribute("EmpList", loginemployeeDAOimpl.employeeList());
		    			model.addAttribute("userId", userId);
		    	    	model.addAttribute("UserName", al.get(0));
		    			return "EmployeeList";
		    		}
		    		else
		    		{
		    			model.addAttribute("reasonObject","You are not authorized to login.....!");
		    			return "login";
		    		}
	    		}
	    	}
	    	
	    	model.addAttribute("reasonObject","validation failed");
	    	return "login";
			
	    }		
	
	 
	 @RequestMapping(value="/employeeListGrid",method={RequestMethod.POST,RequestMethod.GET})
	 @ResponseBody
	public String employeeListGrid() {
		
		 List<EmployeeDTO> employeeList=loginemployeeDAOimpl.employeeList();
		   
		   Gson gson=new Gson();
		   
		  String gsonFormat= gson.toJson(employeeList);
		   
		   System.out.println("gson  "+gsonFormat);
		   return gsonFormat;
	}
	
	

}
