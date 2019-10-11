package com.xsm.cache;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 1. 导入数据库文件, 创建出department和employee表
 * 2. 创建javaBean封装
 * 3. 整合Mybatis操作数据库
 *    1. 配置数据源信息
 *    2. 使用注解版的mybatis
 *      1) @MapperScan指定需要扫描的mapper接口所在包
 * 二, 快速体验缓存
 * 步骤:
 * 1. 开启基于注解的缓存
 * 2. 标注缓存注解即可
 *    @Cacheable
 *    @CacheEvict
 *    @CachePut
 */
@EnableCaching
@MapperScan("com.xsm.cache.mapper")
@SpringBootApplication
public class Springboot01CacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot01CacheApplication.class, args);
    }

}
