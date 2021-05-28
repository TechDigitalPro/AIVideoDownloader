package com.allinone.videosaver.free.videodownloader.statussaver.activity;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import com.allinone.videosaver.free.videodownloader.statussaver.adapter.AdapterWhatsAppStatusHorizontal;
import com.allinone.videosaver.free.videodownloader.statussaver.adapter.WhatsAppStatusCleanerAdapter;
import com.allinone.videosaver.free.videodownloader.statussaver.model.ModelWhatsAppStatus;
import com.allinone.videosaver.free.videodownloader.statussaver.utility.Utility;
import videodownload.com.newmusically.R;

public class CleanerActivity extends BaseActivity {
    private static final String TAG = "CleanerActivity";
    private CleanerActivity activity;
    private int valueDownloadedStatus = 1;
    private int valueNewStatus = 0;
    private WhatsAppStatusCleanerAdapter adapterWhatsAppStatusHorizontal;
    private ArrayList<ModelWhatsAppStatus> arrWhatsappStatus = new ArrayList();
    private RecyclerView recyclerNewStatus;
    private int selectedValue = 1;
    private String what = "";
    private String title = "";
    private SwipeRefreshLayout swipeToRefreshStatus;
    private TextView txtError;
    private TextView txt_title;
    private ImageView iv_selectall;
    private FloatingActionButton add_fab;
    private Toolbar mToolbar;
    private boolean isallselected = false;
    private boolean isaudio = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utility.handledarkmode(this);
        setContentView(R.layout.fragment_whatsapp_new_status);
        activity = this;
        getIntentpath();
        init();
        setStatusData();
    }

    private void getIntentpath() {
        what = getIntent().getStringExtra("what");
        title = getIntent().getStringExtra("title");
        isaudio = getIntent().getBooleanExtra("isaudio", false);
    }

    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setVisibility(View.VISIBLE);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable.ic_baseline_arrow_back_24));

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //What to do on back clicked
            }
        });
        add_fab = findViewById(R.id.add_fab);

        this.txtError = (TextView) findViewById(R.id.txtError);
        this.txt_title = (TextView) findViewById(R.id.txt_title);
        this.iv_selectall = findViewById(R.id.iv_selectall);
        txt_title.setText(title);
        this.recyclerNewStatus = (RecyclerView) findViewById(R.id.recyclerNewStatus);
        this.adapterWhatsAppStatusHorizontal = new WhatsAppStatusCleanerAdapter(activity, this.arrWhatsappStatus, this.selectedValue, what, isaudio ? R.layout.list_item_audio : R.layout.list_item_whats_app_status_horizontal, isaudio);
        this.recyclerNewStatus.setAdapter(this.adapterWhatsAppStatusHorizontal);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 3);
        GridLayoutManager gridLayoutManagerisaudio = new GridLayoutManager(activity, 1, RecyclerView.VERTICAL, false);
        this.recyclerNewStatus.setLayoutManager(!isaudio ? gridLayoutManager : gridLayoutManagerisaudio);

        this.adapterWhatsAppStatusHorizontal.setOnSelactionListener(new WhatsAppStatusCleanerAdapter.SelactionChangeListener() {
            public void onSelactionChange(boolean z) {
                if (z) {
                    showDeleteButton();
                } else {
                    hideDeleteButton();
                }//todo
            }
        });

        add_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSelaction();
                hideDeleteButton();
            }
        });
        this.swipeToRefreshStatus = (SwipeRefreshLayout) findViewById(R.id.swipeToRefreshStatus);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            public int getSpanSize(int i) {
                return ((ModelWhatsAppStatus) arrWhatsappStatus.get(i)).itemType == 3 ? 1 : 1;


            }
        });
        swipeToRefreshStatus.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setStatusData();
            }
        });
        iv_selectall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isallselected) {
                    isallselected = true;
                    showDeleteButton();
                    adapterWhatsAppStatusHorizontal.selectall(isallselected);
                } else {
                    isallselected = false;
                    hideDeleteButton();
                    adapterWhatsAppStatusHorizontal.selectall(isallselected);
                }


            }
        });
    }

    public void deleteSelaction() {
        this.adapterWhatsAppStatusHorizontal.deleteAllSelaction();
        //setStatusData();
    }

    @SuppressLint("StaticFieldLeak")
    private void setStatusData() {
        Log.e(TAG, "setStatusData: called");
        new AsyncTask<String, String, String>() {

            protected void onPreExecute() {
                super.onPreExecute();
                arrWhatsappStatus.clear();
                swipeToRefreshStatus.setRefreshing(true);
            }


            protected String doInBackground(String... strArr) {
                getStatusFromWhastapp();
                return null;
            }


            protected void onPostExecute(String str) {
                super.onPostExecute(str);
                swipeToRefreshStatus.setRefreshing(false);
                if (arrWhatsappStatus == null || arrWhatsappStatus.size() <= 0) {

                    txtError.setText(activity.getResources().getString(R.string.nofilesavailable));

                    txtError.setVisibility(View.VISIBLE);
                    iv_selectall.setVisibility(View.INVISIBLE);
                    return;
                }
                txtError.setVisibility(View.GONE);
                iv_selectall.setVisibility(View.VISIBLE);
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        adapterWhatsAppStatusHorizontal.notifyDataSetChanged();

                    }
                });
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void getStatusFromWhastapp() {
        File file;
        file = new File(what);

        if (file.exists()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                Arrays.sort(listFiles, new Comparator() {
                    public int compare(Object obj, Object obj2) {
                        File file = (File) obj;
                        File file2 = (File) obj2;
                        if (file.lastModified() > file2.lastModified()) {
                            return -1;
                        }
                        return file.lastModified() < file2.lastModified() ? 1 : 0;
                    }
                });
                this.arrWhatsappStatus.clear();
                for (File file3 : listFiles) {
                    if (!file3.isDirectory() && !file3.getAbsolutePath().contains(".nomedia")) {
                        ModelWhatsAppStatus modelWhatsAppStatus2 = new ModelWhatsAppStatus(AdapterWhatsAppStatusHorizontal.itemStatus);
                        modelWhatsAppStatus2.setFilePath(file3.getAbsolutePath());
                        modelWhatsAppStatus2.setItemPos(this.arrWhatsappStatus.size());
                        this.arrWhatsappStatus.add(modelWhatsAppStatus2);
                    }

                }
            }
        }
    }

    public void hideDeleteButton() {
        add_fab.setVisibility(View.GONE);

    }

    public void showDeleteButton() {
        add_fab.setVisibility(View.VISIBLE);

    }
}