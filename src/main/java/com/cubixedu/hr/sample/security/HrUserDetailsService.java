package com.cubixedu.hr.sample.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cubixedu.hr.sample.model.Employee;
import com.cubixedu.hr.sample.repository.EmployeeRepository;

@Service
public class HrUserDetailsService implements UserDetailsService {
	
	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Employee emp = employeeRepository.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException(username));
		
		return new HrUser(username, emp.getPassword(), List.of(new SimpleGrantedAuthority("USER")), emp);
	}

}
