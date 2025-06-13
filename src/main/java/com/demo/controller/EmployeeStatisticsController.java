package com.demo.controller;

import com.demo.common.AjaxResult;
import com.demo.service.EmployeeStatisticsService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees/statistics")
public class EmployeeStatisticsController {

    @Resource
    private EmployeeStatisticsService statisticsService;

    // 1. 按部门统计员工人数
    @GetMapping("/departmentCount")
    public AjaxResult departmentCount() {
        return AjaxResult.success(statisticsService.countByDepartment());
    }

    // 2. 按职位统计员工人数
    @GetMapping("/positionCount")
    public AjaxResult positionCount() {
        return AjaxResult.success(statisticsService.countByPosition());
    }

    // 3. 按年龄段统计分布
    @GetMapping("/ageRangeCount")
    public AjaxResult ageRangeCount() {
        return AjaxResult.success(statisticsService.countByAgeRange());
    }

    // 4. 按薪资区间统计分布
    @GetMapping("/salaryRangeCount")
    public AjaxResult salaryRangeCount() {
        return AjaxResult.success(statisticsService.countBySalaryRange());
    }
}