package com.example.model.service;

import java.util.List;

import com.example.exceptions.FriendNotFoundException;
import com.example.exceptions.ProfileNotFoundExceptions;
import com.example.model.beans.Friend;

public interface FriendService {
	
	public Friend addFriend(int profileIdRef,Friend friend);
	
	public List<Friend> deleteFriend(int profileIdRef,Friend friend) throws ProfileNotFoundExceptions;

	public List<Friend> modifyFriendNumber(int profileId, int friendId, long phoneNumber) throws FriendNotFoundException;
}
