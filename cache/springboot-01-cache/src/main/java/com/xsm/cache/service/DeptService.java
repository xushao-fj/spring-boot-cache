package com.xsm.cache.service;

import com.xsm.cache.mapper.DepartmentMapper;
import com.xsm.cache.po.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author xsm
 * @Date 2019/10/12 22:51
 */
@Service
public class DeptService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private DepartmentMapper departmentMapper;

    @Cacheable(cacheNames = "dept")
    public Department getDeptById(Long id){
        log.info("查询部门, id: {}", id);
        Department dept = departmentMapper.getDeptById(id);
        return dept;

    }
}
