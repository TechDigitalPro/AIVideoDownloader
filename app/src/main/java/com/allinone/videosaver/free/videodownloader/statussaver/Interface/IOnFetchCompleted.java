package com.allinone.videosaver.free.videodownloader.statussaver.Interface;

import com.allinone.videosaver.free.videodownloader.statussaver.model.VideoHistoryModel;

import java.util.List;

public interface IOnFetchCompleted {
    void onError(int i);

    void onFetchCompleted(List<VideoHistoryModel> list);
}
