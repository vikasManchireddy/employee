package com.cg.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;






import com.cg.dao.UpdateEmployeeDAO;
import com.cg.model.DesignationDTO;
import com.cg.model.EmployeeDTO;
import com.cg.model.StateCityDTO;
import com.google.gson.Gson;

@Controller
@RequestMapping("/employee")
public class UpdateControllerHelper {

	private UpdateEmployeeDAO updateEmpDAO;

	@Autowired
	public void setUpdateEmployeeDAO(final UpdateEmployeeDAO updateEmpDAO) {
		this.updateEmpDAO = updateEmpDAO;
	}

	//this method is used to save details of employee after submit form
	@RequestMapping(value = "/saveForm", method = RequestMethod.POST)
	protected String saveDetails(ModelMap model_map,
			@ModelAttribute("Employee") final EmployeeDTO employee)
			throws IOException {
		updateEmpDAO.update(employee);
		List<EmployeeDTO> employee1 = updateEmpDAO.findAll();
		model_map.addAttribute("employee1", employee1);
		return "EmployeeList";
	}

	//this method takes 'id' as request parameter and retrieves the all the state's & designation's details ,employee's details having emp_Id='id' and displays on the updateEmployee.jsp
	@RequestMapping(value = "/editDetail", method = { RequestMethod.GET })
	protected String editDetails(
			ModelMap model_map,
			@ModelAttribute("Employee") EmployeeDTO employee,
			@RequestParam(value = "emp_state", required = false) String emp_state,
			@RequestParam(value = "id", required = false) final String[] id)
			throws IOException {
		employee = updateEmpDAO.findById(Integer.parseInt(id[0]));
		model_map.addAttribute("employee1", employee);
		List<StateCityDTO> state = new ArrayList<StateCityDTO>();
		state = updateEmpDAO.findState();
		Map<String, String> stateMap = new LinkedHashMap<String, String>();
		for(StateCityDTO s :state){
			if(!stateMap.containsKey(s.getStateCityId()))
			stateMap.put(s.getStateCityId().toString(), s.getStateCityName());
		}
		model_map.addAttribute("stateOption",stateMap);
		List<DesignationDTO> desig = new ArrayList<DesignationDTO>();
		desig = updateEmpDAO.findDesig();
		model_map.addAttribute("desig", desig);
		return "UpdateEmployee";
	}

	//this method is used to display cities according to the selected state
	@RequestMapping(value = "/cityDetail", method = { RequestMethod.POST })
	@ResponseBody protected String cityDetails(
			ModelMap model_map,
			@RequestParam(value = "emp_state", required = false) String emp_state)
			throws IOException {
		    Gson gson = new Gson();
			List<StateCityDTO> city = new ArrayList<StateCityDTO>();
			
			city=updateEmpDAO.findCity(emp_state);
			
			HashMap<String, String> hm=new HashMap<String, String>();
			for(StateCityDTO s :city){
				if(!hm.containsKey(s.getStateCityId()))
					hm.put("label", s.getStateCityName());
					hm.put("value", s.getStateCityId().toString());
			}
			 String json ="";
			 if(hm.isEmpty()==false){
				 json = gson.toJson(hm);
			 }
			return json;
	}
	
}
