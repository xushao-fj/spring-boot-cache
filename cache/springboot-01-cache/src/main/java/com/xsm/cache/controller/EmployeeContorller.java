package com.xsm.cache.controller;

import com.xsm.cache.po.Employee;
import com.xsm.cache.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xsm
 * @Date 2019/10/11 22:15
 */
@RestController
public class EmployeeContorller {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/emp/{id}")
    public Employee getEmployee(@PathVariable("id") Long id){
        Employee emp = employeeService.getEmp(id);
        return emp;

    }
}
