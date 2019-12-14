package com.xsm.cache.controller;

import com.xsm.cache.annotation.PermissionAnnotation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xsm
 * @version 1.0.0
 * @date 2019/12/8
 * @description TODO
 */
@RestController
@RequestMapping("/permission")
public class HelloController {

    @PermissionAnnotation(permission = true)
    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }

}

