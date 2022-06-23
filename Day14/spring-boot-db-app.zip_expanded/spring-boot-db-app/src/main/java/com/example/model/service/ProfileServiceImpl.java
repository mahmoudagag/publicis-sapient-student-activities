package com.example.model.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exceptions.ProfileNotFoundExceptions;
import com.example.model.beans.Friend;
import com.example.model.beans.Profile;
import com.example.model.dao.FriendRepository;
import com.example.model.dao.ProfileRepository;

@Service
public class ProfileServiceImpl implements ProfileService{

	@Autowired
	private ProfileRepository profileDao;
	
	@Autowired
	private FriendRepository friendDao;
	
	@Transactional
	@Override
	public Profile storeProfile(Profile profile) {
		Profile createdProfile = profileDao.save(profile);
		return createdProfile;
	}

	@Override
	public List<Profile> fetchProfiles() {
		// TODO Auto-generated method stub
		List<Profile> list  = profileDao.findAll();
		return list;
	}

	@Override
	public Profile getProfile(int Id) throws ProfileNotFoundExceptions{
		// TODO Auto-generated method stub
		Profile profile = profileDao.findById(Id).orElse(null);
		
		if(profile == null) {
			throw new ProfileNotFoundExceptions("Profile with an id: "+ Id + " is not found");
		}
		List<Friend> friends = friendDao.getFriendsFromProfile(Id);
		profile.setFriends(friends);
		return profile;
	}
	@Transactional
	@Override
	public Profile updatePhoneNumber(int Id,long phoneNumber) throws ProfileNotFoundExceptions{
		
		Profile profile = profileDao.findById(Id).orElse(null);
		if(profile == null) {
			throw new ProfileNotFoundExceptions("Profile with an id: "+ Id + " is not found");
		}
		profileDao.updatePhoneNumber(phoneNumber, Id);
		profile.setPhone(phoneNumber);
		return profile;
		
	}
	
}
