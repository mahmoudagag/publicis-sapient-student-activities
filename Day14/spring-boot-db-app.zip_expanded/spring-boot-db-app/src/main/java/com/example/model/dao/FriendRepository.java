package com.example.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.model.beans.Friend;

public interface FriendRepository extends JpaRepository<Friend, Integer>{
	
	
	// make custom query
	@Query("select f from Friend f where profileIdRef = ?1")
	public List<Friend> getFriendsFromProfile(int profileIdRef);
	
	@Query("update Friend f set f.phone = ?2 where f.id = ?1")
	public void updateFriendPhoneNumber(int id, long phone );
}
