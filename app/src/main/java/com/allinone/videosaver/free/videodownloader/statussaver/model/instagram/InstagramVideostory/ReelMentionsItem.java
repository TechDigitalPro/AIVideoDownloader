package com.allinone.videosaver.free.videodownloader.statussaver.model.instagram.InstagramVideostory;

import com.google.gson.annotations.SerializedName;

public class ReelMentionsItem{

	@SerializedName("display_type")
	private String displayType;

	@SerializedName("rotation")
	private double rotation;

	@SerializedName("x")
	private double X;

	@SerializedName("width")
	private double width;

	@SerializedName("is_hidden")
	private int isHidden;

	@SerializedName("y")
	private double Y;

	@SerializedName("is_sticker")
	private int isSticker;

	@SerializedName("z")
	private int Z;

	@SerializedName("is_fb_sticker")
	private int isFbSticker;

	@SerializedName("user")
	private User user;

	@SerializedName("height")
	private double height;

	@SerializedName("is_pinned")
	private int isPinned;

	public void setDisplayType(String displayType){
		this.displayType = displayType;
	}

	public String getDisplayType(){
		return displayType;
	}

	public void setRotation(double rotation){
		this.rotation = rotation;
	}

	public double getRotation(){
		return rotation;
	}

	public void setX(double X){
		this.X = X;
	}

	public double getX(){
		return X;
	}

	public void setWidth(double width){
		this.width = width;
	}

	public double getWidth(){
		return width;
	}

	public void setIsHidden(int isHidden){
		this.isHidden = isHidden;
	}

	public int getIsHidden(){
		return isHidden;
	}

	public void setY(double Y){
		this.Y = Y;
	}

	public double getY(){
		return Y;
	}

	public void setIsSticker(int isSticker){
		this.isSticker = isSticker;
	}

	public int getIsSticker(){
		return isSticker;
	}

	public void setZ(int Z){
		this.Z = Z;
	}

	public int getZ(){
		return Z;
	}

	public void setIsFbSticker(int isFbSticker){
		this.isFbSticker = isFbSticker;
	}

	public int getIsFbSticker(){
		return isFbSticker;
	}

	public void setUser(User user){
		this.user = user;
	}

	public User getUser(){
		return user;
	}

	public void setHeight(double height){
		this.height = height;
	}

	public double getHeight(){
		return height;
	}

	public void setIsPinned(int isPinned){
		this.isPinned = isPinned;
	}

	public int getIsPinned(){
		return isPinned;
	}

	@Override
 	public String toString(){
		return 
			"ReelMentionsItem{" + 
			"display_type = '" + displayType + '\'' + 
			",rotation = '" + rotation + '\'' + 
			",x = '" + X + '\'' + 
			",width = '" + width + '\'' + 
			",is_hidden = '" + isHidden + '\'' + 
			",y = '" + Y + '\'' + 
			",is_sticker = '" + isSticker + '\'' + 
			",z = '" + Z + '\'' + 
			",is_fb_sticker = '" + isFbSticker + '\'' + 
			",user = '" + user + '\'' + 
			",height = '" + height + '\'' + 
			",is_pinned = '" + isPinned + '\'' + 
			"}";
		}
}