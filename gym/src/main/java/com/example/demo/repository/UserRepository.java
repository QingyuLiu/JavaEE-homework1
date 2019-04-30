package com.example.demo.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
	  // User findByUsername(String userName);
	/**
	 * 根据用户名和密码查询数据
	 */
	
	
	public User findByUsernameAndPassword(String username,String password);
	
	
	public User findByUsername( String username);
	
	public User findUserById(Integer id);
	
}
