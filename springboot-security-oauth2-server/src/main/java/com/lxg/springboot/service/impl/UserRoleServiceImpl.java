package com.lxg.springboot.service.impl;

import com.lxg.springboot.mapper.UserRoleRepository;
import com.lxg.springboot.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wh1107066
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;
    @Override
    public List<String> findRoles(int uid) {
        List<String> list = userRoleRepository.findRoleName(uid);
        return list;
    }
}
