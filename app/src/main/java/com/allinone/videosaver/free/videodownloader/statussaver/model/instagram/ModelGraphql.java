package com.allinone.videosaver.free.videodownloader.statussaver.model.instagram;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ModelGraphql implements Serializable {

    @SerializedName("shortcode_media")
    private ModelShortcodeMedia shortcode_media;

    public ModelShortcodeMedia getShortcode_media() {
        return shortcode_media;
    }

    public void setShortcode_media(ModelShortcodeMedia shortcode_media) {
        this.shortcode_media = shortcode_media;
    }
}
