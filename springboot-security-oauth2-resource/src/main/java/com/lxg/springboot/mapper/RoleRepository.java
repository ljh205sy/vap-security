package com.lxg.springboot.mapper;

import com.lxg.springboot.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wh1107066
 */
@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
}
