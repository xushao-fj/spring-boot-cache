package com.xsm.cache;

import com.xsm.cache.mapper.EmployeeMapper;
import com.xsm.cache.po.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot01CacheApplicationTests {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Test
    public void contextLoads() {
        Employee emById = employeeMapper.getEmById(2L);
        System.out.println(emById);
    }

}
