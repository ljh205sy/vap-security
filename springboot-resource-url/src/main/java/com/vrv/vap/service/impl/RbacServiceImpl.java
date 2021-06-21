package com.vrv.vap.service.impl;

import com.vrv.vap.service.RbacService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wh1107066
 * @date 2021/6/21 15:13
 */
@Component("rbacService")
public class RbacServiceImpl implements RbacService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        logger.info("uri:", request.getRequestURI());
        boolean hasPermission = false;
        logger.info(String.format("%s", principal));
        return hasPermission;
    }

}
