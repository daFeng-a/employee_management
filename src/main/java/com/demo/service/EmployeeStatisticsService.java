package com.demo.service;

import com.demo.entity.Employee;
import com.demo.repository.EmployeeRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeStatisticsService {

    @Resource
    private EmployeeRepository employeeRepository;

    // 1. 按部门统计员工人数
    public Map<String, Long> countByDepartment() {
        List<Employee> all = employeeRepository.findAll();
        return all.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
    }

    // 2. 按职位统计员工人数
    public Map<String, Long> countByPosition() {
        List<Employee> all = employeeRepository.findAll();
        return all.stream()
                .collect(Collectors.groupingBy(Employee::getPosition, Collectors.counting()));
    }

    // 3. 按年龄段统计分布（例：<25, 25-34, 35-44, 45-54, >=55）
    public Map<String, Long> countByAgeRange() {
        List<Employee> all = employeeRepository.findAll();
        Map<String, Long> result = new LinkedHashMap<>();
        result.put("<25", 0L);
        result.put("25-34", 0L);
        result.put("35-44", 0L);
        result.put("45-54", 0L);
        result.put(">=55", 0L);
        for (Employee e : all) {
            int age = e.getAge();
            if (age < 25) result.put("<25", result.get("<25") + 1);
            else if (age < 35) result.put("25-34", result.get("25-34") + 1);
            else if (age < 45) result.put("35-44", result.get("35-44") + 1);
            else if (age < 55) result.put("45-54", result.get("45-54") + 1);
            else result.put(">=55", result.get(">=55") + 1);
        }
        return result;
    }

    // 4. 按薪资区间统计分布（例：0-4999, 5000-9999, 10000-14999, 15000-19999, >=20000）
    public Map<String, Long> countBySalaryRange() {
        List<Employee> all = employeeRepository.findAll();
        Map<String, Long> result = new LinkedHashMap<>();
        result.put("0-4999", 0L);
        result.put("5000-9999", 0L);
        result.put("10000-14999", 0L);
        result.put("15000-19999", 0L);
        result.put(">=20000", 0L);
        for (Employee e : all) {
            BigDecimal salary = e.getSalary();
            if (salary.compareTo(new BigDecimal("5000")) < 0) result.put("0-4999", result.get("0-4999") + 1);
            else if (salary.compareTo(new BigDecimal("10000")) < 0) result.put("5000-9999", result.get("5000-9999") + 1);
            else if (salary.compareTo(new BigDecimal("15000")) < 0) result.put("10000-14999", result.get("10000-14999") + 1);
            else if (salary.compareTo(new BigDecimal("20000")) < 0) result.put("15000-19999", result.get("15000-19999") + 1);
            else result.put(">=20000", result.get(">=20000") + 1);
        }
        return result;
    }
}