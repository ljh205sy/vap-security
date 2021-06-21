package com.vrv.vap.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wh1107066
 */
@RestController
public class HelloWorldRestController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @PreAuthorize("hasRole('role1')")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public List<String> listAllUsers() {
        List<String> list = new ArrayList<>();
        list.add("role1");
        return list;
    }
    @PreAuthorize("hasRole('xxx')")
    @RequestMapping(value = "/nopermision", method = RequestMethod.GET)
    @ResponseBody
    public String nopermision() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication信息: {}", authentication);
        return "permision";
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public List<String> hello() {
        logger.info("-------------方法体-------------");
        List<String> list = new ArrayList<>();
        list.add("role1");
        return list;
    }
    
    @GetMapping("/getCurUser")
    public Principal user(Principal user){
        return user;
    }

  
}