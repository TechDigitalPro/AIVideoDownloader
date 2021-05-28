package com.allinone.videosaver.free.videodownloader.statussaver.Adservices;

import android.app.Activity;
import android.content.Context;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;


import videodownload.com.newmusically.BuildConfig;
import videodownload.com.newmusically.R;

public class UnityAd {
    private static final String TAG = "UnityAds";
    private Context context;
    public static UnityAd store;
    /**
     * boolean for Enable/Disable ads , if true then ads will shown
     */
    public static final boolean ISADSENABLE = true;


    private static final String GOOGLEADMOB = "0";
    private static final String FACEBOOKAD = "1";
    private static final String ALTERNATIVE = "2";
    private static final String UNITYADS = "3";

    /*
     * WHATAD is indicating that which ads to shown
     *
     * if you want to use google then select GOOGLEADMOB     or
     * if you want to use facebookads then select FACEBOOKAD    or
     * if you want to use alternative ads then select ALTERNATIVE   or
     * if you want to use Unity ads then select UNITYADS
     * */
    public static final String WHATAD = GOOGLEADMOB;

    /**
     * To Test Unity ads only for testing purpose ,
     * do false when release or just ignore this line it will automatically false
     */
    private static final boolean testMode = BuildConfig.DEBUG;

    /**
     * Placement id of unity ads , don't change it
     * untill not required for additional ads
     */
    private static final String placementIdinter = "Inter";
    public static final String bannerPlacement = "banner";


    /**
     * Admob ads
     */
    private InterstitialAd mInterstitialAdMob = null;
    //fb
    com.facebook.ads.InterstitialAd interstitialAdfb;
    /**
     * This is the InterstitialAd ad click Threshold
     * It defines when fullscreen ads to be displayed after given Threshold click.
     */
    private int InterAdThrespold = 3;
    private int currentcounter = 1;


    public static UnityAd getInstance(Context context) {
        if (store == null)
            store = new UnityAd(context);
        return store;
    }

    public UnityAd(final Context context) {
        this.context = context;
        if (ISADSENABLE) {
            UnityAdsListener myAdsListener = new UnityAdsListener();
            com.unity3d.ads.UnityAds.addListener(myAdsListener);
            com.unity3d.ads.UnityAds.initialize((Activity) context, context.getString(R.string.unityGameID), testMode, testMode);

            if (WHATAD.equals(GOOGLEADMOB)) {
                loadinteradmob();
            } else if (WHATAD.equals(FACEBOOKAD)) {
                loadfbInter();
            } else if (WHATAD.equals(ALTERNATIVE)) {
                loadinteradmob();
                loadfbInter();
            }
        }

    }

    private void loadfbInter() {
        AudienceNetworkAds.initialize(context);
        interstitialAdfb = new com.facebook.ads.InterstitialAd(context, context.getString(R.string.fbinter));
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback

            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback

            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed

                // Show the ad

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
        interstitialAdfb.loadAd(interstitialAdfb.buildLoadAdConfig()
                .withAdListener(interstitialAdListener)
                .build());
    }


    public void showUnityInter() {
        if (ISADSENABLE)
            if (UnityAds.isReady(placementIdinter)) {
                UnityAds.show((Activity) context, placementIdinter);
            } else {

            }
    }

    public void showinter() {
        if (ISADSENABLE) {
            currentcounter++;
            if (currentcounter % InterAdThrespold == 0) {
                currentcounter = 0;
                switch (WHATAD) {
                    //0 =google 1= fb //2 = alternative//3 for unity
                    case "0":
                        showinteradmob();
                        break;
                    case "1":
                        // fb
                        showfbinter();
                        break;
                    case "2":
                        //alternate
                        showmixed();
                        break;
                    case "3":
                    default:
                        //unity
                        loadunityinter();
                        break;
                }
            }

        }
    }

    private boolean isFb = false;

    private void showmixed() {
        if (!isFb) {
            isFb = true;
            showinteradmob();

        } else {
            isFb = false;
            showfbinter();


        }

    }

    private void showfbinter() {
        if (interstitialAdfb == null || !interstitialAdfb.isAdLoaded()) {
            showUnityInter();
            return;
        }
        // Check if ad is already expired or invalidated, and do not show ad if that is the case. You will not get paid to show an invalidated ad.
        if (interstitialAdfb.isAdInvalidated()) {
            showUnityInter();
            return;
        }
        // Show the ad
        interstitialAdfb.show();
    }

    private void showinteradmob() {
        if (mInterstitialAdMob != null && mInterstitialAdMob.isLoaded()) {
            mInterstitialAdMob.show();
        } else {
            showUnityInter();
        }
    }

    private void loadunityinter() {
        showUnityInter();
    }


    private void loadinteradmob() {
        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mInterstitialAdMob = new InterstitialAd(context);
        mInterstitialAdMob.setAdUnitId(context.getString(R.string.admob_interstitial_id));
        mInterstitialAdMob.setAdListener(new AdListener() {

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                loadunityinter();
            }

            @Override
            public void onAdClosed() {
                requestNewInterstitial();

            }
        });
        requestNewInterstitial();

    }

    public void requestNewInterstitial() {
        //AdRequest adRequest = new AdRequest.Builder().addTestDevice("B630A940994DE88DDC3A654C3D5CD48D").build();
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAdMob.loadAd(adRequest);
    }

    private static class UnityAdsListener implements IUnityAdsListener {


        @Override
        public void onUnityAdsReady(String placementId) {
            // Implement functionality for an ad being ready to show.
        }

        @Override
        public void onUnityAdsStart(String placementId) {
            // Implement functionality for a user starting to watch an ad.
        }

        @Override
        public void onUnityAdsFinish(String placementId, com.unity3d.ads.UnityAds.FinishState finishState) {
            if (finishState.equals(UnityAds.FinishState.COMPLETED)) {
                // Reward the user for watching the ad to completion.
            } else if (finishState.equals(UnityAds.FinishState.SKIPPED)) {
                // Do not reward the user for skipping the ad.
            } else if (finishState.equals(UnityAds.FinishState.ERROR)) {
                // Log an error.
            }
            // Implement functionality for a user finishing an ad.
        }

        @Override
        public void onUnityAdsError(com.unity3d.ads.UnityAds.UnityAdsError error, String message) {
            // Implement functionality for a Unity Ads service error occurring.
        }
    }


}
