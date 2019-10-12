package com.xsm.cache.service;

import com.xsm.cache.mapper.EmployeeMapper;
import com.xsm.cache.po.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author xsm
 * @Date 2019/10/11 22:13
 */
@CacheConfig(cacheNames = "emp") // 抽取缓存的公共配置
@Service
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /** 操作字符串*/
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 将方法的运行结果进行缓存, 以后再要相同的数据, 直接从缓存中获取, 不用再调用方法
     * CacheManager管理多个Cache组件的, 对缓存的真正CRUD操作在Cache组件中, 每一个缓存组件中都有一个唯一的名字
     * 几个属性:
     *     CacheNames/value: 指定缓存的名字, 将方法的返回值结果放在哪个缓存中, 是数组的形式, 可以指定多个缓存;
     *     key: 缓存数据时使用的key: 可以用它来指定, 如果不指定, 默认使用方法参数的值  1-方法的返回值
     *          编写SpEL表达式: #id: 参数id的值, #a0  #p0  #root.args[0]
     *     keyGenerator: key的生成器: 可以自己指定key的生成器的组件id
     *     (key/keyGenerator 只能二选一)
     *
     *     cacheManager: 指定缓存管理器; 或者指定缓存解析器 CacheResolver 也是二选一
     *     condition: 指定符合条件的情况下才缓存
     *     unless: 否定缓存, 当unless指定的条件为true, 方法的返回值就不会被缓存, 可以获取到结果进行判断
     *             (unless = "#result == null")
     *     sync: 是否使用异步模式
     *     ,key = "#id", condition = "#id>0"
     *
     * 原理:
     *   1. 自动配置类: CacheAutoConfiguration
     *   2. 缓存的配置类
     *   org.springframework.boot.autoconfigure.cache.GenericCacheConfiguration
     *   org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration
     *   org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration
     *   org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration
     *   org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration
     *   org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration
     *   org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration
     *   org.springframework.boot.autoconfigure.cache.CaffeineCacheConfiguration
     *   org.springframework.boot.autoconfigure.cache.SimpleCacheConfiguration
     *   org.springframework.boot.autoconfigure.cache.NoOpCacheConfiguration
     *
     *   3. 哪个配置默认生效? -> SimpleCacheConfiguration
     *
     *   4. 给容器中注册了一个cacheManager: ConcurrentMapCacheManager
     *   5. 可以获取和创建ConcurrentMapCache类型的缓存组件: 它的作用将数据保存在ConcurrentMap中
     *   6. 如何保存数据
     *   运行流程:
     *   1. 方法运行之前, 先去查询Cache(缓存组件), 按照cacheNames指定的名字获取;
     *      (ConcurrentMapCacheManager先获取相应的缓存), 第一次获取缓存如果没有Cache组件会自动创建.
     *   2. 去Cache中查找缓存的内容, 使用一个key, 默认就是方法的参数;
     *      key是按照某种策略生成的, 默认是使用keyGenerator生成, 默认使用SimpleKeyGenerator生成key
     *         SimpleKeyGenerator 生成key的默认策略:
     *           1. 如果没有参数, key = new SimpleKey();
     *              如果有一个参数: key = 参数的值
     *              如果有多个参数: key = new SimpleKey(params);
     *   3. 没有查到缓存就调用目标方法.
     *   4. 将目标方法返回的结果, 放进缓存中
     *
     *   @Cacheable标注方法执行之前先来检查缓存中有没有这个数据, 默认按照参数的值作为key去查询缓存,
     *   如果没有就运行方法并将结果放入缓存, 以后再来调用就可以直接使用缓存中的数据
     *
     *   核心关注:
     *   1. 使用CacheManager按照名字得到Cache组件(默认ConcurrentMapCacheManager) 按照名字得到Cache(默认ConcurrentMapCache)
     *   2. key使用KeyGenerator生成的, 默认是SimpleKeyGenerator
     *
     *
     * @param id
     * @return
     */
    @Cacheable(cacheNames = {"emp"}, keyGenerator = "myKeyGenerator")
    public Employee getEmp(Long id){
        System.out.println("查询" + id + "号员工");
        Employee emById = employeeMapper.getEmById(id);
        return emById;

    }

    /**
     * @CachePut: 即调用方法, 同步更新缓存数据:
     * 修改了数据库的某个数据, 同时更新缓存
     * 运行时机:
     *   1. 先调用目标方法
     *   2. 将目标方法的结果缓存起来
     * 测试步骤:
     *   1. 查询1号员工, 查到的结果放到缓存中
     *   2. 以后查询还是之前的结果
     *   3. 更新1号员工: [lastName:zhangsan; gender: 0]
     *         将方法的返回值也放进缓存了
     *         key: 传入的employee对象  值: 返回的employee对象;
     *   4. 查询1号员工?
     *         应该是更新后的员工;
     *           key = "@employee.id" : 使用传入的参数的员工id
     *         @Cacheable 的key是不能用#result
     *         为什么是没更新前的? [1号员工没有在缓存中更新]
     * @param employee
     * @return
     */
    @CachePut(value = "emp", key = "#result.id")
    public Employee updateEmp(Employee employee){
        employeeMapper.updateEm(employee);
        System.out.println(employee);
        return employee;

    }

    /**
     * @CacheEvict: 缓存清除
     *   key: 指定要删除的key
     *   allEntries: 是否删除全部
     *   beforeInvocation = false: 缓存的清除是否在方法执行之前清除
     *                             默认代表是在方法执行之后执行 -> 如果出现异常缓存就不会清除
     *   beforeInvocation = false: 默认代表是在方法执行之前执行 -> 无论方法执行结果如何, 都能够清除缓存
     */
    @CacheEvict(value = "emp", key = "#id", allEntries = true)
    public void deleteEmp(Long id){
        System.out.println("deleteEmp:" + id);
        employeeMapper.deleteEmById(id);
    }

    /**
     * @Caching: 定义复杂的缓存规则
     */
    @Caching(
        cacheable = {
            @Cacheable(value = "emp", key = "#lastName")
        },
        put = { // @CachePut 方法一定会执行
            @CachePut(value = "emp", key = "#result.id"),
            @CachePut(value = "emp", key = "#result.email")
        }
    )
    public Employee getEmpByLastName(String lastName){
        return employeeMapper.getEmpByLastName(lastName);

    }

}
