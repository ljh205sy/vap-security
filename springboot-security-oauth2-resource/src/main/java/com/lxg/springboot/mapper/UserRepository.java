package com.lxg.springboot.mapper;

import com.lxg.springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @author wh1107066
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUsername(String name);

	public User findById(Integer id);
}
