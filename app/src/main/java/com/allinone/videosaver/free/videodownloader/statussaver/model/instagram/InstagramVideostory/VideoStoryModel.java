package com.allinone.videosaver.free.videodownloader.statussaver.model.instagram.InstagramVideostory;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class VideoStoryModel{

	@SerializedName("media_count")
	private int mediaCount;

	@SerializedName("media_ids")
	private List<Long> mediaIds;

	@SerializedName("is_sensitive_vertical_ad")
	private boolean isSensitiveVerticalAd;

	@SerializedName("seen")
	private int seen;

	@SerializedName("can_gif_quick_reply")
	private boolean canGifQuickReply;

	@SerializedName("expiring_at")
	private int expiringAt;

	@SerializedName("can_reply")
	private boolean canReply;

	@SerializedName("prefetch_count")
	private int prefetchCount;

	@SerializedName("id")
	private long id;

	@SerializedName("latest_reel_media")
	private int latestReelMedia;

	@SerializedName("has_besties_media")
	private boolean hasBestiesMedia;

	@SerializedName("can_reshare")
	private boolean canReshare;

	@SerializedName("reel_type")
	private String reelType;

	@SerializedName("user")
	private User user;

	@SerializedName("items")
	private List<ItemsItem> items;

	@SerializedName("status")
	private String status;

	public void setMediaCount(int mediaCount){
		this.mediaCount = mediaCount;
	}

	public int getMediaCount(){
		return mediaCount;
	}

	public void setMediaIds(List<Long> mediaIds){
		this.mediaIds = mediaIds;
	}

	public List<Long> getMediaIds(){
		return mediaIds;
	}

	public void setIsSensitiveVerticalAd(boolean isSensitiveVerticalAd){
		this.isSensitiveVerticalAd = isSensitiveVerticalAd;
	}

	public boolean isIsSensitiveVerticalAd(){
		return isSensitiveVerticalAd;
	}

	public void setSeen(int seen){
		this.seen = seen;
	}

	public int getSeen(){
		return seen;
	}

	public void setCanGifQuickReply(boolean canGifQuickReply){
		this.canGifQuickReply = canGifQuickReply;
	}

	public boolean isCanGifQuickReply(){
		return canGifQuickReply;
	}

	public void setExpiringAt(int expiringAt){
		this.expiringAt = expiringAt;
	}

	public int getExpiringAt(){
		return expiringAt;
	}

	public void setCanReply(boolean canReply){
		this.canReply = canReply;
	}

	public boolean isCanReply(){
		return canReply;
	}

	public void setPrefetchCount(int prefetchCount){
		this.prefetchCount = prefetchCount;
	}

	public int getPrefetchCount(){
		return prefetchCount;
	}

	public void setId(long id){
		this.id = id;
	}

	public long getId(){
		return id;
	}

	public void setLatestReelMedia(int latestReelMedia){
		this.latestReelMedia = latestReelMedia;
	}

	public int getLatestReelMedia(){
		return latestReelMedia;
	}

	public void setHasBestiesMedia(boolean hasBestiesMedia){
		this.hasBestiesMedia = hasBestiesMedia;
	}

	public boolean isHasBestiesMedia(){
		return hasBestiesMedia;
	}

	public void setCanReshare(boolean canReshare){
		this.canReshare = canReshare;
	}

	public boolean isCanReshare(){
		return canReshare;
	}

	public void setReelType(String reelType){
		this.reelType = reelType;
	}

	public String getReelType(){
		return reelType;
	}

	public void setUser(User user){
		this.user = user;
	}

	public User getUser(){
		return user;
	}

	public void setItems(List<ItemsItem> items){
		this.items = items;
	}

	public List<ItemsItem> getItems(){
		return items;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"VideoStoryModel{" + 
			"media_count = '" + mediaCount + '\'' + 
			",media_ids = '" + mediaIds + '\'' + 
			",is_sensitive_vertical_ad = '" + isSensitiveVerticalAd + '\'' + 
			",seen = '" + seen + '\'' + 
			",can_gif_quick_reply = '" + canGifQuickReply + '\'' + 
			",expiring_at = '" + expiringAt + '\'' + 
			",can_reply = '" + canReply + '\'' + 
			",prefetch_count = '" + prefetchCount + '\'' + 
			",id = '" + id + '\'' + 
			",latest_reel_media = '" + latestReelMedia + '\'' + 
			",has_besties_media = '" + hasBestiesMedia + '\'' + 
			",can_reshare = '" + canReshare + '\'' + 
			",reel_type = '" + reelType + '\'' + 
			",user = '" + user + '\'' + 
			",items = '" + items + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}