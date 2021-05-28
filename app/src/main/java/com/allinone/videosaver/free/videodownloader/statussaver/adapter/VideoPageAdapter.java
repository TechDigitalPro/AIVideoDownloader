package com.allinone.videosaver.free.videodownloader.statussaver.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.allinone.videosaver.free.videodownloader.statussaver.fragments.VideoPlayFragment;

import java.util.ArrayList;


public class VideoPageAdapter extends FragmentPagerAdapter {
    private ArrayList<String> arrVideo = new ArrayList();

    public VideoPageAdapter(FragmentManager fragmentManager, ArrayList<String> arrayList) {
        super(fragmentManager);
        this.arrVideo = arrayList;
    }

    public int getCount() {
        return this.arrVideo.size();
    }

    public Fragment getItem(int i) {
        return  VideoPlayFragment.createInstance((String) this.arrVideo.get(i));
    }
}
