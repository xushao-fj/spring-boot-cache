package com.xsm.cache.controller;

import com.xsm.cache.po.Department;
import com.xsm.cache.service.DeptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xsm
 * @Date 2019/10/12 22:55
 */
@RestController
public class DeptController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private DeptService deptService;

    @GetMapping("/dept/{id}")
    public Department getById(@PathVariable("id") Long id){
        log.info("查询部门, 部门id: {}", id);
        return deptService.getDeptById(id);
    }
}
