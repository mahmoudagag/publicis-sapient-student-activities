package com.example.model.service;

import java.util.List;

import com.example.exceptions.ProfileNotFoundExceptions;
import com.example.model.beans.Profile;

// This is an interface for businness layer, it needs to be
//implemmented by dev

public interface ProfileService {
	
	public Profile storeProfile(Profile profile);
	
	public List<Profile> fetchProfiles();
	
	public Profile getProfile(int Id) throws ProfileNotFoundExceptions;

	public Profile updatePhoneNumber(int Id, long phoneNumber) throws ProfileNotFoundExceptions;
}
