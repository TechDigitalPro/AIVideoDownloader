package com.allinone.videosaver.free.videodownloader.statussaver.model.instagram.InstagramVideostory;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class User{

	@SerializedName("is_private")
	private boolean isPrivate;

	@SerializedName("account_badges")
	private List<Object> accountBadges;

	@SerializedName("full_name")
	private String fullName;

	@SerializedName("is_favorite")
	private boolean isFavorite;

	@SerializedName("profile_pic_id")
	private String profilePicId;

	@SerializedName("is_unpublished")
	private boolean isUnpublished;

	@SerializedName("pk")
	private long pk;

	@SerializedName("has_anonymous_profile_picture")
	private boolean hasAnonymousProfilePicture;

	@SerializedName("profile_pic_url")
	private String profilePicUrl;

	@SerializedName("is_verified")
	private boolean isVerified;

	@SerializedName("username")
	private String username;

	public void setIsPrivate(boolean isPrivate){
		this.isPrivate = isPrivate;
	}

	public boolean isIsPrivate(){
		return isPrivate;
	}

	public void setAccountBadges(List<Object> accountBadges){
		this.accountBadges = accountBadges;
	}

	public List<Object> getAccountBadges(){
		return accountBadges;
	}

	public void setFullName(String fullName){
		this.fullName = fullName;
	}

	public String getFullName(){
		return fullName;
	}

	public void setIsFavorite(boolean isFavorite){
		this.isFavorite = isFavorite;
	}

	public boolean isIsFavorite(){
		return isFavorite;
	}

	public void setProfilePicId(String profilePicId){
		this.profilePicId = profilePicId;
	}

	public String getProfilePicId(){
		return profilePicId;
	}

	public void setIsUnpublished(boolean isUnpublished){
		this.isUnpublished = isUnpublished;
	}

	public boolean isIsUnpublished(){
		return isUnpublished;
	}

	public void setPk(long pk){
		this.pk = pk;
	}

	public long getPk(){
		return pk;
	}

	public void setHasAnonymousProfilePicture(boolean hasAnonymousProfilePicture){
		this.hasAnonymousProfilePicture = hasAnonymousProfilePicture;
	}

	public boolean isHasAnonymousProfilePicture(){
		return hasAnonymousProfilePicture;
	}

	public void setProfilePicUrl(String profilePicUrl){
		this.profilePicUrl = profilePicUrl;
	}

	public String getProfilePicUrl(){
		return profilePicUrl;
	}

	public void setIsVerified(boolean isVerified){
		this.isVerified = isVerified;
	}

	public boolean isIsVerified(){
		return isVerified;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	@Override
 	public String toString(){
		return 
			"User{" + 
			"is_private = '" + isPrivate + '\'' + 
			",account_badges = '" + accountBadges + '\'' + 
			",full_name = '" + fullName + '\'' + 
			",is_favorite = '" + isFavorite + '\'' + 
			",profile_pic_id = '" + profilePicId + '\'' + 
			",is_unpublished = '" + isUnpublished + '\'' + 
			",pk = '" + pk + '\'' + 
			",has_anonymous_profile_picture = '" + hasAnonymousProfilePicture + '\'' + 
			",profile_pic_url = '" + profilePicUrl + '\'' + 
			",is_verified = '" + isVerified + '\'' + 
			",username = '" + username + '\'' + 
			"}";
		}
}