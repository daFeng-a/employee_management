package com.demo.controller;

import com.demo.entity.Employee;
import com.demo.service.EmployeeService;
import com.demo.vo.PageReqVO;
import com.demo.common.AjaxResult;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;

    // 分页+多条件查询，直接返回Page<Employee>
    @PostMapping("/query")
    public Page<Employee> queryEmployees(@RequestBody PageReqVO req) {
        return employeeService.queryEmployees(req);
    }

    // 获取所有员工
    @GetMapping
    public AjaxResult getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return AjaxResult.success(employees);
    }

    // 根据id获取员工
    @GetMapping("/{id}")
    public AjaxResult getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee != null) {
            return AjaxResult.success(employee);
        } else {
            return AjaxResult.error("员工不存在");
        }
    }

    // 新增员工
    @PostMapping
    public AjaxResult addEmployee(@RequestBody Employee employee) {
        Employee saved = employeeService.addEmployee(employee);
        return AjaxResult.success(saved);
    }

    // 更新员工
    @PutMapping("/{id}")
    public AjaxResult updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        Employee updated = employeeService.updateEmployee(id, employee);
        if (updated != null) {
            return AjaxResult.success(updated);
        } else {
            return AjaxResult.error("更新失败，员工不存在");
        }
    }

    // 删除员工
    @DeleteMapping("/{id}")
    public AjaxResult deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return AjaxResult.success(null);
    }
}