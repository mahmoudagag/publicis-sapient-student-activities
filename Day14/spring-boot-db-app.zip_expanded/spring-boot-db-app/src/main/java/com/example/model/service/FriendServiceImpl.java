package com.example.model.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exceptions.FriendNotFoundException;
import com.example.exceptions.ProfileNotFoundExceptions;
import com.example.model.beans.Friend;
import com.example.model.beans.Profile;
import com.example.model.dao.FriendRepository;
import com.example.model.dao.ProfileRepository;

@Service
public class FriendServiceImpl implements FriendService{

	@Autowired
	private FriendRepository friendDao;
	
	@Autowired
	private ProfileRepository profileDao;
	
//	private ProfileServiceImpl profileService;
	
	@Override
	public Friend addFriend(int profileIdRef, Friend friend) {
		// TODO Auto-generated method stub
		// to make it simple we will not enter id that doesnt exist but you sould check
		friend.setProfileIdRef(profileIdRef);
		return friendDao.save(friend);
		// you should access profile of profileIdRef and add Friend to friend list
		//Instead we added it in profileService when get Friends is called
		//		Profile profile = profileService.getProfile(profileIdRef)
		//		List<Friend> friends = profile.getFriends()
		//		Profile.addFriend(friend)
		
	}
	
	@Override
	public List<Friend> deleteFriend(int profileIdRef, Friend friend) throws ProfileNotFoundExceptions {
		// TODO Auto-generated method stub
		friend.setProfileIdRef(profileIdRef);
		friendDao.delete(friend);
		Profile profile = profileDao.findById(profileIdRef).orElse(null);
		
		if(profile == null) {
			throw new ProfileNotFoundExceptions("Profile with an id: "+ profileIdRef + " is not found");
		}
		List<Friend> friends = friendDao.getFriendsFromProfile(profileIdRef);
		profile.setFriends(friends);
		return profile.getFriends();
	}

	@Transactional
	@Override
	public List<Friend> modifyFriendNumber(int profileId, int friendId, long phoneNumber) throws FriendNotFoundException {
		// TODO Auto-generated method stub
		Friend friend = friendDao.findById(friendId).orElse(null);
		if(friend == null) {
			throw new FriendNotFoundException("Friend with id " + friendId + " not found");
		}
		friendDao.updateFriendPhoneNumber(friendId, phoneNumber);
		List<Friend> friends = friendDao.getFriendsFromProfile(profileId);
		return friends;
	}
	
	

}
