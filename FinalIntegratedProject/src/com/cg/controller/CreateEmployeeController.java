package com.cg.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.cg.dao.CreateEmployeeDAO;
import com.cg.dao.impl.CreateEmployeeDAOimpl;
import com.cg.model.EmployeeDTO;
import com.cg.model.StateCityDTO;
import com.google.gson.Gson;


/**
 * <h1>CREATE EMPLOYEE CONTROLLER</h1>
 * <P>
 * This class is the controller for creating new employee.When a request comes
 *  from the create employee jsp it receives the form inputs and sends to the database.
 * </P>
 * 
 * @author NEHA ERSAVADLA
 * @version 1.0
 * @since 2016-05-02
 */

@Controller
/*I annotated Create Employee Controller with @SessionAttributes to put the same model attribute (myRequestObject) in Spring session.*/

@SessionAttributes({"UserId","UserName","totalEmp","EmpList","stateList","designationList"})
public class CreateEmployeeController {
	
	@Autowired
    CreateEmployeeDAO createEmployeeDAOimpl;
    
    
    
    @RequestMapping(value="/createEmployee", method=RequestMethod.GET)
    //We use request mapping with class definition to create the base URI
    public String createNewEmpPage(ModelMap model)
    {
    	/*System.out.println("i'm");*/
    	EmployeeDTO employeeDTO= new EmployeeDTO();
    	// here we can add any Collection Objects to ModelMap
		// including JSON, String, Array, Map, List, etc...
    	model.addAttribute("employeeDTO",employeeDTO);
    	model.addAttribute("designationList",createEmployeeDAOimpl.loadActiveDesignation());
    	model.addAttribute("StateList", createEmployeeDAOimpl.loadActiveState());
    	return "CreateEmployee";
    }
    
   
    
   @RequestMapping(value="/saveEmployee",method={RequestMethod.POST,RequestMethod.GET})
   /*@RequestMapping annotation is used to map a particular HTTP request method (GET/POST)
   to a specific class/method in controller which will handle the respective request.*/
    public String newEmployee(@ModelAttribute("employeeDTO") EmployeeDTO employeeDTO)
    {
    	
    	/*System.out.println(employeeDTO.getEmployeeId());
    	System.out.println(employeeDTO.getEducation());
    	System.out.println(employeeDTO.getEmployeeRole());
    	System.out.println(employeeDTO.getState());*/
    	
    	//System.out.println(" function call");
    	 createEmployeeDAOimpl.addEmployee(employeeDTO);
    	return "EmployeeList";
    }

   
   @RequestMapping(value="/empList",method=RequestMethod.GET)
   public String empList(ModelMap model)
   {
	   
	   List<EmployeeDTO> employeeList=createEmployeeDAOimpl.employeeList();
	   
	   Gson gson=new Gson();
	   
	  String gsonFormat= gson.toJson(employeeList);
	   
	   System.out.println(gsonFormat);
	   
	   model.addAttribute("EmpList", createEmployeeDAOimpl.employeeList());
	   return "EmployeeList";
   }
    
   
  

@RequestMapping(value = "/cityDetail", method = { RequestMethod.POST })
@ResponseBody
protected String cityDetails(
ModelMap model_map,
@RequestParam(value = "emp_state", required = false) String emp_state)
throws IOException {
   Gson gson = new Gson();
List<StateCityDTO> city = new ArrayList<StateCityDTO>();
 
city=createEmployeeDAOimpl.findCity(emp_state);
 
HashMap<String, String> hm=new HashMap<String, String>();
for(StateCityDTO s :city){
if(!hm.containsKey(s.getStateCityId()))
hm.put("label", s.getStateCityName());
hm.put("value", String.valueOf(s.getStateCityId()));


}
       
String json = gson.toJson(hm);
return json;
}

}
