package com.cg.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cg.dao.EmployeeModificationDAO;
import com.cg.model.EmployeeDTO;
import com.google.gson.Gson;


@Controller
public class EmployeeActivationController {
	
	
	@Autowired
	EmployeeModificationDAO employeeModificationDAOimpl;
	
	
	
	 @RequestMapping("/TotalEmployeeListGrid")
	    @ResponseBody
	    public String TotalEmployeeListActivation(ModelMap model)
	    {
	    	ArrayList<EmployeeDTO> empdet=(ArrayList<EmployeeDTO>) employeeModificationDAOimpl.totalEmployeeList();
	    	
	    	
	    	 Gson gson=new Gson();
	  	   
	    	 String gsonFormat= gson.toJson(empdet);
	   	  
	   	   
	   	   //out.println(gsonFormat);
	    	
	  
	    	return gsonFormat;
	    }
	
	   /* 
	    * update Employee  method is to update the designation ie to activate or to inactivate or to delete
	    */

	   @RequestMapping(value="/updateEmployee",method={RequestMethod.POST})
	   @ResponseBody
	   public String updateEmployee(@RequestParam("employeeData") int employeeId,@RequestParam("employeeStatus") String employeeStatus) {
		   System.out.println("before: "+employeeStatus);
			  
		   if("AC".equals(employeeStatus))
			   employeeStatus="IN";
				  else
					  employeeStatus="AC";
			  
			  System.out.println("After: "+employeeStatus);
		 
		   employeeModificationDAOimpl.updateEmployee(employeeId,employeeStatus);
		 
	   	return "EmployeeActivation";
	   }
	 
	   @RequestMapping(value="/deleteEmployee",method={RequestMethod.POST})
	   @ResponseBody
	   public String deleteEmployee(@RequestParam("employeeData") int employeeId) {
		 
		   employeeModificationDAOimpl.updateEmployee(employeeId,"DE");
		 
	   	return "EmployeeActivation";
	   }
	   
	   
	   
	   /* TotalEmployeeList is the URI for which this controller will be used.*/
	    @RequestMapping("/TotalEmployeeList")
	    public String totalEmployeeList(ModelMap model)
	    {
	    	
	    	return "EmployeeActivation";
	    }	   

}
