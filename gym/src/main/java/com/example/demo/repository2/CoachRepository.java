package com.example.demo.repository2;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity2.Coach;

public interface CoachRepository extends JpaRepository<Coach, Integer> {
	
	public Coach findCoachById(Integer id);
}
