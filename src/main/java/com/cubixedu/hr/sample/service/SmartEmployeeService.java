package com.cubixedu.hr.sample.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cubixedu.hr.sample.config.HrConfigProperties;
import com.cubixedu.hr.sample.config.HrConfigProperties.Limit;
import com.cubixedu.hr.sample.config.HrConfigProperties.Smart;
import com.cubixedu.hr.sample.model.Employee;

@Service
public class SmartEmployeeService implements EmployeeService {

	@Autowired
	HrConfigProperties config;

	@Override
	public int getPayRaisePercent(Employee employee) {
		
		double yearsWorked = ChronoUnit.DAYS.between(employee.getDateOfStartWork(), LocalDateTime.now()) / 365.0;
		
		Smart smartConfig = config.getSalary().getSmart();
//		if(yearsWorked > smartConfig.getLimit3())
//			return smartConfig.getPercent3();
//		
//		if(yearsWorked > smartConfig.getLimit2())
//			return smartConfig.getPercent2();
//		
//		if(yearsWorked > smartConfig.getLimit1())
//			return smartConfig.getPercent1();
		
		TreeMap<Double, Integer> limits = smartConfig.getLimits();
		//opcionális, 1. megoldás
//		Integer maxPercent = null;
//		for(Entry<Double, Integer> entry: limits.entrySet()) {
//			if(yearsWorked >= entry.getKey()) {
//				maxPercent = entry.getValue();
//			} else
//				break;
//		}
//		
//		return maxPercent != null ? maxPercent : 0;
		
		//opcionális, 2. megoldás
//		Optional<Double> optionalMax = limits.keySet().stream()
//		.filter(k -> yearsWorked >= k)
//		.max(Double::compare);
//		
//		return optionalMax.isPresent() ? limits.get(optionalMax.get()) : 0;
		
		//opcionális, 3. megoldás
//		Entry<Double, Integer> floorEntry = limits.floorEntry(yearsWorked);
//		return floorEntry != null ? floorEntry.getValue() : 0;
		
		//opcionális, 4. megoldás
		List<Limit> limitObjs = smartConfig.getLimitObjs();
		
		Optional<Limit> optionalMax = limitObjs.stream()
		.filter(l -> yearsWorked >= l.getYear())
		.max(Comparator.comparing(Limit::getYear));
		
		return optionalMax.isPresent() ? optionalMax.get().getPercent() : 0;
		
	}

}
