package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity2.Coach;

public interface CoachMapper {
	@Insert("insert sys_coach(id,user_name) values(#{id},#{userName})")
	void insert(Coach u);
	
	@Update("update sys_coach set user_name = #{userName} where id=#{id} ")	
	void update(Coach u);		
	
	@Delete("delete from sys_coach where id=#{id} ")	
	void delete(@Param("id")Integer id);		
	
	
	@Select("select id,user_name from sys_coach where id=#{id} ")	
	Coach find(@Param("id")Integer id);		
	
	//注：方法名和要UserMapper.xml中的id一致	
	List<Coach> query(@Param("userName")String userName);		
	@Delete("delete from sys_coach")	
	void deleteAll();
}
