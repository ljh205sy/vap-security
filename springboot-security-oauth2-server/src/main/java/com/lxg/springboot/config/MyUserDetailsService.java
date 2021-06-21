package com.lxg.springboot.config;

import com.lxg.springboot.entity.Role;
import com.lxg.springboot.entity.User;
import com.lxg.springboot.service.UserRoleService;
import com.lxg.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * @author wh1107066
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;
    
    /**
     * 根据用户名获取登录用户信息
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
        User user = userService.findByUsername(username);

        if(user == null){
             throw new UsernameNotFoundException("用户名："+ username + "不存在！");
        }
        Collection<SimpleGrantedAuthority> collection = new HashSet<SimpleGrantedAuthority>();
        // 通过用户级联查询用户该有的角色信息，通过jpa查询
        Iterator<Role> iterator =  user.getList().iterator();
        while (iterator.hasNext()){
            collection.add(new SimpleGrantedAuthority(iterator.next().getRole_name()));
        }
        collection.add(new SimpleGrantedAuthority("ROLE_role1"));
        collection.add(new SimpleGrantedAuthority("ROLE_role2"));
        return new org.springframework.security.core.userdetails.User(username,user.getPassword(),collection);
    }
}
