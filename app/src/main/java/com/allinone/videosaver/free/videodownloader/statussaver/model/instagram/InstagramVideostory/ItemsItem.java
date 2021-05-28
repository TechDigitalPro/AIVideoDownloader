package com.allinone.videosaver.free.videodownloader.statussaver.model.instagram.InstagramVideostory;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ItemsItem{

	@SerializedName("video_duration")
	private double videoDuration;

	@SerializedName("code")
	private String code;

	@SerializedName("has_audio")
	private boolean hasAudio;

	@SerializedName("can_send_custom_emojis")
	private boolean canSendCustomEmojis;

	@SerializedName("reel_mentions")
	private List<ReelMentionsItem> reelMentions;

	@SerializedName("caption")
	private Object caption;

	@SerializedName("expiring_at")
	private int expiringAt;

	@SerializedName("is_reel_media")
	private boolean isReelMedia;

	@SerializedName("profile_grid_control_enabled")
	private boolean profileGridControlEnabled;

	@SerializedName("media_type")
	private int mediaType;

	@SerializedName("filter_type")
	private int filterType;

	@SerializedName("device_timestamp")
	private long deviceTimestamp;

	@SerializedName("deleted_reason")
	private int deletedReason;

	@SerializedName("original_height")
	private int originalHeight;

	@SerializedName("supports_reel_reactions")
	private boolean supportsReelReactions;

	@SerializedName("id")
	private String id;

	@SerializedName("video_versions")
	private List<VideoVersionsItem> videoVersions;

	@SerializedName("caption_position")
	private double captionPosition;

	@SerializedName("can_see_insights_as_brand")
	private boolean canSeeInsightsAsBrand;

	@SerializedName("integrity_review_decision")
	private String integrityReviewDecision;

	@SerializedName("original_width")
	private int originalWidth;

	@SerializedName("show_one_tap_fb_share_tooltip")
	private boolean showOneTapFbShareTooltip;

	@SerializedName("photo_of_you")
	private boolean photoOfYou;

	@SerializedName("organic_tracking_token")
	private String organicTrackingToken;

	@SerializedName("imported_taken_at")
	private int importedTakenAt;

	@SerializedName("sharing_friction_info")
	private SharingFrictionInfo sharingFrictionInfo;

	@SerializedName("client_cache_key")
	private String clientCacheKey;

	@SerializedName("can_reply")
	private boolean canReply;

	@SerializedName("taken_at")
	private int takenAt;

	@SerializedName("is_shop_the_look_eligible")
	private boolean isShopTheLookEligible;

	@SerializedName("caption_is_edited")
	private boolean captionIsEdited;

	@SerializedName("image_versions2")
	private ImageVersions2 imageVersions2;

	@SerializedName("pk")
	private long pk;

	@SerializedName("is_in_profile_grid")
	private boolean isInProfileGrid;

	@SerializedName("can_reshare")
	private boolean canReshare;

	@SerializedName("user")
	private User user;

	@SerializedName("story_static_models")
	private List<Object> storyStaticModels;

	public void setVideoDuration(double videoDuration){
		this.videoDuration = videoDuration;
	}

	public double getVideoDuration(){
		return videoDuration;
	}

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return code;
	}

	public void setHasAudio(boolean hasAudio){
		this.hasAudio = hasAudio;
	}

	public boolean isHasAudio(){
		return hasAudio;
	}

	public void setCanSendCustomEmojis(boolean canSendCustomEmojis){
		this.canSendCustomEmojis = canSendCustomEmojis;
	}

	public boolean isCanSendCustomEmojis(){
		return canSendCustomEmojis;
	}

	public void setReelMentions(List<ReelMentionsItem> reelMentions){
		this.reelMentions = reelMentions;
	}

	public List<ReelMentionsItem> getReelMentions(){
		return reelMentions;
	}

	public void setCaption(Object caption){
		this.caption = caption;
	}

	public Object getCaption(){
		return caption;
	}

	public void setExpiringAt(int expiringAt){
		this.expiringAt = expiringAt;
	}

	public int getExpiringAt(){
		return expiringAt;
	}

	public void setIsReelMedia(boolean isReelMedia){
		this.isReelMedia = isReelMedia;
	}

	public boolean isIsReelMedia(){
		return isReelMedia;
	}

	public void setProfileGridControlEnabled(boolean profileGridControlEnabled){
		this.profileGridControlEnabled = profileGridControlEnabled;
	}

	public boolean isProfileGridControlEnabled(){
		return profileGridControlEnabled;
	}

	public void setMediaType(int mediaType){
		this.mediaType = mediaType;
	}

	public int getMediaType(){
		return mediaType;
	}

	public void setFilterType(int filterType){
		this.filterType = filterType;
	}

	public int getFilterType(){
		return filterType;
	}

	public void setDeviceTimestamp(long deviceTimestamp){
		this.deviceTimestamp = deviceTimestamp;
	}

	public long getDeviceTimestamp(){
		return deviceTimestamp;
	}

	public void setDeletedReason(int deletedReason){
		this.deletedReason = deletedReason;
	}

	public int getDeletedReason(){
		return deletedReason;
	}

	public void setOriginalHeight(int originalHeight){
		this.originalHeight = originalHeight;
	}

	public int getOriginalHeight(){
		return originalHeight;
	}

	public void setSupportsReelReactions(boolean supportsReelReactions){
		this.supportsReelReactions = supportsReelReactions;
	}

	public boolean isSupportsReelReactions(){
		return supportsReelReactions;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setVideoVersions(List<VideoVersionsItem> videoVersions){
		this.videoVersions = videoVersions;
	}

	public List<VideoVersionsItem> getVideoVersions(){
		return videoVersions;
	}

	public void setCaptionPosition(double captionPosition){
		this.captionPosition = captionPosition;
	}

	public double getCaptionPosition(){
		return captionPosition;
	}

	public void setCanSeeInsightsAsBrand(boolean canSeeInsightsAsBrand){
		this.canSeeInsightsAsBrand = canSeeInsightsAsBrand;
	}

	public boolean isCanSeeInsightsAsBrand(){
		return canSeeInsightsAsBrand;
	}

	public void setIntegrityReviewDecision(String integrityReviewDecision){
		this.integrityReviewDecision = integrityReviewDecision;
	}

	public String getIntegrityReviewDecision(){
		return integrityReviewDecision;
	}

	public void setOriginalWidth(int originalWidth){
		this.originalWidth = originalWidth;
	}

	public int getOriginalWidth(){
		return originalWidth;
	}

	public void setShowOneTapFbShareTooltip(boolean showOneTapFbShareTooltip){
		this.showOneTapFbShareTooltip = showOneTapFbShareTooltip;
	}

	public boolean isShowOneTapFbShareTooltip(){
		return showOneTapFbShareTooltip;
	}

	public void setPhotoOfYou(boolean photoOfYou){
		this.photoOfYou = photoOfYou;
	}

	public boolean isPhotoOfYou(){
		return photoOfYou;
	}

	public void setOrganicTrackingToken(String organicTrackingToken){
		this.organicTrackingToken = organicTrackingToken;
	}

	public String getOrganicTrackingToken(){
		return organicTrackingToken;
	}

	public void setImportedTakenAt(int importedTakenAt){
		this.importedTakenAt = importedTakenAt;
	}

	public int getImportedTakenAt(){
		return importedTakenAt;
	}

	public void setSharingFrictionInfo(SharingFrictionInfo sharingFrictionInfo){
		this.sharingFrictionInfo = sharingFrictionInfo;
	}

	public SharingFrictionInfo getSharingFrictionInfo(){
		return sharingFrictionInfo;
	}

	public void setClientCacheKey(String clientCacheKey){
		this.clientCacheKey = clientCacheKey;
	}

	public String getClientCacheKey(){
		return clientCacheKey;
	}

	public void setCanReply(boolean canReply){
		this.canReply = canReply;
	}

	public boolean isCanReply(){
		return canReply;
	}

	public void setTakenAt(int takenAt){
		this.takenAt = takenAt;
	}

	public int getTakenAt(){
		return takenAt;
	}

	public void setIsShopTheLookEligible(boolean isShopTheLookEligible){
		this.isShopTheLookEligible = isShopTheLookEligible;
	}

	public boolean isIsShopTheLookEligible(){
		return isShopTheLookEligible;
	}

	public void setCaptionIsEdited(boolean captionIsEdited){
		this.captionIsEdited = captionIsEdited;
	}

	public boolean isCaptionIsEdited(){
		return captionIsEdited;
	}

	public void setImageVersions2(ImageVersions2 imageVersions2){
		this.imageVersions2 = imageVersions2;
	}

	public ImageVersions2 getImageVersions2(){
		return imageVersions2;
	}

	public void setPk(long pk){
		this.pk = pk;
	}

	public long getPk(){
		return pk;
	}

	public void setIsInProfileGrid(boolean isInProfileGrid){
		this.isInProfileGrid = isInProfileGrid;
	}

	public boolean isIsInProfileGrid(){
		return isInProfileGrid;
	}

	public void setCanReshare(boolean canReshare){
		this.canReshare = canReshare;
	}

	public boolean isCanReshare(){
		return canReshare;
	}

	public void setUser(User user){
		this.user = user;
	}

	public User getUser(){
		return user;
	}

	public void setStoryStaticModels(List<Object> storyStaticModels){
		this.storyStaticModels = storyStaticModels;
	}

	public List<Object> getStoryStaticModels(){
		return storyStaticModels;
	}

	@Override
 	public String toString(){
		return 
			"ItemsItem{" + 
			"video_duration = '" + videoDuration + '\'' + 
			",code = '" + code + '\'' + 
			",has_audio = '" + hasAudio + '\'' + 
			",can_send_custom_emojis = '" + canSendCustomEmojis + '\'' + 
			",reel_mentions = '" + reelMentions + '\'' + 
			",caption = '" + caption + '\'' + 
			",expiring_at = '" + expiringAt + '\'' + 
			",is_reel_media = '" + isReelMedia + '\'' + 
			",profile_grid_control_enabled = '" + profileGridControlEnabled + '\'' + 
			",media_type = '" + mediaType + '\'' + 
			",filter_type = '" + filterType + '\'' + 
			",device_timestamp = '" + deviceTimestamp + '\'' + 
			",deleted_reason = '" + deletedReason + '\'' + 
			",original_height = '" + originalHeight + '\'' + 
			",supports_reel_reactions = '" + supportsReelReactions + '\'' + 
			",id = '" + id + '\'' + 
			",video_versions = '" + videoVersions + '\'' + 
			",caption_position = '" + captionPosition + '\'' + 
			",can_see_insights_as_brand = '" + canSeeInsightsAsBrand + '\'' + 
			",integrity_review_decision = '" + integrityReviewDecision + '\'' + 
			",original_width = '" + originalWidth + '\'' + 
			",show_one_tap_fb_share_tooltip = '" + showOneTapFbShareTooltip + '\'' + 
			",photo_of_you = '" + photoOfYou + '\'' + 
			",organic_tracking_token = '" + organicTrackingToken + '\'' + 
			",imported_taken_at = '" + importedTakenAt + '\'' + 
			",sharing_friction_info = '" + sharingFrictionInfo + '\'' + 
			",client_cache_key = '" + clientCacheKey + '\'' + 
			",can_reply = '" + canReply + '\'' + 
			",taken_at = '" + takenAt + '\'' + 
			",is_shop_the_look_eligible = '" + isShopTheLookEligible + '\'' + 
			",caption_is_edited = '" + captionIsEdited + '\'' + 
			",image_versions2 = '" + imageVersions2 + '\'' + 
			",pk = '" + pk + '\'' + 
			",is_in_profile_grid = '" + isInProfileGrid + '\'' + 
			",can_reshare = '" + canReshare + '\'' + 
			",user = '" + user + '\'' + 
			",story_static_models = '" + storyStaticModels + '\'' + 
			"}";
		}
}