package com.cubixedu.hr.sample.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cubixedu.hr.sample.model.Employee;
import com.cubixedu.hr.sample.model.Position;
import com.cubixedu.hr.sample.repository.EmployeeRepository;
import com.cubixedu.hr.sample.repository.PositionRepository;

@Service
public abstract class AbstractEmployeeService implements EmployeeService {
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	PositionRepository positionRepository;

	@Override
	public Employee save(Employee employee) {
		if(!(employee.getEmployeeId() == null || employee.getEmployeeId() == 0))
			throw new IllegalArgumentException();
		processPosition(employee);
		
		return employeeRepository.save(employee);
	}

	private void processPosition(Employee employee) {
		String positionName = employee.getPosition().getName();
		if(positionName != null) {
			List<Position> positions = positionRepository.findByName(positionName);
			Position position = null;
			if(positions.isEmpty()) {
				position = positionRepository.save(employee.getPosition());				
			} else {
				position = positions.get(0);
			}
			employee.setPosition(position);
		}
		
	}

	@Override
	public Employee update(Employee employee) {
		if(!employeeRepository.existsById(employee.getEmployeeId())) {
			return null;
		}
		processPosition(employee);
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
