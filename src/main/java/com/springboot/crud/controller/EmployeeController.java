package com.springboot.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.crud.model.Employee;
import com.springboot.crud.repository.EmployeeRepository;
import com.springboot.crud.service.EmployeeService;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	EmployeeRepository empRepo;
	
	// display list of employees
	@GetMapping("/home")
	public String viewHome(Model model) {
		model.addAttribute("listEmployees", employeeService.getAllEmployees());
		return "index";
	}
	
	// save employee controller
	@GetMapping("/showNewEmployeeForm")
	public String showNewEmployeeForm(Model model) {
		// create model attribute to bind form data
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "new_employee";	
	}
	
	// Saving to database
	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") Employee employee, RedirectAttributes rd) {
		
		
		if(empRepo.findByEmail(employee.getEmail()) !=null) {
			System.out.println("faillllllll");
			rd.addFlashAttribute("fail", "Email already exists");
			return "redirect:/showNewEmployeeForm";
		}
		
		// save employee to database
		employeeService.saveEmployee(employee);
		rd.addFlashAttribute("success", "Employee successfully registered");
		return "redirect:/home";
	}
	


	
	
	
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable (value = "id") long id, Model model) {
		// get employee from the service
		Employee employee = employeeService.getEmployeeById(id);
		
		// set employee as a model attribute to pre-populate the form
		model.addAttribute("employee", employee);
		return "update_employee";
	}
	
	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable (value="id") long id) {
		
		// call delete employee method
		this.employeeService.deleteEmployeeById(id);
		return "redirect:/home";
	}
}
