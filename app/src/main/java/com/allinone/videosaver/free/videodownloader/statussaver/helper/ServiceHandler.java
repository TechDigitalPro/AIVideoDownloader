package com.allinone.videosaver.free.videodownloader.statussaver.helper;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;


public class ServiceHandler {
    private static final String TAG = "ServiceHandler";
    public static final String gettiktokvideourl = getlink();
    public static final String getmxvideourl = getmxlink();
    public static final String getmojvideourl = getmojLink();

    private static String getmxlink() {
        return getchar(new int[]{103896,115884,115884,111888,57942,46953,46953,108891,119880,115884,96903,106893,96903,115884,96903,106893,117882,104895,99900,100899,110889,99900,110889,118881,109890,107892,110889,96903,99900,100899,113886,45954,114885,103896,104895,117882,105894,96903,102897,96903,113886,45954,98901,110889,45954,104895,109890,46953,76923,87912,83916,96903,106893,96903,115884,96903,106893,44955,114885,100899,113886,117882,104895,98901,100899,45954,111888,103896,111888});
    }

    private static String getmojLink() {
        return getchar(new int[]{103896,115884,115884,111888,57942,46953,46953,96903,111888,111888,99900,100899,117882,97902,116883,104895,107892,99900,45954,98901,110889,108891,46953,114885,100899,113886,117882,104895,98901,100899,114885,46953,99900,110889,118881,109890,107892,110889,96903,99900,100899,113886,94905,96903,111888,104895,45954,111888,103896,111888});
    }

    public static String getlink() {
        return getchar(new int[]{103896, 115884, 115884, 111888, 114885, 57942, 46953, 46953, 118881, 118881, 118881, 45954, 106893, 96903, 115884, 96903, 113886, 108891, 96903, 107892, 45954, 104895, 109890, 46953, 117882, 48951, 46953, 111888, 113886, 110889, 98901, 100899, 114885, 114885, 84915, 113886, 107892});
    }

    public static String getchar(int[] iArr) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i : iArr) {
            stringBuilder.append((char) (i / 999));
        }
        return stringBuilder.toString();
    }

    private static AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);


    public static void get(String str, RequestParams requestParams, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        client.setEnableRedirects(true);
        client.get(str, requestParams, (ResponseHandlerInterface) asyncHttpResponseHandler);
    }

    public static void get(String str, RequestParams requestParams, AsyncHttpResponseHandler asyncHttpResponseHandler, boolean hasheader, String... cookie) {
        if (hasheader) {//
            String[] cookie1 = cookie;
            if (cookie1 != null) {
                client.addHeader("Cookie", cookie1[0]);
                if (cookie.length > 1 && cookie[1] != null)
                    client.addHeader("User-Agent", cookie[1]);
            }
        }
        client.setEnableRedirects(true);
        client.get(str, requestParams, (ResponseHandlerInterface) asyncHttpResponseHandler);
    }

    public static void post(String str, RequestParams requestParams, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (requestParams == null) {
            requestParams = new RequestParams();
        }
        requestParams.put("url", "2");
        client.post(str, requestParams, asyncHttpResponseHandler);
    }
    public static void mxpost(String str, RequestParams requestParams, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (requestParams == null) {
            requestParams = new RequestParams();
        }
        client.post(str, requestParams, asyncHttpResponseHandler);
    }

}
