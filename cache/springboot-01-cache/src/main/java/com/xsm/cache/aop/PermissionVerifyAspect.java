package com.xsm.cache.aop;

import com.xsm.cache.annotation.PermissionAnnotation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xsm
 * @version 1.0.0
 * @date 2019/12/8
 * @description TODO 权限认证切面
 */
@Aspect
@Component
@Order(1)
public class PermissionVerifyAspect {

    private final Logger logger = LoggerFactory.getLogger(PermissionVerifyAspect.class);

    private final HttpServletRequest request;

    @Autowired
    public PermissionVerifyAspect(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * 权限认证拦截切点
     */
    @Pointcut("@annotation(com.xsm.cache.annotation.PermissionAnnotation)")
    public void permissionPoint() {

    }

    /**
     * 权限校验切面
     * @param joinPoint      切入点
     * @param permissionAnnotation 校验操作标记注解
     * @return 返回切入点方法调用结果
     * @throws Throwable 抛出登录校验过程中的异常
     */
    @Around("permissionPoint() && @annotation(permissionAnnotation)")
    public Object permissionVerifyAspect(ProceedingJoinPoint joinPoint, PermissionAnnotation permissionAnnotation) throws Throwable {
        boolean permission = permissionAnnotation.permission();
        if (permission) {
            String requestURI = request.getRequestURI();
            System.out.println(requestURI);
            throw new RuntimeException("没有权限");
        }
        return joinPoint.proceed();
    }

}

