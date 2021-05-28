package com.allinone.videosaver.free.videodownloader.statussaver.services;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipData.Item;
import android.content.ClipboardManager;
import android.content.ClipboardManager.OnPrimaryClipChangedListener;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.provider.MediaStore.Video.Media;

import androidx.core.content.ContextCompat;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.allinone.videosaver.free.videodownloader.statussaver.database.DBOperations;
import com.allinone.videosaver.free.videodownloader.statussaver.fragments.DashboardFragment;
import com.loopj.android.http.FileAsyncHttpResponseHandler;


import com.allinone.videosaver.free.videodownloader.statussaver.utility.Utility;
import com.allinone.videosaver.free.videodownloader.statussaver.utility.VideoDownload;
import com.allinone.videosaver.free.videodownloader.statussaver.model.CommonModel;
import com.allinone.videosaver.free.videodownloader.statussaver.helper.ServiceHandler;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpHost;
import ir.siaray.downloadmanagerplus.enums.DownloadReason;
import ir.siaray.downloadmanagerplus.interfaces.DownloadListener;
import videodownload.com.newmusically.BuildConfig;
import videodownload.com.newmusically.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.allinone.videosaver.free.videodownloader.statussaver.utility.Utility.alreadydownloaded;
import static com.allinone.videosaver.free.videodownloader.statussaver.utility.Utility.patternforsharechat;
import static com.allinone.videosaver.free.videodownloader.statussaver.utility.Utility.videoUrl;


public class ClipboardMonitorService extends Service {
    private static final String filename = "clipboard-history.txt";
    private static final String TAG = "ClipboardMonitorService";
    String currentcopiedurl = "";
    String filenames = "";
    File file = null;
    File file1 = null;
    String whatdownload = "other";
    private ClipboardManager mClipboardManager;
    private File mHistoryFile;
    private OnPrimaryClipChangedListener mOnPrimaryClipChangedListener = new OnPrimaryClipChangedListener() {
        public void onPrimaryClipChanged() {
            if (mClipboardManager != null && mClipboardManager.getPrimaryClip() != null) {
                ClipData primaryClip = mClipboardManager.getPrimaryClip();
                if (primaryClip != null && primaryClip.getItemCount() > 0) {
                    Item itemAt = primaryClip.getItemAt(0);
                    if (itemAt != null) {
                        String charSequence = itemAt.getText().toString();
                        currentcopiedurl = videoUrl(charSequence);
                        if (currentcopiedurl != null)
                            if (currentcopiedurl.contains(DashboardFragment.forsharechat)) {
                                whatdownload = DashboardFragment.forsharechat;
                                ArrayList<String> linkforsharechat = patternforsharechat(currentcopiedurl);
                                if (linkforsharechat.size() > 0) {
                                    currentcopiedurl = (String) linkforsharechat.get(0);
                                }
                            } else if (currentcopiedurl.contains(DashboardFragment.fortiktok)) {
                                whatdownload = DashboardFragment.fortiktok;
                                //
                            } else if (currentcopiedurl.contains(DashboardFragment.forjosh)) {
                                whatdownload = DashboardFragment.forjosh;
                                ArrayList<String> linkforsharechat = patternforsharechat(currentcopiedurl);
                                if (linkforsharechat.size() > 0) {
                                    currentcopiedurl = (String) linkforsharechat.get(0);
                                }
                            } else if (currentcopiedurl.contains(DashboardFragment.formx)) {
                                whatdownload = DashboardFragment.formx;
                                ArrayList<String> linkforsharechat = patternforsharechat(currentcopiedurl);
                                if (linkforsharechat.size() > 0) {
                                    currentcopiedurl = (String) linkforsharechat.get(0);
                                }
                            } else if (currentcopiedurl.contains(DashboardFragment.forSNACK)) {
                                whatdownload = DashboardFragment.forSNACK;
                                ArrayList<String> linkforsharechat = patternforsharechat(currentcopiedurl);
                                if (linkforsharechat.size() > 0) {
                                    currentcopiedurl = (String) linkforsharechat.get(0);
                                }
                            } else if (currentcopiedurl.contains(DashboardFragment.forroposo)) {
                                whatdownload = DashboardFragment.forroposo;
                                ArrayList<String> linkforsharechat = patternforsharechat(currentcopiedurl);
                                if (linkforsharechat.size() > 0) {
                                    currentcopiedurl = (String) linkforsharechat.get(0);
                                }
                            } else if (currentcopiedurl.contains(DashboardFragment.forinstagram)) {
                                whatdownload = DashboardFragment.forinstagram;
                                ArrayList<String> linkforsharechat = patternforsharechat(currentcopiedurl);
                                if (linkforsharechat.size() > 0) {
                                    currentcopiedurl = (String) linkforsharechat.get(0);
                                }
                            } else if (currentcopiedurl.contains(DashboardFragment.forfacebook)) {
                                whatdownload = DashboardFragment.forfacebook;
                                ArrayList<String> linkforsharechat = patternforsharechat(currentcopiedurl);
                                if (linkforsharechat.size() > 0) {
                                    currentcopiedurl = (String) linkforsharechat.get(0);
                                }
                            } else if (currentcopiedurl.contains(DashboardFragment.forLikees)) {
                                whatdownload = DashboardFragment.forLikees;
                                ArrayList<String> linkforsharechat = patternforsharechat(currentcopiedurl);
                                if (linkforsharechat.size() > 0) {
                                    currentcopiedurl = (String) linkforsharechat.get(0);
                                }
                            } else if (currentcopiedurl.contains(DashboardFragment.forChingari)) {
                                whatdownload = DashboardFragment.forChingari;
                                ArrayList<String> linkforsharechat = patternforsharechat(currentcopiedurl);
                                if (linkforsharechat.size() > 0) {
                                    currentcopiedurl = (String) linkforsharechat.get(0);
                                }
                            } else if (currentcopiedurl.contains(DashboardFragment.forTwitter)) {
                                whatdownload = DashboardFragment.forTwitter;
                                ArrayList<String> linkforsharechat = patternforsharechat(currentcopiedurl);
                                if (linkforsharechat.size() > 0) {
                                    currentcopiedurl = (String) linkforsharechat.get(0);
                                }
                            } else if (currentcopiedurl.contains(DashboardFragment.formoj)) {
                                whatdownload = DashboardFragment.formoj;
                                ArrayList<String> linkforsharechat = patternforsharechat(currentcopiedurl);
                                if (linkforsharechat.size() > 0) {
                                    currentcopiedurl = (String) linkforsharechat.get(0);
                                }
                            }
                        if (!TextUtils.isEmpty(currentcopiedurl)) {
                            getVideo();
                        }
                    }
                }
            }
        }
    };
    private ExecutorService mThreadPool = Executors.newSingleThreadExecutor();
    private CommonModel newMusicModel;

    private void downloadVideo() {
        if (this.newMusicModel != null) {
            Log.e(TAG, "downloadVideo: called newMusicModel is not null");
            String title = newMusicModel.getTitle();
            String videoUniquePath = newMusicModel.getVideoUniquePath();
            this.filenames = whatdownload +"_"+ title + videoUniquePath;
            if (!TextUtils.isEmpty(this.filenames)) {
             //   String path = Utility.STORAGEDIR + whatdownload + "/" + filenames;
                String path = Utility.STORAGEDIR +   "/" + filenames;
                if (new File(path.toString()).exists()) {
                    Toast.makeText(this, alreadydownloaded, Toast.LENGTH_SHORT).show();
                } else {
                    if (this.file1 != null) {

                        path = file1 + "/" + filenames;

                        if (new File(path).exists()) {
                            Toast.makeText(this, this.getResources().getString(R.string.alreadydownloading), Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    Toast.makeText(this, "Downloading Video from " + getApplicationContext().getResources().getString(R.string.app_name), Toast.LENGTH_SHORT).show();
                    downloadMusiVideo();
                }
            }
        }
    }

    public void addVideo(File file) {
        ContentValues contentValues = new ContentValues(3);
        contentValues.put("title", "My video title");
        contentValues.put("mime_type", "video/mp4");
        contentValues.put("_data", file.getAbsolutePath());
        getApplicationContext().getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, contentValues);
    }

    @SuppressLint("StaticFieldLeak")
    public void downloadMusiVideo() {
        Log.e(TAG, "downloadMusiVideo: called");
        String videoPath = this.newMusicModel.getVideoPath();
        if (videoPath == null || videoPath.isEmpty())
            videoPath = newMusicModel.getImagePath();

        String path = Utility.STORAGEDIR ;//+ whatdownload;

        this.file = new File(path);
        if (!this.file.exists()) {
            this.file.mkdir();
        }
        path = file.getAbsolutePath() ;//+ "/.temp/";
        this.file1 = new File(path);
        if (!this.file1.exists()) {
            this.file1.mkdir();
        }
        String stringBuilder2 = file1 + "/" + filenames;
        File file = new File(stringBuilder2);
        if (videoPath.contains("https") && !whatdownload.equals(DashboardFragment.forMitron)) {
            videoPath = videoPath.replace("https", HttpHost.DEFAULT_SCHEME_NAME);
        }
        String finalVideoPath = videoPath;
        if (BuildConfig.DEBUG)
            Log.e(TAG, "downloadMusiVideo: videoPath :" + videoPath);
        Log.e(TAG, "downloadMusiVideo: file :" + file.getAbsolutePath());
        Log.e(TAG, "downloadMusiVideo: file :" + file.getName());



        /* insert into db now*/
        CommonModel commonModel = new CommonModel();
        commonModel.setSaved_video_url(videoPath);
        commonModel.setSaved_video_date(String.valueOf(System.currentTimeMillis()));
        commonModel.setSaved_video_path(file.getAbsolutePath());
        commonModel.setSaved_video_name(filenames);
        DBOperations.InsertDownload(getApplicationContext(), commonModel, new DownloadListener() {
            @Override
            public void onComplete(int totalBytes) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ClipboardMonitorService.this, getResources().getString(R.string.download_complate), Toast.LENGTH_SHORT).show();
                    }
                });

                String stringBuilder = ClipboardMonitorService.this.file + "/" + filenames;
                File file2 = new File(stringBuilder);
                file.renameTo(file2);
                addVideo(file2);
                if (file1.exists()) {
                    file1.delete();
                }
            }

            @Override
            public void onPause(int percent, DownloadReason reason, int totalBytes, int downloadedBytes) {

            }

            @Override
            public void onPending(int percent, int totalBytes, int downloadedBytes) {

            }

            @Override
            public void onFail(int percent, DownloadReason reason, int totalBytes, int downloadedBytes) {

            }

            @Override
            public void onCancel(int totalBytes, int downloadedBytes) {

            }

            @Override
            public void onRunning(int percent, int totalBytes, int downloadedBytes, float downloadSpeed) {

            }
        });


       /* ServiceHandler.get(videoPath, null, new FileAsyncHttpResponseHandler(file) {
            public void onFailure(int i, Header[] headerArr, Throwable th, File file) {
                if (i == 403) {
                    new AsyncTask<Void, Void, Void>() {


                        @Override
                        protected Void doInBackground(Void... voids) {
                            int count;
                            try {
                                URL url = new URL(finalVideoPath);
                                URLConnection conection = url.openConnection();
                                conection.connect();
                                int lenghtOfFile = conection.getContentLength();
                                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                                OutputStream output = new FileOutputStream(stringBuilder2);
                                byte data[] = new byte[1024];
                                long total = 0;
                                while ((count = input.read(data)) != -1) {
                                    total += count;
                                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                                    output.write(data, 0, count);
                                }
                                output.flush();
                                output.close();
                                input.close();
                            } catch (Exception e) {
                                Log.e("Error: ", e.getMessage());
                            }
                            return null;
                        }

                        private void publishProgress(String s) {
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            Toast.makeText(ClipboardMonitorService.this, getResources().getString(R.string.download_complate), Toast.LENGTH_SHORT).show();
                            String stringBuilder = file + "/" +
                                    filenames;
                            File file2 = new File(stringBuilder);
                            file.renameTo(file2);
                            addVideo(file2);
                            if (file1.exists()) {
                                file1.delete();
                            }
                        }
                    }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


                }
            }

            public void onSuccess(int i, Header[] headerArr, File file) {
                Log.e(TAG, "onSuccess: called");

            }
        });*/
    }

    public void getVideo() {
        VideoDownload.Instance().getMusicallyUrl(this.currentcopiedurl, new VideoDownload.MusicallyDelegate() {
            public void OnFailure(String str) {
            }

            public void OnSuccess(CommonModel musicallyModel) {
                newMusicModel = musicallyModel;
                if (VERSION.SDK_INT < 23 || ContextCompat.checkSelfPermission(ClipboardMonitorService.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == 0) {
                    downloadVideo();
                }
            }
        });
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        this.mHistoryFile = new File(getExternalFilesDir(null), filename);
        this.mClipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        this.mClipboardManager.addPrimaryClipChangedListener(this.mOnPrimaryClipChangedListener);
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.mClipboardManager != null) {
            this.mClipboardManager.removePrimaryClipChangedListener(this.mOnPrimaryClipChangedListener);
        }
    }

    public void onTaskRemoved(Intent intent) {
        stopSelf();
        Log.e("stopservice", "stopServices");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onTaskRemoved(intent);
    }
}
