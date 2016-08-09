package com.cg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.cg.dao.DeleteResetEmployeeDAO;


/**
 * <h1>DELETE EMPLOYEE CONTROLLER</h1>
 * <P>
 * This class is the controller for delete employee. When request comes for
 * delete employee it updates the employee status from AC to DE 
 * </P>
 * 
 * @author S Sneha & A Shilpa
 * @version 1.0
 * @since 2016-05-02
 */

@Controller
@SessionAttributes({"userId","EmpList"})

  public class DeleteEmployeeController {

	@Autowired
	   DeleteResetEmployeeDAO employeeDAOimpl;
	
	
	@RequestMapping(value="/DeleteEmployee",method={RequestMethod.POST,RequestMethod.GET})
    public String DeleteEmployee(@RequestParam("employeeId") String employeeId) {
 	 
    	System.out.println("in delet emp");
 	   //System.out.println(EmployeeDTO.getEmployeeId());
 	
 	  employeeDAOimpl.deleteEmployee(Integer.parseInt(employeeId));
 	   
 	   System.out.println("no probs");
    	//return "EmployeeList";
 	   
 	   return "EmployeeList";
    }
}
