package com.luv2code.springboot.cruddemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springboot.cruddemo.entity.Employee;
import com.luv2code.springboot.cruddemo.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeRESTController {
	
	private EmployeeService employeeService;
	
	@Autowired
	public EmployeeRESTController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	// list all employees
	@GetMapping("/employees")
	public List<Employee> listEmployees(){
		return employeeService.findAll();
	}
	
	// finding employee by id
	@GetMapping("/employees/{employeeId}")
	public Employee findEmployeeById(@PathVariable int employeeId) {
		
		Employee employee = employeeService.findById(employeeId);
		
		return employee;
	}
	
	@PostMapping("/employees/")
	public Employee createEmployee(@RequestBody Employee employee) {
		
		// just in case they try to pass an ID into the json post request
		// we're setting to 0 because this controller only add employees
		employee.setId(0);
		
		employeeService.save(employee);
		
		return employee;
	}
	
	
	@PutMapping("/employees/")
	public Employee updateEmployee(@RequestBody Employee employee) {
		
		employeeService.save(employee);
		
		return employee;
	}
	
	@DeleteMapping("/employees/{employeeId}")
	public String updateEmployee(@PathVariable int employeeId) {
		
		Employee tempEmployee = employeeService.findById(employeeId);
		
		if(tempEmployee == null) {
			throw new RuntimeException("This employee doesn't exist int he database, id: " + employeeId);
		}
		
		employeeService.deleteById(employeeId);
		
		return "Deleted employee with ID of: " + employeeId;
	}
	
	
}
