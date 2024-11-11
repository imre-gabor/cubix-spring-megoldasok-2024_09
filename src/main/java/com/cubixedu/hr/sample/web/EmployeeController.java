package com.cubixedu.hr.sample.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.data.web.SortDefault.SortDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cubixedu.hr.sample.dto.EmployeeDto;
import com.cubixedu.hr.sample.mapper.EmployeeMapper;
import com.cubixedu.hr.sample.model.Employee;
import com.cubixedu.hr.sample.repository.EmployeeRepository;
import com.cubixedu.hr.sample.service.EmployeeService;
import com.cubixedu.hr.sample.service.SalaryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EmployeeMapper employeeMapper;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	
//	@GetMapping
//	public List<EmployeeDto> findAll(){
//		return new ArrayList<>(employees.values());
//	}

	//1. megoldás
//	@GetMapping(params = "minSalary")
//	public List<EmployeeDto> findBySalary(@RequestParam int minSalary){
//		return employees.values().stream()
//				.filter(e -> e.getSalary() >= minSalary)
//				.toList();
//	}
	
	//2. megoldás
	@GetMapping
	public List<EmployeeDto> findAll(@RequestParam Optional<Integer> minSalary, @SortDefault("employeeId") Pageable pageable){
		List<Employee> employees = null;
		if (minSalary.isPresent()) {
			Page<Employee> page = employeeRepository.findBySalaryGreaterThan(minSalary.get(), pageable);
			System.out.println(page.getTotalElements());
			System.out.println(page.isFirst());
			System.out.println(page.isLast());
			System.out.println(page.hasPrevious());
			System.out.println(page.hasNext());
			
			employees = page.getContent();
		} else {
			employees = employeeService.findAll();
		}
		return employeeMapper.employeesToDtos(employees);
	}
	
	@GetMapping("/{id}")
	public EmployeeDto findById(@PathVariable long id) {
		Employee employee = findByIdOrThrow(id);
		return employeeMapper.employeeToDto(employee);
	}
	private Employee findByIdOrThrow(long id) {
		return employeeService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	@PostMapping
	public EmployeeDto create(@RequestBody @Valid EmployeeDto employeeDto) {
		return employeeMapper.employeeToDto(employeeService.save(employeeMapper.dtoToEmployee(employeeDto)));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EmployeeDto> update(@PathVariable long id, @RequestBody @Valid EmployeeDto employeeDto) {
		employeeDto.setId(id);
		Employee updatedEmployee = employeeService.update(employeeMapper.dtoToEmployee(employeeDto));
		if (updatedEmployee == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(employeeMapper.employeeToDto(updatedEmployee));
		}
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		employeeService.delete(id);
	}
	
	@PostMapping("/payRaise")
	public int getPayRaisePercent(@RequestBody Employee employee) {
		return employeeService.getPayRaisePercent(employee);
	}
	@PostMapping("/search")
	public List<EmployeeDto> findByExample(@RequestBody EmployeeDto example) {
		return employeeMapper.employeesToDtos(employeeService.findEmployeesByExample(employeeMapper.dtoToEmployee(example)));
	}

}
