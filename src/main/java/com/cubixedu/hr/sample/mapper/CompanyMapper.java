package com.cubixedu.hr.sample.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.cubixedu.hr.sample.dto.CompanyDto;
import com.cubixedu.hr.sample.dto.EmployeeDto;
import com.cubixedu.hr.sample.model.Company;
import com.cubixedu.hr.sample.model.Employee;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
	
	List<CompanyDto> companiesToDtos(List<Company> companies);
	
	@IterableMapping(qualifiedByName = "summary")
	List<CompanyDto> companiesToSummaryDtos(List<Company> companies);
	
	CompanyDto companyToDto(Company company);
	
	@Mapping(target = "employees", ignore = true)
	@Named("summary")
	CompanyDto companyToSummaryDto(Company company);

	Company dtoToCompany(CompanyDto companyDto);

	List<Company> dtosToCompanys(List<CompanyDto> companies);
	
	@Mapping(source = "employeeId", target = "id")
	@Mapping(source = "jobTitle", target = "title")
	@Mapping(source = "dateOfStartWork", target = "entryDate")
	EmployeeDto employeeToDto(Employee employee);

	@InheritInverseConfiguration
	Employee dtoToEmployee(EmployeeDto employeeDto);

	List<Employee> dtosToEmployees(List<EmployeeDto> employees);

}
