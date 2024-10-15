package com.cubixedu.hr.sample.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cubixedu.hr.sample.model.Employee;
import com.cubixedu.hr.sample.repository.EmployeeRepository;

@Service
public abstract class AbstractEmployeeService implements EmployeeService {
	
	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public Employee save(Employee employee) {
		if(!(employee.getEmployeeId() == null || employee.getEmployeeId() == 0))
			throw new IllegalArgumentException();
		return employeeRepository.save(employee);
	}

	@Override
	public Employee update(Employee employee) {
		if(!employeeRepository.existsById(employee.getEmployeeId())) {
			return null;
		}
		return employeeRepository.save(employee);
	}

	@Override
	public List<Employee> findAll() {
		return employeeRepository.findAll();
	}

	@Override
	public Optional<Employee> findById(long id) {
		return employeeRepository.findById(id);
	}

	@Override
	public void delete(long id) {
		employeeRepository.deleteById(id);
	}
	
}
