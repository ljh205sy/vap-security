package com.lxg.springboot.mapper;

import com.lxg.springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	User findByUsername(String name);

	Optional<User> findById(Integer id);
}
