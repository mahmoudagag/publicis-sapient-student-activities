package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.exceptions.FriendNotFoundException;
import com.example.exceptions.ProfileNotFoundExceptions;
import com.example.model.beans.Friend;
import com.example.model.beans.Profile;
import com.example.model.service.FriendService;
import com.example.model.service.ProfileService;
import com.example.model.service.ProfileServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/profile")
public class profileController {
	
	@Autowired
	private ProfileService profileService;
	@Autowired
	private FriendService friendService;
	
	@PostMapping
	public ResponseEntity<Object> store(@RequestBody Profile profile){
		Profile storedProfile = profileService.storeProfile(profile);
		return ResponseEntity.status(HttpStatus.CREATED).body(storedProfile);
	}
	
	@GetMapping
	public ResponseEntity<Object> getProfiles(){
		List<Profile> storedProfile = profileService.fetchProfiles();
		return ResponseEntity.status(HttpStatus.CREATED).body(storedProfile);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getProfiles(@PathVariable("id") int id){
		try {
			Profile profile = profileService.getProfile(id);
			return ResponseEntity.status(HttpStatus.CREATED).body(profile);
		}catch(ProfileNotFoundExceptions e) {
			Map<String,String> map = new HashMap<String,String>();
			map.put("error", e.getMessage());
			map.put("status", "404");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
		}
	}
	
	@PostMapping("/{profileId}")
	public ResponseEntity<Object> addFriend(@RequestBody Friend friend,
			@PathVariable("profileId") int id){
		Friend createdFriend = friendService.addFriend(id, friend);
		Map<String, String> map = new HashMap<String,String>();
		map.put("message", "Added " +createdFriend.getName());
		map.put("profileId", "Added to profile id " + createdFriend.getProfileIdRef());
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}
	
	@PutMapping("/{profileId}/{phoneNumber}")
	public ResponseEntity<Object> updatePhoneNumber(@PathVariable("profileId") int id,
			@PathVariable("phoneNumber") long phone){
		Map<String,String> map = new HashMap<String,String>();
		try {
			Profile profile = profileService.updatePhoneNumber(id, phone);
			map.put("message", "Change profile with id: " + id + " to have phoneNumebr" + profile.getPhone());
			return ResponseEntity.status(HttpStatus.OK).body(map);
		}catch(ProfileNotFoundExceptions e) {
			map.put("error", e.getMessage());
			map.put("status", "404");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
		}
	}
	@DeleteMapping("/{Id}")
	public ResponseEntity<Object> deleteFriend(@PathVariable("Id") int id, @RequestBody Friend friend) throws ProfileNotFoundExceptions{
		List<Friend> friends = friendService.deleteFriend(id, friend);
		return ResponseEntity.status(HttpStatus.OK).body(friends);
	}
	
	@PutMapping("friend/{profileId}/{friendId}")
	public ResponseEntity<Object> modifyFriendPhoneNumber(@PathVariable("profileId") int profileId,
			@PathVariable("friendId") int friendId,
			@RequestBody long phoneNumber){
		try {
			List<Friend> friends = friendService.modifyFriendNumber(profileId,friendId,phoneNumber);
			return ResponseEntity.status(HttpStatus.OK).body(friends);
		}catch(FriendNotFoundException e) {
			Map<String, String> map = new HashMap<String,String>();
			map.put("error", e.getMessage());
			map.put("status", "404");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
		}
	}
}

