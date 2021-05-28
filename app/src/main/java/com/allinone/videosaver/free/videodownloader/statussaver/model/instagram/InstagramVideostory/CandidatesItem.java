package com.allinone.videosaver.free.videodownloader.statussaver.model.instagram.InstagramVideostory;

import com.google.gson.annotations.SerializedName;

public class CandidatesItem{

	@SerializedName("scans_profile")
	private String scansProfile;

	@SerializedName("width")
	private int width;

	@SerializedName("url")
	private String url;

	@SerializedName("height")
	private int height;

	public void setScansProfile(String scansProfile){
		this.scansProfile = scansProfile;
	}

	public String getScansProfile(){
		return scansProfile;
	}

	public void setWidth(int width){
		this.width = width;
	}

	public int getWidth(){
		return width;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	public void setHeight(int height){
		this.height = height;
	}

	public int getHeight(){
		return height;
	}

	@Override
 	public String toString(){
		return 
			"CandidatesItem{" + 
			"scans_profile = '" + scansProfile + '\'' + 
			",width = '" + width + '\'' + 
			",url = '" + url + '\'' + 
			",height = '" + height + '\'' + 
			"}";
		}
}