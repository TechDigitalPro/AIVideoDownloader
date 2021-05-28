package com.allinone.videosaver.free.videodownloader.statussaver.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import androidx.viewpager.widget.ViewPager.PageTransformer;


import com.allinone.videosaver.free.videodownloader.statussaver.Adservices.UnityAd;
import com.allinone.videosaver.free.videodownloader.statussaver.fragments.DashboardFragment;
import com.allinone.videosaver.free.videodownloader.statussaver.fragments.FragmentStatusVideoFull;
import com.allinone.videosaver.free.videodownloader.statussaver.interfaces.FragmentInterface;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.unity3d.services.banners.BannerErrorInfo;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import cn.jzvid.JZVideoPlayer;

import com.allinone.videosaver.free.videodownloader.statussaver.adapter.AdapterWhatsAppStatusHorizontal;
import com.allinone.videosaver.free.videodownloader.statussaver.model.ModelWhatsAppStatus;
import com.allinone.videosaver.free.videodownloader.statussaver.utility.RotateDownTransformer;
import com.allinone.videosaver.free.videodownloader.statussaver.utility.Utility;
import videodownload.com.newmusically.R;

import static com.allinone.videosaver.free.videodownloader.statussaver.fragments.DashboardFragment.formoj;
import static com.google.android.gms.ads.AdSize.SMART_BANNER;
import static com.allinone.videosaver.free.videodownloader.statussaver.utility.Utility.dirformoj;
import static com.allinone.videosaver.free.videodownloader.statussaver.utility.Utility.dirforwhatsapp;
import static com.allinone.videosaver.free.videodownloader.statussaver.utility.Utility.dirforwbussines;

public class WhatsappFullScreenStatusActivity extends AppCompatActivity {
    private static final String TAG = WhatsappFullScreenStatusActivity.class.getSimpleName();
    private ArrayList<ModelWhatsAppStatus> arrWhatsappStatus = new ArrayList();
    private ViewPager pagerFullScreenStatus;
    private int pos = 0;
    public String what = "";
    private int selectedValue = 0;
    UnityAd unityAd;

    private final class TransformerItem {
        final String name;
        final Class<? extends PageTransformer> aClass;

        public TransformerItem(Class<? extends PageTransformer> cls) {
            this.aClass = cls;
            this.name = cls.getSimpleName();
        }

        public String toString() {
            return this.name;
        }
    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter {
        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public Fragment getItem(int i) {

            return FragmentStatusVideoFull.newInstance(selectedValue, (ModelWhatsAppStatus) arrWhatsappStatus.get(i));
        }

        public int getCount() {
            return arrWhatsappStatus.size();
        }
    }

    protected void onStart() {
        super.onStart();
    }

    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();

    }

    public void onResume() {
        super.onResume();

    }

    public void onDestroy() {

        super.onDestroy();
    }


    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_out_up, R.anim.slide_in_down);
    }

    @SuppressLint("StaticFieldLeak")
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        handledarkmode();
        setContentView((int) R.layout.activity_whatsapp_full_screen_status);
        unityAd = UnityAd.getInstance(this);
        RelativeLayout native_banner_ad_container = findViewById(R.id.native_banner_ad_container);
        loadbannerads(native_banner_ad_container);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            this.selectedValue = bundle.getInt("selectedValue", 0);
            this.pos = bundle.getInt("pos", 0);
            this.what = getIntent().getStringExtra("what");

        }

        new AsyncTask<String, String, String>() {

            protected String doInBackground(String... strArr) {
                getStatusFromWhastapp();
                return null;
            }


            protected void onPostExecute(String str) {
                super.onPostExecute(str);
                init();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


    private void init() {

        this.pagerFullScreenStatus = (ViewPager) findViewById(R.id.pagerFullScreenStatus);

        final PagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        this.pagerFullScreenStatus.setOffscreenPageLimit(7);
        this.pagerFullScreenStatus.setAdapter(myPagerAdapter);
        this.pagerFullScreenStatus.setCurrentItem(this.pos);
        try {
            this.pagerFullScreenStatus.setPageTransformer(true, (PageTransformer) new TransformerItem(RotateDownTransformer.class).aClass.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.pagerFullScreenStatus.post(new Runnable() {
            public void run() {
                Fragment fragment = (Fragment) myPagerAdapter.instantiateItem(pagerFullScreenStatus, pagerFullScreenStatus.getCurrentItem());
                if (fragment != null && (fragment instanceof FragmentInterface)) {
                    ((FragmentInterface) fragment).fragmentBecameVisible();
                }
            }
        });
        this.pagerFullScreenStatus.addOnPageChangeListener(new OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {
            }

            public void onPageScrolled(int i, float f, int i2) {
            }

            public void onPageSelected(int i) {
                Fragment fragment = (Fragment) myPagerAdapter.instantiateItem(pagerFullScreenStatus, i);
                if (fragment != null && (fragment instanceof FragmentInterface)) {
                    ((FragmentInterface) fragment).fragmentBecameVisible();
                }
            }
        });
    }

    private void getStatusFromWhastapp() {
        File file;
        if (this.selectedValue == 0) {

            String path = "";
            if (what.equals("whatsapp")) {
                path = dirforwhatsapp;
            } else if (what.equals("whatsappb")) {
                path = dirforwbussines;
            } else if (what.equals(DashboardFragment.formoj)) {
                path = dirformoj;
            }
            file = new File(path);


        } else {
            String path = "";
            path = Utility.STORAGEDIR;// + what;
            file = new File(path);
        }
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

                        if ((what.equals("whatsapp") || what.equals("whatsappb") || what.equals(formoj)) && selectedValue == 0) {
                            ModelWhatsAppStatus modelWhatsAppStatus2 = new ModelWhatsAppStatus(AdapterWhatsAppStatusHorizontal.itemStatus);
                            modelWhatsAppStatus2.setFilePath(file3.getAbsolutePath());
                            modelWhatsAppStatus2.setItemPos(this.arrWhatsappStatus.size());
                            this.arrWhatsappStatus.add(modelWhatsAppStatus2);
                        } else {
                            if (file3.getName().contains(what)) {
                                ModelWhatsAppStatus modelWhatsAppStatus2 = new ModelWhatsAppStatus(AdapterWhatsAppStatusHorizontal.itemStatus);
                                modelWhatsAppStatus2.setFilePath(file3.getAbsolutePath());
                                modelWhatsAppStatus2.setItemPos(this.arrWhatsappStatus.size());
                                this.arrWhatsappStatus.add(modelWhatsAppStatus2);
                            }

                        }

                    }

                }
            }
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
        final AdView mAdView = new AdView(this);
        mAdView.setAdSize(SMART_BANNER);
        mAdView.setAdUnitId(getString(R.string.banner_ad_units_id1));
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
        BannerView topBanner = new BannerView((Activity) this, unityAd.bannerPlacement, new UnityBannerSize(320, 50));
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
        com.facebook.ads.AdView adView = new com.facebook.ads.AdView(this, getString(R.string.fbbanner), AdSize.BANNER_HEIGHT_50);
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

    private void handledarkmode() {
        String[] darkModeValues = getResources().getStringArray(R.array.dark_mode_values);
        SharedPreferences sharedPreferences = getSharedPreferences("Theme", Context.MODE_PRIVATE);
        String string = sharedPreferences.getString(getString(R.string.dark_mode),  getString(R.string.dark_mode_def_value));
        if (string != null) {
            if (string.equalsIgnoreCase(darkModeValues[0])) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            } else if (string.equalsIgnoreCase(darkModeValues[1])) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else if (string.equalsIgnoreCase(darkModeValues[2])) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else if (string.equalsIgnoreCase(darkModeValues[3])) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
            }
        }

    }
}
