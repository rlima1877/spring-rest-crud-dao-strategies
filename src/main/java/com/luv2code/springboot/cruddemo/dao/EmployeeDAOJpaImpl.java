package com.luv2code.springboot.cruddemo.dao;

import java.util.List;

// standard JPA API
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springboot.cruddemo.entity.Employee;

@Repository
public class EmployeeDAOJpaImpl implements EmployeeDAO {
	
	private EntityManager entityManager;
	
	@Autowired
	public EmployeeDAOJpaImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Employee> findAll() {
		
		Query query = entityManager.createQuery("from Employee");
		
		List<Employee> employees = query.getResultList();
		
		return employees;
	}

	@Override
	public Employee findById(int id) {
		
		Employee theEmployee = entityManager.find(Employee.class, id);
		
		return theEmployee;
	}

	@Override
	public void deleteById(int id) {
		
		Query theQuery = entityManager.createQuery("delete from Employee where id=:employeeId");
		theQuery.setParameter("employeeId", id);
		
		theQuery.executeUpdate();
		
	}

	@Override
	public void save(Employee employee) {
		
		// remember, if the employee ID == 0 the merge() method will add the employee.
		// if the employee ID != 0, it will update that given employee by using the ID.
		// in the controller class we create two different methods to handle. One for the update, and one for the insert.
		// so if the client passes an ID while try to insert, we just set the ID to zero prior to calling the DAO.
		// In the update controller, we just pass the entire object and the DAO will know to update.
		
		// This employee being passed as paratemer to this method is coming all the way up from the client, which gets passed into the controller, which passes it 
		// to the Service, then the service passes it to DAO, which is here.
		// We're pretty much holding the employee object and once we are done, we'll be going back up the stack with the object ID
		
		
		Employee dbEmployee = entityManager.merge(employee); // here is where the database gets a new row inserted.
		
		// here is where we are adding an ID to the object that came down all the way down here. 
		// the client will not get this object until we make it back up the stack. All the way up to the controller.
		// Once there, the controller will return this object which we have manipulated.
		// notice we don't have to return this new object, because we are never changing it's address.
		// we just passed it around.
		
		// WHY DO WE NEED TO SET THE ID BEFORE ENDING THIS METHOD.
		// BECAUSE NATIVE JPA DOES NOT DO IT FOR US. HIBERNATE DOES IT.
		
		employee.setId(dbEmployee.getId());
		
		
	}

}
