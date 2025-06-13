package com.demo.service;

import com.demo.entity.Employee;
import com.demo.repository.EmployeeRepository;
import com.demo.vo.PageReqVO;
import jakarta.annotation.Resource;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Resource
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Page<Employee> queryEmployees(PageReqVO req) {
        Specification<Employee> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (req.getName() != null && !req.getName().isEmpty()) {
                predicates.add(cb.like(root.get("name"), "%" + req.getName() + "%"));
            }
            if (req.getAge() != null) {
                predicates.add(cb.equal(root.get("age"), req.getAge()));
            }
            if (req.getMinAge() != null) {
                predicates.add(cb.ge(root.get("age"), req.getMinAge()));
            }
            if (req.getMaxAge() != null) {
                predicates.add(cb.le(root.get("age"), req.getMaxAge()));
            }
            if (req.getDepartment() != null && !req.getDepartment().isEmpty()) {
                predicates.add(root.get("department").in(req.getDepartment()));
            }
            if (req.getPosition() != null && !req.getPosition().isEmpty()) {
                predicates.add(root.get("position").in(req.getPosition()));
            }
            if (req.getMinSalary() != null) {
                predicates.add(cb.ge(root.get("salary"), req.getMinSalary()));
            }
            if (req.getMaxSalary() != null) {
                predicates.add(cb.le(root.get("salary"), req.getMaxSalary()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return employeeRepository.findAll(spec, PageRequest.of(req.getPage(), req.getLimit()));
    }

    public Employee getEmployeeById(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee.orElse(null);
    }

    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee employee) {
        if (employeeRepository.existsById(id)) {
            employee.setId(id);
            return employeeRepository.save(employee);
        }
        return null;
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}