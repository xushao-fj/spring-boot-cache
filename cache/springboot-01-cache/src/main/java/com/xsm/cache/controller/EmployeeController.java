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
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/emp/{id}")
    public Employee getEmployee(@PathVariable("id") Long id){
        Employee emp = employeeService.getEmp(id);
        return emp;

    }

    @GetMapping("/emp")
    public Employee update(Employee employee){
        Employee updateEmp = employeeService.updateEmp(employee);
        return updateEmp;

    }

    @GetMapping("/delEmp")
    public String deleteEmp(Long id){
        System.out.println("deleteEmp:" + id);
        employeeService.deleteEmp(id);
        return "success";
    }

    @GetMapping("/emp/lastName/{lastName}")
    public Employee getEmpByLastName(@PathVariable("lastName")String lastName){
        return employeeService.getEmpByLastName(lastName);
    }

}
