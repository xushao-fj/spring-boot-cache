package com.xsm.cache.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author xsm
 * @Date 2019/10/11 23:15
 */
@Configuration
public class MyCacheConfig {

    /**
     * 自定义KeyGenerator
     * @return
     */
    @Bean("myKeyGenerator")
    public KeyGenerator keyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                return method.getName() + "[" + Arrays.asList(params).toString() + "]";
            }
        };
       // return (target, method, params) -> method.getName() + "[" + Arrays.asList(params).toString() + "]";
    }
}
