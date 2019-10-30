package com.luv2code.springboot.cruddemo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luv2code.springboot.cruddemo.dao.EmployeeRepository;
import com.luv2code.springboot.cruddemo.entity.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	/* we are currently using Spring Data JPA for our DAO. No need for DAO implementations any longer... wohoo..
	 * commenting out below line. 
	 */
	
	//private EmployeeDAO employeeDAO;
	
	private EmployeeRepository employeeRepository;
	
	
	/*
	 * since we have more than one implementation for EmployeeDAO, we must tell spring which one we want to use.
	 * so we can use @Qualifier annotation with the name of the implementation.
	 * keep in mind that spring will make the first letter of the bean class lower case. employeeDAOJpaImpl
	 * 
	 * comenting out below constructor because we don't need DAO implementation when using Spring Data JPA
	 */
	
	//	@Autowired
	//	public EmployeeServiceImpl(@Qualifier("employeeDAOJpaImpl") EmployeeDAO employeeDAO) {
	//		this.employeeDAO = employeeDAO;
	//	}
	
		
	@Autowired
	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}
	
	

	@Override
	//@Transactional		NO NEED FOR TRANSACTIONAL SPRING DATA JPA PROVIDES IT OUT OF THE BOX!! WOW
	public List<Employee> findAll() {
		return employeeRepository.findAll();
	}

	@Override
	//@Transactional		NO NEED FOR TRANSACTIONAL SPRING DATA JPA PROVIDES IT OUT OF THE BOX!! WOW
	public Employee findById(int id) {
		Optional<Employee> result = employeeRepository.findById(id);
		
		Employee theEmployee = null;
		
		if(result.isPresent()) {
			theEmployee = result.get();
		}else {
			throw new RuntimeException("Did not find employee with ID : " + id);
		}
		
		return theEmployee;
		
	}

	@Override
	//@Transactional		NO NEED FOR TRANSACTIONAL SPRING DATA JPA PROVIDES IT OUT OF THE BOX!! WOW
	public void deleteById(int id) {
		employeeRepository.deleteById(id);
	}

	@Override
	//@Transactional		NO NEED FOR TRANSACTIONAL SPRING DATA JPA PROVIDES IT OUT OF THE BOX!! WOW
	public void save(Employee employee) {
		employeeRepository.save(employee);		
	}

}
