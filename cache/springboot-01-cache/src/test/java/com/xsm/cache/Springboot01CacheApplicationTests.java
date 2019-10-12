package com.xsm.cache;

import com.xsm.cache.mapper.EmployeeMapper;
import com.xsm.cache.po.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot01CacheApplicationTests {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void contextLoads() {
        Employee emById = employeeMapper.getEmById(2L);
        System.out.println(emById);
    }

    /**
     * Redis 常见的五大数据模型的操作
     * String(字符串), List(列表), Set(集合), Hash(散列), ZSet(有序集合)
     * stringRedisTemplate.opsForValue() -> 字符串
     * stringRedisTemplate.opsForList() -> 列表
     * stringRedisTemplate.opsForSet() -> 集合
     * stringRedisTemplate.opsForHash() -> 散列
     * stringRedisTemplate.opsForZSet() -> 有序集合
     *
     */
    @Test
    public void redisTest() {
        // 给redis中保存数据
        stringRedisTemplate.opsForValue().append("msg", "hello");

        String msg = stringRedisTemplate.opsForValue().get("msg");
        System.out.println(msg);
    }

}
