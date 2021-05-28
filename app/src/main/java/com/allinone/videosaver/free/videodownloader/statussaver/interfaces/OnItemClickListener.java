package com.allinone.videosaver.free.videodownloader.statussaver.interfaces;

import android.view.View;

public interface OnItemClickListener<T> {
    void onItemClick(View v, int position, T t);
}
