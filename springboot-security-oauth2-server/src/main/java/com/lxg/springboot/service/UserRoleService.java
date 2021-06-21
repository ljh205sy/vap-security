package com.lxg.springboot.service;

import java.util.List;


/**
 * @author wh1107066
 */
public interface UserRoleService {

    List<String> findRoles(int uid);
}
