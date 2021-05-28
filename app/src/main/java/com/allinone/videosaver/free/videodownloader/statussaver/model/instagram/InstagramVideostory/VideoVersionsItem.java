package com.allinone.videosaver.free.videodownloader.statussaver.model.instagram.InstagramVideostory;

import com.google.gson.annotations.SerializedName;

public class VideoVersionsItem{

	@SerializedName("width")
	private int width;

	@SerializedName("id")
	private String id;

	@SerializedName("type")
	private int type;

	@SerializedName("url")
	private String url;

	@SerializedName("height")
	private int height;

	public void setWidth(int width){
		this.width = width;
	}

	public int getWidth(){
		return width;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setType(int type){
		this.type = type;
	}

	public int getType(){
		return type;
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
			"VideoVersionsItem{" + 
			"width = '" + width + '\'' + 
			",id = '" + id + '\'' + 
			",type = '" + type + '\'' + 
			",url = '" + url + '\'' + 
			",height = '" + height + '\'' + 
			"}";
		}
}