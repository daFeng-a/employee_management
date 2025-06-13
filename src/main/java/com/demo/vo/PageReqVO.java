package com.demo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PageReqVO {
    private int page;        // 页码，从0开始
    private int limit;       // 每页条数

    private String name;             // 员工姓名（模糊查询）
    private Integer age;             // 年龄（等值）
    private Integer minAge;          // 年龄下限
    private Integer maxAge;          // 年龄上限
    private List<String> department; // 部门（可多选）
    private List<String> position;   // 职位（可多选）
    private BigDecimal minSalary;    // 薪资下限
    private BigDecimal maxSalary;    // 薪资上限
}