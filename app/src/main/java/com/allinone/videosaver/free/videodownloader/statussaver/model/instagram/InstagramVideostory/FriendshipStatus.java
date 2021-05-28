package com.allinone.videosaver.free.videodownloader.statussaver.model.instagram.InstagramVideostory;

import com.google.gson.annotations.SerializedName;

public class FriendshipStatus{

	@SerializedName("is_private")
	private boolean isPrivate;

	@SerializedName("followed_by")
	private boolean followedBy;

	@SerializedName("muting")
	private boolean muting;

	@SerializedName("incoming_request")
	private boolean incomingRequest;

	@SerializedName("blocking")
	private boolean blocking;

	@SerializedName("is_restricted")
	private boolean isRestricted;

	@SerializedName("following")
	private boolean following;

	@SerializedName("outgoing_request")
	private boolean outgoingRequest;

	@SerializedName("is_bestie")
	private boolean isBestie;

	public void setIsPrivate(boolean isPrivate){
		this.isPrivate = isPrivate;
	}

	public boolean isIsPrivate(){
		return isPrivate;
	}

	public void setFollowedBy(boolean followedBy){
		this.followedBy = followedBy;
	}

	public boolean isFollowedBy(){
		return followedBy;
	}

	public void setMuting(boolean muting){
		this.muting = muting;
	}

	public boolean isMuting(){
		return muting;
	}

	public void setIncomingRequest(boolean incomingRequest){
		this.incomingRequest = incomingRequest;
	}

	public boolean isIncomingRequest(){
		return incomingRequest;
	}

	public void setBlocking(boolean blocking){
		this.blocking = blocking;
	}

	public boolean isBlocking(){
		return blocking;
	}

	public void setIsRestricted(boolean isRestricted){
		this.isRestricted = isRestricted;
	}

	public boolean isIsRestricted(){
		return isRestricted;
	}

	public void setFollowing(boolean following){
		this.following = following;
	}

	public boolean isFollowing(){
		return following;
	}

	public void setOutgoingRequest(boolean outgoingRequest){
		this.outgoingRequest = outgoingRequest;
	}

	public boolean isOutgoingRequest(){
		return outgoingRequest;
	}

	public void setIsBestie(boolean isBestie){
		this.isBestie = isBestie;
	}

	public boolean isIsBestie(){
		return isBestie;
	}

	@Override
 	public String toString(){
		return 
			"FriendshipStatus{" + 
			"is_private = '" + isPrivate + '\'' + 
			",followed_by = '" + followedBy + '\'' + 
			",muting = '" + muting + '\'' + 
			",incoming_request = '" + incomingRequest + '\'' + 
			",blocking = '" + blocking + '\'' + 
			",is_restricted = '" + isRestricted + '\'' + 
			",following = '" + following + '\'' + 
			",outgoing_request = '" + outgoingRequest + '\'' + 
			",is_bestie = '" + isBestie + '\'' + 
			"}";
		}
}