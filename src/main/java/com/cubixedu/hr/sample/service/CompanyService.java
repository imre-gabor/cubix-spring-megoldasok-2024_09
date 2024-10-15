package com.cubixedu.hr.sample.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cubixedu.hr.sample.model.Company;
import com.cubixedu.hr.sample.model.Employee;
import com.cubixedu.hr.sample.repository.CompanyRepository;

@Service
public class CompanyService {
	
	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	EmployeeService employeeService;

	public Company save(Company company) {
		if(!(company.getId() == null || company.getId() == 0))
			throw new IllegalArgumentException();
		return companyRepository.save(company);
	}

	public Company update(Company company) {
		if(!companyRepository.existsById(company.getId())) {
			return null;
		}
		return companyRepository.save(company);
	}

	public List<Company> findAll() {
		return companyRepository.findAll();
	}

	public Optional<Company> findById(long id) {
		return companyRepository.findById(id);
	}

	public void delete(long id) {
		companyRepository.deleteById(id);
	}
	
	public Company addEmployee(long id, Employee employee) {
		Company company = companyRepository.findById(id).get();
		company.addEmployee(employee);
		employeeService.save(employee);
		return company;
	}
	
	public Company deleteEmployee(long id, long employeeId) {
		Company company = companyRepository.findById(id).get();
		Employee employee = employeeService.findById(employeeId).get();
		company.removeEmployee(employee);
		employeeService.update(employee);
		return company;
	}
	
	public Company replaceEmployees(long id, List<Employee> employees) {
		Company company = companyRepository.findById(id).get();
		company.getEmployees().forEach(e -> e.setCompany(null));
		company.getEmployees().clear();
		employees.forEach(e -> {
			company.addEmployee(e);
			employeeService.save(e);
		});
		return company;
	}
}
