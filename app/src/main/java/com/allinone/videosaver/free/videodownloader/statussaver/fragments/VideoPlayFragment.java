package com.allinone.videosaver.free.videodownloader.statussaver.fragments;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;

import com.allinone.videosaver.free.videodownloader.statussaver.Adservices.UnityAd;
import com.bumptech.glide.Glide;

import com.allinone.videosaver.free.videodownloader.statussaver.activity.PlayvideosActivity;
import videodownload.com.newmusically.R;

public class VideoPlayFragment extends Fragment {
    public static String extraVideourl = "videoUrl";
    VideoView videoView;
    ImageView imgpreview;
    ImageView imageplay;
    ImageView fullscreen;
    private String databaseContents;
    TextView txtstart;
    TextView txtend;
    private Runnable onEverySecond = new Runnable() {
        public void run() {
            if (VideoPlayFragment.this.seekbar != null) {
                TextView textView;
                StringBuilder stringBuilder;
                String str;
                VideoPlayFragment.this.seekbar.setProgress(VideoPlayFragment.this.videoView.getCurrentPosition());
                int currentPosition = VideoPlayFragment.this.videoView.getCurrentPosition() / 1000;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(currentPosition);
                stringBuilder2.append("");
                if (stringBuilder2.toString().length() > 1) {
                    textView = VideoPlayFragment.this.txtstart;
                    stringBuilder = new StringBuilder();
                    str = "00:";
                } else {
                    textView = VideoPlayFragment.this.txtstart;
                    stringBuilder = new StringBuilder();
                    str = "00:0";
                }
                stringBuilder.append(str);
                stringBuilder.append(currentPosition);
                stringBuilder.append("");
                textView.setText(stringBuilder.toString());
            }
            if (VideoPlayFragment.this.videoView.isPlaying()) {
                VideoPlayFragment.this.seekbar.postDelayed(VideoPlayFragment.this.onEverySecond, 300);
            }
        }
    };
    private SeekBar seekbar;

    public static VideoPlayFragment createInstance(String str) {
        Bundle bundle = new Bundle();
        bundle.putString(extraVideourl, str);
        VideoPlayFragment videoPlayFragment = new VideoPlayFragment();
        videoPlayFragment.setArguments(bundle);
        return videoPlayFragment;
    }

    public VideoPlayFragment() {
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.databaseContents = getArguments().getString(extraVideourl);
        View inflate = layoutInflater.inflate(R.layout.fragment_video_play, viewGroup, false);

        this.videoView = (VideoView) inflate.findViewById(R.id.vdo_ContentVideo);
        this.imgpreview = (ImageView) inflate.findViewById(R.id.imgpreview);
        this.imageplay = (ImageView) inflate.findViewById(R.id.imageplay);
        this.fullscreen = (ImageView) inflate.findViewById(R.id.fullscreen);
        this.seekbar = (SeekBar) inflate.findViewById(R.id.seekBar);
        this.txtstart = (TextView) inflate.findViewById(R.id.txtstart);
        this.txtend = (TextView) inflate.findViewById(R.id.txtend);
        this.fullscreen.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ((PlayvideosActivity) VideoPlayFragment.this.getActivity()).fullScreenvideo();
            }
        });
        this.imageplay.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (VideoPlayFragment.this.videoView.isPlaying()) {
                    VideoPlayFragment.this.imageplay.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    VideoPlayFragment.this.videoView.pause();
                    return;
                }
                UnityAd.getInstance(getContext()).showinter();
                VideoPlayFragment.this.imageplay.setImageResource(R.drawable.ic_pause_black_24dp);
                VideoPlayFragment.this.imgpreview.setVisibility(View.GONE);
                VideoPlayFragment.this.videoView.start();
                VideoPlayFragment.this.onEverySecond.run();
            }
        });
        if (this.videoView != null) {
            Uri parse = Uri.parse(this.databaseContents);
            MediaController mediaController = new MediaController(getActivity());
            mediaController.setAnchorView(this.videoView);
            mediaController.setMediaPlayer(this.videoView);
            this.videoView.setVideoURI(parse);
            this.videoView.requestFocus();
            this.videoView.setOnPreparedListener(new OnPreparedListener() {
                public void onPrepared(MediaPlayer mediaPlayer) {
                    VideoPlayFragment.this.videoView.seekTo(0);
                    VideoPlayFragment.this.seekbar.setMax(VideoPlayFragment.this.videoView.getDuration());
                    int duration = (VideoPlayFragment.this.videoView.getDuration() - VideoPlayFragment.this.videoView.getCurrentPosition()) / 1000;
                    TextView textView = VideoPlayFragment.this.txtend;
                    String durations = "00:" + duration + "";
                    textView.setText(durations);
                    Glide.with(VideoPlayFragment.this.getContext()).load(VideoPlayFragment.this.databaseContents).into(VideoPlayFragment.this.imgpreview);
                }
            });
            this.videoView.setOnCompletionListener(new OnCompletionListener() {
                public void onCompletion(MediaPlayer mediaPlayer) {
                    VideoPlayFragment.this.videoView.seekTo(0);
                    VideoPlayFragment.this.txtstart.setText("00");
                    VideoPlayFragment.this.imageplay.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                }
            });
        }
        this.seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (z) {
                    VideoPlayFragment.this.videoView.seekTo(i);
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        return inflate;
    }

    public void onResume() {
        super.onResume();
    }

    public void setUserVisibleHint(boolean z) {
        super.setUserVisibleHint(z);
        if (!z && this.videoView != null && this.videoView.isPlaying()) {
            this.imageplay.setImageResource(R.drawable.ic_play_arrow_black_24dp);
            this.videoView.pause();
        }
    }
}
