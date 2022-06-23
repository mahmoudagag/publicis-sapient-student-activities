package com.example.model.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.model.beans.Profile;

public interface ProfileRepository extends JpaRepository <Profile, Integer>{
	
	@Modifying
	@Query("update Profile p set p.phone = ?1 where p.profileId = ?2")
	public void updatePhoneNumber(long phoneNumeber, int id);
}
