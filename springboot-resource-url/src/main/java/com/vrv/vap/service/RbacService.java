package com.vrv.vap.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wh1107066
 * @date 2021/6/21 15:13
 */
public interface RbacService {
    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
