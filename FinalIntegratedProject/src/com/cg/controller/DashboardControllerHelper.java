//package com.cg.controller;
//
//import java.util.ArrayList;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.SessionAttributes;
//
//import com.cg.dao.EmployeeDAO;
//import com.cg.dao.impl.EmployeeDAOimpl;
//import com.cg.model.EmployeeDTO;
//
//@Controller
//@SessionAttributes({ "totalEmp", "EmpInactiveNumber" })
//public class DashboardControllerHelper {
//
//	
//	
//	@Autowired(required=true)
//	EmployeeDAO employeeDAOimpl;
//
//	public void setEmployeeDAOimpl(EmployeeDAO employeeDAOimpl) {
//		this.employeeDAOimpl = employeeDAOimpl;
//	}
//
//	
//
//	@RequestMapping(value = "/TotalEmployeeList", method = RequestMethod.GET)
//	public String totalEmployeeList(ModelMap model) {
//		ArrayList<EmployeeDTO> empdet = (ArrayList<EmployeeDTO>) employeeDAOimpl
//				.totalEmployeeList();
//		model.addAttribute("totalEmp", empdet);
//		return "EmployeeActivation";
//	}
//
//	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
//	public String dashboard(ModelMap model) {
//		model.addAttribute("EmpInactiveNumber", employeeDAOimpl.dashbord());
//
//		return "Dashboard";
//	}
//
//}
