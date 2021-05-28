package com.allinone.videosaver.free.videodownloader.statussaver.fragments;


import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.allinone.videosaver.free.videodownloader.statussaver.Adservices.UnityAd;
import com.allinone.videosaver.free.videodownloader.statussaver.Interface.GoEditTextListener;
import com.allinone.videosaver.free.videodownloader.statussaver.activity.InstaActivity;
import com.allinone.videosaver.free.videodownloader.statussaver.activity.NewDashboardActivity;
import com.allinone.videosaver.free.videodownloader.statussaver.activity.NewfbActivity;
import com.allinone.videosaver.free.videodownloader.statussaver.database.DBOperations;
import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.ViewPropertyTransition;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.unity3d.services.banners.BannerErrorInfo;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpHost;

import com.allinone.videosaver.free.videodownloader.statussaver.activity.LoginActivity;
import com.allinone.videosaver.free.videodownloader.statussaver.activity.PlayvideosActivity;
import com.allinone.videosaver.free.videodownloader.statussaver.activity.WhatsappCleanerActivity;
import com.allinone.videosaver.free.videodownloader.statussaver.utility.Utility;
import com.allinone.videosaver.free.videodownloader.statussaver.utility.VideoDownload;
import com.allinone.videosaver.free.videodownloader.statussaver.model.GoEditText;
import com.allinone.videosaver.free.videodownloader.statussaver.model.CommonModel;
import com.allinone.videosaver.free.videodownloader.statussaver.helper.ServiceHandler;
import com.allinone.videosaver.free.videodownloader.statussaver.services.ClipboardMonitorService;
import com.allinone.videosaver.free.videodownloader.statussaver.utility.PrefManager;

import ir.siaray.downloadmanagerplus.enums.DownloadReason;
import ir.siaray.downloadmanagerplus.interfaces.DownloadListener;
import videodownload.com.newmusically.R;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.CLIPBOARD_SERVICE;
import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.content.Intent.ACTION_VIEW;
import static com.google.android.gms.ads.AdSize.SMART_BANNER;
import static com.allinone.videosaver.free.videodownloader.statussaver.utility.Utility.nodatafound;
import static com.allinone.videosaver.free.videodownloader.statussaver.utility.Utility.alreadydownloaded;
import static com.allinone.videosaver.free.videodownloader.statussaver.utility.Utility.patternforsharechat;
import static com.allinone.videosaver.free.videodownloader.statussaver.utility.Utility.shareVideoToOtherApps;
import static com.allinone.videosaver.free.videodownloader.statussaver.utility.Utility.sharetext;
import static com.allinone.videosaver.free.videodownloader.statussaver.utility.Utility.videoUrl;
import static com.allinone.videosaver.free.videodownloader.statussaver.utility.WhatsAppStatusHandler.copyStatusInSavedDirectory;


public class DashboardFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "DashboardFragment";
    private NewDashboardActivity activity;
    private Context context;
    private File dirname = null;
    private File filepathfordownload = null;
    private ClipboardManager myClipboard;
    private PrefManager prefManager;
    private VideoView vdo_ContentVideo;
    private CommonModel newMusicModel;
    private GoEditText eturl;
    private ImageView image;
    private View urlcard;
    private View laycard;
    private View cardinstaprivate;
    private View ll_instaautoprivate;
    private View fbcardinside;
    private View fbappcard;
    private View wacleaner;
    private View fbcleaner;
    private View insta_new;
    private ProgressBar progressLoader;
    private NestedScrollView parentscrollview;
    private LinearLayout app_tiktok;
    private LinearLayout app_mx;
    private LinearLayout app_roposo;
    private LinearLayout app_josh;
    private LinearLayout app_sharechat;
    private LinearLayout app_fb;
    private LinearLayout app_wa;
    private LinearLayout app_wab;
    private LinearLayout app_snack;
    private LinearLayout app_moj;
    private LinearLayout app_insta;
    private LinearLayout app_twitter;
    private LinearLayout app_chingari;
    private LinearLayout app_mitron;
    private LinearLayout app_zili;
    private LinearLayout app_likee;
    private Button btndownload;
    private Button btnshare;
    private ImageView vdopause;
    private ImageView imgpreview;
    private TextView txtPaste;
    private String cliptext = "";
    private String whatdownload = "other";
    private SwitchCompat swAutoDownlaod;
    private SwitchCompat swAutoDownlaodinstagramprivate;
    private String currenturls = "";
    private String downloadfilename = "";
    private File checkfileexistsornnot = null;
    private ShimmerFrameLayout shimmer;

    /* for match*/
    public static final String forsharechat = "sharechat";
    public static final String fortiktok = "tiktok";
    public static final String forjosh = "myjosh";
    public static final String formx = "takatak";
    public static final String forSNACK = "sck.io";
    public static final String forroposo = "roposo.com";
    public static final String forinstagram = "instagram.com";
    public static final String forfacebook = "facebook.com";
    public static final String forTwitter = "twitter.com";
    public static final String forChingari = "chingari";
    public static final String forMitron = "mitron";
    public static final String forzili = "zilivideo";
    public static final String formoj = "mojapp.in";
    public static final String forLikees = "likee";
    public static final String PKG_TIKTOK = "com.zhiliaoapp.musically";
    public static final String PKG_SHARECHAT = "in.mohalla.sharechat";
    public static final String PKG_JOSH = "com.eterno.shortvideos";
    public static final String PKG_MX = "com.next.innovation.takatak";
    public static final String PKG_FB = "com.facebook.katana";
    public static final String PKG_WA = "com.whatsapp";
    public static final String PKG_WAB = "com.whatsapp.w4b";
    public static final String PKG_SNACK = "com.kwai.bulldog";
    public static final String PKG_ROPOSO = "com.roposo.android";
    public static final String PKG_MOJ = "in.mohalla.video";
    public static final String PKG_INSTAGRAM = "com.instagram.android";
    public static final String PKG_TWITTER = "com.twitter.android";
    public static final String PKG_CHINGARI = "io.chingari.app";
    public static final String PKG_MITRON = "com.mitron.tv";
    public static final String PKG_ZILI = "com.funnypuri.client";
    public static final String PKG_LIKEE = "video.like";
    public static final String[] APPLIST = new String[]{"Moj", "Tiktok", "MX Takatak", "Roposo", "Josh", "Sharechat", "Snack", "Instagram", "Facebook", "Twitter", "Chingari", "Mitron", "Zili", "Likee"};
    public static final String[] FORLIST = new String[]{formoj, fortiktok, formx, forroposo, forjosh, forsharechat, forSNACK, forinstagram, forfacebook, forTwitter, forChingari, forMitron, forzili, forLikees};
    private Intent intent;
    String CopyKey = "";
    String CopyValue = "";
    UnityAd unityAd;
    private boolean isshareselected = false;
    ViewPropertyTransition.Animator animationObject;
    private RequestOptions requestOptions;

    public DashboardFragment(Intent intent) {
        this.intent = intent;
        // Required empty public constructor
    }

    public DashboardFragment() {

        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getContext();
        unityAd = UnityAd.getInstance(context);
        activity = (NewDashboardActivity) getActivity();
        View view = inflater.inflate(R.layout.dashboardfragment, container, false);
        bindviews(view);
        initpref();
        iniviews();
        return view;
    }


    private void initpref() {
        prefManager = new PrefManager(activity);
    }

    private void bindviews(View view) {
        myClipboard = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
        vdo_ContentVideo = (VideoView) view.findViewById(R.id.vdo_ContentVideo);
        eturl = (GoEditText) view.findViewById(R.id.eturl);
        swAutoDownlaod = (SwitchCompat) view.findViewById(R.id.swAutoDownlaod);
        swAutoDownlaodinstagramprivate = (SwitchCompat) view.findViewById(R.id.swAutoDownlaodinstagramprivate);
        txtPaste = (TextView) view.findViewById(R.id.txtPaste);
        image = (ImageView) view.findViewById(R.id.image);
        vdopause = (ImageView) view.findViewById(R.id.vdopause);
        btndownload = (Button) view.findViewById(R.id.btndownload);
        btnshare = (Button) view.findViewById(R.id.btnshare);
        imgpreview = (ImageView) view.findViewById(R.id.imgpreview);
        progressLoader = (ProgressBar) view.findViewById(R.id.progressLoader);
        parentscrollview = view.findViewById(R.id.parentscrollview);
        urlcard = view.findViewById(R.id.urlcard);
        laycard = view.findViewById(R.id.laycard);
        cardinstaprivate = view.findViewById(R.id.cardinstaprivate);
        ll_instaautoprivate = view.findViewById(R.id.ll_instaautoprivate);
        fbcardinside = view.findViewById(R.id.fbcardinside);
        fbappcard = view.findViewById(R.id.fbappcard);
        wacleaner = view.findViewById(R.id.wacleaner);
        fbcleaner = view.findViewById(R.id.fbcleaner);
        insta_new = view.findViewById(R.id.insta_new);
        shimmer = view.findViewById(R.id.shimmer);
        shimmer.startShimmer();


        app_tiktok = view.findViewById(R.id.app_tiktok);
        app_mx = view.findViewById(R.id.app_mx);
        app_roposo = view.findViewById(R.id.app_roposo);
        app_josh = view.findViewById(R.id.app_josh);
        app_sharechat = view.findViewById(R.id.app_sharechat);
        app_fb = view.findViewById(R.id.app_fb);
        app_wa = view.findViewById(R.id.app_wa);
        app_wab = view.findViewById(R.id.app_wab);
        app_snack = view.findViewById(R.id.app_snack);
        app_moj = view.findViewById(R.id.app_moj);
        app_insta = view.findViewById(R.id.app_insta);
        app_twitter = view.findViewById(R.id.app_twitter);
        app_chingari = view.findViewById(R.id.app_chingari);
        app_mitron = view.findViewById(R.id.app_mitron);
        app_zili = view.findViewById(R.id.app_zili);
        app_likee = view.findViewById(R.id.app_likee);
        app_tiktok.setOnClickListener(this);
        app_mx.setOnClickListener(this);
        app_roposo.setOnClickListener(this);
        app_josh.setOnClickListener(this);
        app_sharechat.setOnClickListener(this);
        app_fb.setOnClickListener(this);
        app_wa.setOnClickListener(this);
        app_wab.setOnClickListener(this);
        app_snack.setOnClickListener(this);
        app_moj.setOnClickListener(this);
        app_insta.setOnClickListener(this);
        app_twitter.setOnClickListener(this);
        app_chingari.setOnClickListener(this);
        app_mitron.setOnClickListener(this);
        app_zili.setOnClickListener(this);
        app_likee.setOnClickListener(this);
        fbcardinside.setOnClickListener(this);
        wacleaner.setOnClickListener(this);
        fbcleaner.setOnClickListener(this);
        insta_new.setOnClickListener(this);
        RelativeLayout adView = view.findViewById(R.id.adView);
        loadbannerads(adView);
        animationObject = new ViewPropertyTransition.Animator() {
            @Override
            public void animate(View view) {
                // if it's a custom view class, cast it here
                // then find subviews and do the animations
                // here, we just use the entire view for the fade animation
                view.setAlpha(0f);
                ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
                fadeAnim.setDuration(500);
                fadeAnim.start();
            }
        };
        requestOptions = new RequestOptions().placeholder(R.drawable.ic_placeholder).error(R.drawable.ic_placeholder);
    }

    private void iniviews() {
        eturl.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.length() <= 0) {
                    setdefaultmode();
                    return;
                }
                image.setVisibility(View.VISIBLE);
                txtPaste.setVisibility(View.GONE);
            }
        });
        txtPaste.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                handlepaste(false);
            }
        });
        eturl.addListener(new GoEditTextListener() {
            public void onUpdate() {
                if (!(myClipboard == null || myClipboard.getPrimaryClip() == null)) {
                    ClipData primaryClip = myClipboard.getPrimaryClip();
                    if (primaryClip != null && primaryClip.getItemCount() > 0) {
                        Context context;
                        CharSequence charSequence;
                        ClipData.Item itemAt = primaryClip.getItemAt(0);
                        cliptext = itemAt.getText().toString();
                        currenturls = videoUrl(cliptext);
                        if (TextUtils.isEmpty(currenturls)) {
                            context = activity;
                            charSequence = activity.getResources().getString(R.string.urlnotvalid);
                        } else {
                            Log.e("urlpath", currenturls);
                            eturl.setText(currenturls);
                            vdo_ContentVideo.setVisibility(View.VISIBLE);
                            vdopause.setVisibility(View.GONE);
                            getMusicallyVideo();
                            context = activity;
                            charSequence = activity.getResources().getString(R.string.tapdownload);

                        }
                        Toast.makeText(context, charSequence, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        eturl.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i != 3) {
                    return false;
                }
                try {
                    ((InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                } catch (Exception unused) {
                    if (TextUtils.isEmpty(eturl.getText().toString()) || TextUtils.isEmpty(currenturls)) {
                        Toast.makeText(activity, activity.getResources().getString(R.string.urlnotvalid), Toast.LENGTH_SHORT).show();
                    } else {
                        getMusicallyVideo();
                    }
                    return true;
                }
                return true;
            }
        });
        if (prefManager.getMusiFirstLaunch()) {
            prefManager.setMusiFirstLaunch(false);

        }
        image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!TextUtils.isEmpty(eturl.getText().toString().trim())) {
                    eturl.setText("");
                    parentscrollview.post(new Runnable() {
                        @Override
                        public void run() {
                            parentscrollview.smoothScrollTo(0, laycard.getTop());
                        }
                    });
                }
                if (activity.snackbar != null && activity.snackbar.isShown()) {
                    activity.snackbar.dismiss();
                }
            }
        });
        btndownload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                shareDialog();
                unityAd.showinter();
                activity.checkPermission();
            }
        });
        btnshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isshareselected = true;
                shareDialog();
                unityAd.showinter();
                activity.checkPermission();
            }
        });
        shareDialog();
        vdo_ContentVideo.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (vdopause.isShown()) {
                    vdopause.setVisibility(View.GONE);
                    return false;
                }
                if (vdo_ContentVideo.isPlaying()) {
                    vdopause.setImageResource(R.drawable.ic_pause);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            if (vdo_ContentVideo.isPlaying()) {
                                vdopause.setVisibility(View.GONE);
                            }
                        }
                    }, 2000);
                } else {
                    vdopause.setImageResource(R.drawable.ic_play);
                }
                vdopause.setVisibility(View.VISIBLE);
                return false;
            }
        });
        vdopause.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                imgpreview.setVisibility(View.GONE);
                if (vdo_ContentVideo.isPlaying()) {
                    vdo_ContentVideo.pause();
                    vdopause.setImageResource(R.drawable.ic_play);
                    return;
                }
                vdo_ContentVideo.start();
                vdopause.setVisibility(View.GONE);
            }
        });
        swAutoDownlaod.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    activity.checkPermission();
                    prefManager.setServiceStatus(true);
                    activity.startService(new Intent(activity, ClipboardMonitorService.class));
                    return;
                }
                prefManager.setServiceStatus(false);
                activity.stopService(new Intent(activity, ClipboardMonitorService.class));
            }
        });
        swAutoDownlaod.setChecked(prefManager.getServiceStatus());
        if (prefManager.getServiceStatus()) {
            activity.startService(new Intent(activity, ClipboardMonitorService.class));

        }

        ll_instaautoprivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!PrefManager.getInstance(activity).getBoolean(PrefManager.ISINSTALOGIN)) {
                    Intent intent = new Intent(activity,
                            LoginActivity.class);
                    startActivityForResult(intent, 100);
                } else {
                    AlertDialog.Builder ab = new AlertDialog.Builder(activity);
                    ab.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            PrefManager.getInstance(activity).putBoolean(PrefManager.ISINSTALOGIN, false);
                            PrefManager.getInstance(activity).putString(PrefManager.COOKIES, "");
                            PrefManager.getInstance(activity).putString(PrefManager.CSRF, "");
                            PrefManager.getInstance(activity).putString(PrefManager.SESSIONID, "");
                            PrefManager.getInstance(activity).putString(PrefManager.USERID, "");

                            if (PrefManager.getInstance(activity).getBoolean(PrefManager.ISINSTALOGIN)) {
                                swAutoDownlaodinstagramprivate.setChecked(true);
                            } else {
                                swAutoDownlaodinstagramprivate.setChecked(false);
                            }
                            dialog.cancel();

                        }
                    });
                    ab.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert = ab.create();
                    alert.setTitle(getResources().getString(R.string.do_u_want_to_download_media_from_pvt));
                    alert.show();
                }
            }
        });
        setdefaultmode();
        handleintent();

    }

    private void handlepaste(boolean isfromshare) {
        Log.e(TAG, "handlepaste: called 1");

        try {
            if (!(myClipboard == null || myClipboard.getPrimaryClip() == null) && !isfromshare) {
                Log.e(TAG, "handlepaste: called 2");
                ClipData primaryClip = myClipboard.getPrimaryClip();
                if (primaryClip != null && primaryClip.getItemCount() > 0) {
                    Log.e(TAG, "handlepaste: called 3");
                    ClipData.Item itemAt = primaryClip.getItemAt(0);
                    cliptext = itemAt.getText().toString();
                    currenturls = videoUrl(cliptext);
                    handlemultipleurls(true);
                } else {
                    Log.e(TAG, "handlepaste: called 4");
                    Log.e(TAG, "handlepaste: all is blank");
                }
            } else {
                Log.e(TAG, "handlepaste: called 5");
                if (CopyValue != null && !CopyValue.equals("")) {
                    Log.e(TAG, "handlepaste: called 6");
                    cliptext = CopyValue;
                    currenturls = videoUrl(cliptext);
                    handlemultipleurls(false);
                } else Log.e(TAG, "handlepaste: called 7");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handlemultipleurls(boolean b) {
        if (currenturls != null && !TextUtils.isEmpty(currenturls)) {
            Log.e(TAG, "onClick: currenturls :" + currenturls);
            if (currenturls.contains(forsharechat)) {
                whatdownload = forsharechat;
                ArrayList<String> linkforsharechat = patternforsharechat(currenturls);
                if (linkforsharechat.size() > 0) {
                    currenturls = (String) linkforsharechat.get(0);
                }
            } else if (currenturls.contains(fortiktok)) {
                whatdownload = fortiktok;
                //
            } else if (currenturls.contains(forjosh)) {
                whatdownload = forjosh;
                ArrayList<String> linkforsharechat = patternforsharechat(currenturls);
                if (linkforsharechat.size() > 0) {
                    currenturls = (String) linkforsharechat.get(0);
                }
            } else if (currenturls.contains(formx)) {
                whatdownload = formx;
                ArrayList<String> linkforsharechat = patternforsharechat(currenturls);
                if (linkforsharechat.size() > 0) {
                    currenturls = (String) linkforsharechat.get(0);
                }
            } else if (currenturls.contains(forSNACK)) {
                whatdownload = forSNACK;
                ArrayList<String> linkforsharechat = patternforsharechat(currenturls);
                if (linkforsharechat.size() > 0) {
                    currenturls = (String) linkforsharechat.get(0);
                }
            } else if (currenturls.contains(forroposo)) {
                whatdownload = forroposo;
                ArrayList<String> linkforsharechat = patternforsharechat(currenturls);
                if (linkforsharechat.size() > 0) {
                    currenturls = (String) linkforsharechat.get(0);
                }
            } else if (currenturls.contains(forfacebook)) {
                whatdownload = forfacebook;
                ArrayList<String> linkforsharechat = patternforsharechat(currenturls);
                if (linkforsharechat.size() > 0) {
                    currenturls = (String) linkforsharechat.get(0);
                }
            } else if (currenturls.contains(forTwitter)) {
                whatdownload = forTwitter;
                ArrayList<String> linkforsharechat = patternforsharechat(currenturls);
                if (linkforsharechat.size() > 0) {
                    currenturls = (String) linkforsharechat.get(0);
                }
            } else if (currenturls.contains(forChingari)) {
                whatdownload = forChingari;
                ArrayList<String> linkforsharechat = patternforsharechat(currenturls);
                if (linkforsharechat.size() > 0) {
                    currenturls = (String) linkforsharechat.get(0);
                }
            } else if (currenturls.contains(forMitron)) {
                whatdownload = forMitron;
                ArrayList<String> linkforsharechat = patternforsharechat(currenturls);
                if (linkforsharechat.size() > 0) {
                    currenturls = (String) linkforsharechat.get(0);
                }
            } else if (currenturls.contains(forzili)) {
                whatdownload = forzili;
                ArrayList<String> linkforsharechat = patternforsharechat(currenturls);
                if (linkforsharechat.size() > 0) {
                    currenturls = (String) linkforsharechat.get(0);
                }
            } else if (currenturls.contains(forLikees)) {
                whatdownload = forLikees;
                ArrayList<String> linkforsharechat = patternforsharechat(currenturls);
                if (linkforsharechat.size() > 0) {
                    currenturls = (String) linkforsharechat.get(0);
                }
            } else if (currenturls.contains(formoj)) {
                whatdownload = formoj;
                ArrayList<String> linkforsharechat = patternforsharechat(currenturls);
                if (linkforsharechat.size() > 0) {
                    currenturls = (String) linkforsharechat.get(0);
                }
            } else if (currenturls.contains(forinstagram)) {
                cardinstaprivate.setVisibility(View.VISIBLE);
                whatdownload = forinstagram;
                ArrayList<String> linkforsharechat = patternforsharechat(currenturls);
                if (linkforsharechat.size() > 0) {
                    currenturls = (String) linkforsharechat.get(0);
                }
            } else {
                Toast.makeText(activity, "OOps This url is not supported", Toast.LENGTH_SHORT).show();
                return;
            }

        } else {
            Log.e(TAG, "handlemultipleurls: currenturls is null or blank");
        }
        if (!(TextUtils.isEmpty(currenturls) || eturl.getText().toString().trim().equals(currenturls))) {
            eturl.setText(currenturls);
            vdo_ContentVideo.setVisibility(View.VISIBLE);
            vdopause.setVisibility(View.GONE);
            getMusicallyVideo();
            Toast.makeText(activity, b ? activity.getResources().getString(R.string.tapdownload) : activity.getResources().getString(R.string.tapdownloadadded), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(activity, "Please paste valid url", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "handlemultipleurls: something wnet wrong");
        }
    }

    private void handleintent() {
        if (intent != null && intent.getExtras() != null) {
            for (String key : intent.getExtras().keySet()) {
                CopyKey = key;
                String value = intent.getExtras().getString(CopyKey);
                if (CopyKey.equals("android.intent.extra.TEXT")) {
                    CopyValue = intent.getExtras().getString(CopyKey);

                    if (value != null && !value.equals("")) {
                        handlepaste(true);
                        eturl.setText(value);
                        getMusicallyVideo();
                    }
                } else {
                    CopyValue = value;
                    Log.e(TAG, "getintentdata: 2 value : " + value);
                    if (CopyValue != null && !CopyValue.equals("")) {
                        handlepaste(true);
                        eturl.setText(CopyValue);
                        getMusicallyVideo();
                    }
                }
            }
        }
    }

    public void getMusicallyVideo() {
        if (!TextUtils.isEmpty(eturl.getText().toString().trim())) {
            progressLoader.setVisibility(View.VISIBLE);
            parentscrollview.post(new Runnable() {
                @Override
                public void run() {
                    if (whatdownload.equals(forinstagram)) {

                        parentscrollview.smoothScrollTo(0, cardinstaprivate.getTop() - 100);
                    } else {
                        parentscrollview.smoothScrollTo(0, urlcard.getTop() - 100);

                    }


                }
            });
            VideoDownload.Instance().getMusicallyUrl(eturl.getText().toString().trim(), new VideoDownload.MusicallyDelegate() {
                public void OnFailure(String str) {
                    progressLoader.setVisibility(View.GONE);
                    vdo_ContentVideo.setVisibility(View.GONE);
                    imgpreview.setVisibility(View.GONE);
                    vdopause.setVisibility(View.GONE);
                    btndownload.setEnabled(false);
                    btnshare.setEnabled(false);
                    btndownload.setTextColor(activity.getApplication().getResources().getColor(R.color.appbgcolor));
                    btnshare.setTextColor(activity.getApplication().getResources().getColor(R.color.appbgcolor));

                    btndownload.setBackgroundResource(R.drawable.main_gradi_btn_disabled);
                    btnshare.setBackgroundResource(R.drawable.main_gradi_btn_disabled);
                    if ((TextUtils.isEmpty(str) || !str.contains("Internet Connection")) && (TextUtils.isEmpty(str) || !str.contains(nodatafound))) {
                        if (!TextUtils.isEmpty(str) && str.length() > 0) {
                            Toast.makeText(activity, str, Toast.LENGTH_LONG).show();
                        }
                        return;
                    }
                    //errorLayout.setError();//todo
                }

                public void OnSuccess(CommonModel musicallyModel) {
                    newMusicModel = musicallyModel;
                    progressLoader.setVisibility(View.GONE);
                    imgpreview.setVisibility(View.VISIBLE);

                    Glide.with(activity.getApplicationContext()).load(musicallyModel.getImagePath()).transition(GenericTransitionOptions.with(animationObject)).apply(requestOptions).into(imgpreview);
                    if (vdo_ContentVideo != null) {
                        Log.e(TAG, "OnSuccess: musicallyModel.getVideoPath():" + musicallyModel.getVideoPath());
                        if (!musicallyModel.getVideoPath().isEmpty()) {
                            Log.e(TAG, "OnSuccess: videourl is not empty");
                            Uri parse = Uri.parse(musicallyModel.getVideoPath());
                            MediaController mediaController = new MediaController(activity);
                            mediaController.setAnchorView(vdo_ContentVideo);
                            mediaController.setMediaPlayer(vdo_ContentVideo);
                            vdo_ContentVideo.setMediaController(null);
                            vdo_ContentVideo.setVideoURI(parse);
                            vdo_ContentVideo.requestFocus();
                            vdo_ContentVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                public void onPrepared(MediaPlayer mediaPlayer) {
                                    vdopause.setImageResource(R.drawable.ic_play);
                                    vdopause.setVisibility(View.VISIBLE);
                                }
                            });
                        } else {
                            /* its image*/
                            Log.e(TAG, "OnSuccess: videourl is  empty");
                            vdo_ContentVideo.setOnClickListener(null);
                            vdo_ContentVideo.setOnTouchListener(null);

                        }

                        btndownload.setVisibility(View.VISIBLE);
                        btnshare.setVisibility(View.VISIBLE);

                        btndownload.setEnabled(true);
                        btnshare.setEnabled(true);
                        btndownload.setTextColor(activity.getApplication().getResources().getColor(R.color.white));
                        btnshare.setTextColor(activity.getApplication().getResources().getColor(R.color.white));

                        btndownload.setBackgroundResource(R.drawable.main_gradi_btn);
                        btnshare.setBackgroundResource(R.drawable.main_gradi_btn);
                        btndownload.setTextColor(activity.getApplication().getResources().getColor(R.color.white));
                        btnshare.setTextColor(activity.getApplication().getResources().getColor(R.color.white));
                    }
                }
            });
        }
    }


    private void setdefaultmode() {
        if (PrefManager.getInstance(activity).getBoolean(PrefManager.ISINSTALOGIN)) {
            swAutoDownlaodinstagramprivate.setChecked(true);
        } else {
            swAutoDownlaodinstagramprivate.setChecked(false);
        }
        txtPaste.setVisibility(View.VISIBLE);
        image.setVisibility(View.GONE);
        imgpreview.setVisibility(View.GONE);
        vdopause.setVisibility(View.GONE);
        vdo_ContentVideo.setVisibility(View.GONE);
        btndownload.setEnabled(false);
        btnshare.setEnabled(false);
        btndownload.setVisibility(View.GONE);
        btnshare.setVisibility(View.GONE);
        cardinstaprivate.setVisibility(View.GONE);
        btndownload.setTextColor(activity.getApplication().getResources().getColor(R.color.appbgcolor));
        btnshare.setTextColor(activity.getApplication().getResources().getColor(R.color.appbgcolor));
        btndownload.setBackgroundResource(R.drawable.main_gradi_btn_disabled);
        btnshare.setBackgroundResource(R.drawable.main_gradi_btn_disabled);
    }

    public static boolean isPackageInstalled(String str, Context context) {
        try {
            context.getPackageManager().getPackageInfo(str, 0);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }


    public void checkDownloadVideo() {
        if (newMusicModel != null) {
            String title = newMusicModel.getTitle();
            String videoUniquePath = newMusicModel.getVideoUniquePath();
            downloadfilename = whatdownload + "_" + title + videoUniquePath;
            Log.e("downloadUniquePath :", downloadfilename);
            if (!TextUtils.isEmpty(downloadfilename)) {
                //   String path = Utility.STORAGEDIR + whatdownload + "/" + downloadfilename;
                String path = Utility.STORAGEDIR + "/" + downloadfilename;
                filepathfordownload = new File(path);
                if (filepathfordownload.exists()) {
                    activity.snackbar = Snackbar.make(activity.constraint, alreadydownloaded, BaseTransientBottomBar.LENGTH_INDEFINITE).setAction((CharSequence) "OPEN", new View.OnClickListener() {
                        public void onClick(View view) {
                            if (!TextUtils.isEmpty(eturl.getText().toString().trim())) {
                                eturl.setText("");
                                vdo_ContentVideo.setVisibility(View.GONE);
                                imgpreview.setVisibility(View.GONE);
                                vdopause.setVisibility(View.GONE);
                                txtPaste.setVisibility(View.VISIBLE);
                                image.setVisibility(View.GONE);
                                btndownload.setEnabled(false);
                                btnshare.setEnabled(false);
                                btndownload.setTextColor(activity.getApplication().getResources().getColor(R.color.appbgcolor));
                                btnshare.setTextColor(activity.getApplication().getResources().getColor(R.color.appbgcolor));
                                btndownload.setBackgroundResource(R.drawable.main_gradi_btn_disabled);
                                btnshare.setBackgroundResource(R.drawable.main_gradi_btn_disabled);
                            }
                            new BackGroundTask().execute();
                        }
                    });
                    ((TextView) activity.snackbar.getView().findViewById(R.id.snackbar_text)).setTextColor(-1);
                    activity.snackbar.show();
                } else {
                    if (dirname != null) {
                        path = dirname + "/" + downloadfilename;
                        if (new File(path).exists()) {
                            Toast.makeText(activity, activity.getResources().getString(R.string.alreadydownloading), Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    downloadMusiVideo();
                }
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    public void downloadMusiVideo() {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.appdialog);
        dialog.setCancelable(false);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = -1;
        layoutParams.height = -2;
        dialog.getWindow().setAttributes(layoutParams);
        final ProgressBar progressBar = (ProgressBar) dialog.findViewById(R.id.progressDialog);
        final TextView textView = (TextView) dialog.findViewById(R.id.txtcountstart);
        ((TextView) dialog.findViewById(R.id.txttitle)).setText(activity.getResources().getString(R.string.downloading));
        progressBar.setProgress(0);
        textView.setText("0%");
        //dialog.show();
        String videoPath = newMusicModel.getVideoPath();
        if (videoPath == null || videoPath.isEmpty())
            videoPath = newMusicModel.getImagePath();
        Log.e("videoUrl", videoPath);
        String path = Utility.STORAGEDIR;//+ whatdownload;

        checkfileexistsornnot = new File(path);
        if (!checkfileexistsornnot.exists()) {
            checkfileexistsornnot.mkdir();
        }
        //path = checkfileexistsornnot.getAbsolutePath() + "/.temp/";
        path = checkfileexistsornnot.getAbsolutePath() + "/";
        dirname = new File(path);
        if (!dirname.exists()) {
            dirname.mkdir();
        }

        if (videoPath.contains("https") && !whatdownload.equals(forMitron)) {
            videoPath = videoPath.replace("https", HttpHost.DEFAULT_SCHEME_NAME);
        }
        String finalVideoPath = videoPath;
        /* insert into db now*/
        Log.e(TAG, "downloadMusiVideo:file: " + dirname.getAbsolutePath());
        Log.e(TAG, "downloadMusiVideo:downloadfilename: " + downloadfilename);
        CommonModel commonModel = new CommonModel();
        commonModel.setSaved_video_url(videoPath);
        commonModel.setSaved_video_date(String.valueOf(System.currentTimeMillis()));
        commonModel.setSaved_video_path(dirname.getAbsolutePath());
        commonModel.setSaved_video_name(downloadfilename);
        if (!TextUtils.isEmpty(eturl.getText().toString().trim())) {
            eturl.setText("");
            vdo_ContentVideo.setVisibility(View.GONE);
            imgpreview.setVisibility(View.GONE);
            vdopause.setVisibility(View.GONE);
            txtPaste.setVisibility(View.VISIBLE);
            image.setVisibility(View.GONE);
            btndownload.setEnabled(false);
            btnshare.setEnabled(false);
            btndownload.setTextColor(activity.getApplication().getResources().getColor(R.color.appbgcolor));
            btnshare.setTextColor(activity.getApplication().getResources().getColor(R.color.appbgcolor));

            btndownload.setBackgroundResource(R.drawable.main_gradi_btn_disabled);
            btnshare.setBackgroundResource(R.drawable.main_gradi_btn_disabled);
        }
        Toast.makeText(activity, activity.getResources().getString(R.string.downloading), Toast.LENGTH_SHORT).show();
        DBOperations.InsertDownload(activity, commonModel, new DownloadListener() {
            @Override
            public void onComplete(int totalBytes) {
                Toast.makeText(activity, activity.getResources().getString(R.string.download_complate), Toast.LENGTH_SHORT).show();

                String stringBuilder = checkfileexistsornnot + "/" + downloadfilename;
                File file2 = new File(stringBuilder);
                //file.renameTo(file2);
                addVideo(file2);
                /*if (dirname.exists()) {
                    dirname.delete();
                }*/
                if (prefManager.getSaveFirstLaunch()) {
                    prefManager.setSaveFirstLaunch(false);

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

        /*ServiceHandler.get(videoPath, null, new FileAsyncHttpResponseHandler(file) {
            public void onFailure(int i, Header[] headerArr, Throwable th, File file) {
                Log.e(TAG, "onFailure: called i:" + i);
                Log.e(TAG, "onFailure: called th:" + th.getMessage());
                Log.e(TAG, "onFailure: called file:" + file.getAbsolutePath());
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
                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                            }

                        }
                    }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


                }

            }

            public void onFinish() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }

            public void onProgress(long j, long j2) {
                super.onProgress(j, j2);
                int i = (int) ((((float) j) * 100.0f) / ((float) j2));
                String stringBuilder = "" +
                        i;
                Log.i("Progress::::", stringBuilder);
                if (i > 0) {
                    progressBar.setProgress(i);
                    textView.setText(String.format("%d%%", i));
                }
            }

            public void onSuccess(int i, Header[] headerArr, File file) {

            }
        });*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.app_mx:
                handleclick(PKG_MX);
                break;
            case R.id.app_tiktok:
                handleclick(PKG_TIKTOK);
                break;
            case R.id.app_roposo:
                handleclick(PKG_ROPOSO);
                break;
            case R.id.app_josh:
                handleclick(PKG_JOSH);
                break;
            case R.id.app_sharechat:
                handleclick(PKG_SHARECHAT);
                break;
            case R.id.app_fb:
                handleclick(PKG_FB);
                break;
            case R.id.app_wa:
                handleclick(PKG_WA);
                break;
            case R.id.app_wab:
                handleclick(PKG_WAB);
                break;
            case R.id.app_snack:
                handleclick(PKG_SNACK);
                break;
            case R.id.app_moj:
                handleclick(PKG_MOJ);
                break;
            case R.id.app_insta:
                handleclick(PKG_INSTAGRAM);
                break;
            case R.id.app_twitter:
                handleclick(PKG_TWITTER);
                break;
            case R.id.app_chingari:
                handleclick(PKG_CHINGARI);
                break;
            case R.id.app_mitron:
                handleclick(PKG_MITRON);
                break;
            case R.id.app_zili:
                handleclick(PKG_ZILI);
                break;
            case R.id.app_likee:
                handleclick(PKG_LIKEE);
                break;

            case R.id.wacleaner:
                opencleanerdialog();
                break;


            case R.id.fbcleaner:
                startActivity(new Intent(activity, NewfbActivity.class));
                break;
            case R.id.insta_new:
                startActivity(new Intent(activity, InstaActivity.class));
                break;

        }
    }

    private void opencleanerdialog() {
        startActivity(new Intent(activity, WhatsappCleanerActivity.class));
    }


    private void handleclick(String str) {

        Intent launchIntent1 = activity.getPackageManager().getLaunchIntentForPackage(str);
        if (launchIntent1 != null) {
            activity.startActivity(launchIntent1);
        } else {
            String stringBuilder = "market://details?id=" + str;
            Intent intent = new Intent(ACTION_VIEW, Uri.parse(stringBuilder));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        }


    }

    private class BackGroundTask extends AsyncTask<Void, Void, ArrayList<String>> {

        protected ArrayList<String> doInBackground(Void... voidArr) {
            String stringBuilder = Utility.STORAGEDIR;
            File file = new File(stringBuilder);
            ArrayList<String> arrayList = new ArrayList();
            if (file != null) {
                File[] listFiles = file.listFiles();
                if (listFiles != null) {
                    Arrays.sort(listFiles, new Comparator() {
                        public int compare(Object obj, Object obj2) {
                            File file = (File) obj;
                            File file2 = (File) obj2;
                            return file.lastModified() > file2.lastModified() ? -1 : file.lastModified() < file2.lastModified() ? 1 : 0;
                        }
                    });
                    for (int i = 0; i < listFiles.length; i++) {
                        if (!listFiles[i].getAbsolutePath().contains(".temp")) {
                            arrayList.add(listFiles[i].getAbsolutePath());
                            Log.e("videoPath", listFiles[i].getAbsolutePath());
                        }
                    }
                }
            }
            return arrayList;
        }

        protected void onPostExecute(ArrayList<String> arrayList) {
            if (activity.snackbar != null && activity.snackbar.isShown()) {
                activity.snackbar.dismiss();
            }
            if (arrayList != null) {
                Intent intent = new Intent(activity, PlayvideosActivity.class);
                int i = 0;
                if (!arrayList.isEmpty()) {
                    i = arrayList.indexOf(filepathfordownload.getAbsolutePath());
                    Log.e("particularVideoPath", filepathfordownload.getAbsolutePath());
                }
                intent.putExtra("item", i);
                intent.putExtra("videoPath", arrayList);
                startActivity(intent);
            }
            super.onPostExecute(arrayList);
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    public void addVideo(File file) {
        if (isshareselected) {
            isshareselected = false;
            copyStatusInSavedDirectory(file.getAbsolutePath(), "", "s", activity);
            //shareVideoToOtherApps(activity,);
        }
        ContentValues contentValues = new ContentValues(3);
        contentValues.put("title", file.getName());
        contentValues.put("mime_type", "video/mp4");
        contentValues.put("_data", file.getAbsolutePath());
        activity.getApplicationContext().getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues);
        unityAd.showinter();
    }

    public void shareDialog() {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.custom_dialog_back);
        dialog.setContentView(R.layout.appdialog_share);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = -2;
        layoutParams.height = -2;
        layoutParams.gravity = 17;
        dialog.getWindow().setAttributes(layoutParams);
        if (prefManager.getshare() % 20 == 0) {
            dialog.show();
        }
        prefManager.setSHARE(prefManager.getshare() + 1);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            public void onDismiss(DialogInterface dialogInterface) {
                if (prefManager.getLater() >= 2) {
                    prefManager.setNever(true);
                } else {
                    prefManager.setLater(prefManager.getLater() + 1);
                }
                dialog.dismiss();
            }
        });
        ((TextView) dialog.findViewById(R.id.tvShare)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("text/plain");
                intent.putExtra("android.intent.extra.TEXT", sharetext);
                startActivity(Intent.createChooser(intent, "Share Text"));
                prefManager.setNever(true);
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.e(TAG, "onActivityResult: called fragment");
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == 100 && resultCode == RESULT_OK) {
                String requiredValue = data.getStringExtra("key");
                if (PrefManager.getInstance(activity).getBoolean(PrefManager.ISINSTALOGIN)) {
                    swAutoDownlaodinstagramprivate.setChecked(true);
                } else {
                    swAutoDownlaodinstagramprivate.setChecked(false);
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    private void loadbannerads(RelativeLayout topBannerView) {
        if (UnityAd.ISADSENABLE) {
            switch (UnityAd.WHATAD) {
                //0 =google 1= fb //2 = alternative//3 for unity
                case "0":
                    loadAdbmobbanner(topBannerView);
                    break;
                case "1":
                    loadfbbanner(topBannerView);
                    break;
                case "2":
                    break;
                case "3":
                    loadunitybanner(topBannerView);
                    break;
            }

        }
    }

    private void loadAdbmobbanner(final RelativeLayout topBannerView) {
        if (topBannerView.getChildCount() > 0) {
            topBannerView.removeAllViews();
        }
        topBannerView.setVisibility(View.GONE);
        final AdView mAdView = new AdView(context);
        mAdView.setAdSize(SMART_BANNER);
        mAdView.setAdUnitId(context.getString(R.string.banner_ad_units_id1));
        mAdView.loadAd(new AdRequest.Builder().build());
        mAdView.setAdListener(new AdListener());
        mAdView.setAdListener(new AdListener() {
            public void onAdClosed() {

                loadunitybanner(topBannerView);
                Log.e("Adbmobbanner closed", "onAdClosed");
            }

            public void onAdFailedToLoad(LoadAdError i) {
                topBannerView.setVisibility(View.GONE);
                loadunitybanner(topBannerView);

                Log.e("Adbmobbanner failed", String.valueOf(i));
            }

            public void onAdLeftApplication() {
                Log.e("left", "onAdLeftApplication");
            }

            public void onAdLoaded() {
                topBannerView.removeAllViews();
                topBannerView.setVisibility(View.VISIBLE);
                topBannerView.addView(mAdView);
                Log.e("onAdLoaded", "onAdLoaded");
            }

            public void onAdOpened() {
                Log.e("opened", "onAdOpened");
            }
        });

    }

    private void loadunitybanner(final RelativeLayout topBannerView) {
        if (topBannerView.getChildCount() > 0) {
            topBannerView.removeAllViews();
        }
        topBannerView.setVisibility(View.GONE);
        BannerView topBanner = new BannerView((Activity) context, unityAd.bannerPlacement, new UnityBannerSize(320, 50));
        topBanner.setListener(new BannerView.IListener() {
            @Override
            public void onBannerLoaded(BannerView bannerView) {
                topBannerView.removeAllViews();
                topBannerView.setVisibility(View.VISIBLE);
                topBannerView.addView(topBanner);
            }

            @Override
            public void onBannerClick(BannerView bannerView) {

            }

            @Override
            public void onBannerFailedToLoad(BannerView bannerView, BannerErrorInfo bannerErrorInfo) {
                topBannerView.setVisibility(View.GONE);
            }

            @Override
            public void onBannerLeftApplication(BannerView bannerView) {

            }
        });
        topBanner.load();

    }

    private void loadfbbanner(final RelativeLayout topBannerView) {
        if (topBannerView.getChildCount() > 0) {
            topBannerView.removeAllViews();
        }
        topBannerView.setVisibility(View.GONE);
        com.facebook.ads.AdView adView = new com.facebook.ads.AdView(context, context.getString(R.string.fbbanner), AdSize.BANNER_HEIGHT_50);
        com.facebook.ads.AdListener adListener = new com.facebook.ads.AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                topBannerView.setVisibility(View.GONE);
                // Ad error callback
                loadunitybanner(topBannerView);

            }

            @Override
            public void onAdLoaded(Ad ad) {

                topBannerView.removeAllViews();
                topBannerView.setVisibility(View.VISIBLE);
                topBannerView.addView(adView);
                // Ad loaded callback
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
            }
        };
        adView.loadAd(adView.buildLoadAdConfig().withAdListener(adListener).build());

    }

    @Override
    public void onResume() {
        super.onResume();
        shimmer.startShimmer();
    }

    @Override
    public void onPause() {
        super.onPause();
        shimmer.stopShimmer();
    }
}