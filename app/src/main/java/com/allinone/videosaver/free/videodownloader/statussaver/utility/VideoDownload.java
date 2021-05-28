package com.allinone.videosaver.free.videodownloader.statussaver.utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.allinone.videosaver.free.videodownloader.statussaver.app.App;
import com.allinone.videosaver.free.videodownloader.statussaver.fragments.DashboardFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler1;
import com.yxcorp.gifshow.util.CPU;

import com.allinone.videosaver.free.videodownloader.statussaver.model.instagram.ModelEdge;
import com.allinone.videosaver.free.videodownloader.statussaver.model.instagram.ModelEdgeSidecarToChildren;
import com.allinone.videosaver.free.videodownloader.statussaver.model.instagram.ModelResponse;
import com.allinone.videosaver.free.videodownloader.statussaver.model.mitron.ModelMitron;
import com.allinone.videosaver.free.videodownloader.statussaver.model.twitter.TwitterResponse;
import com.allinone.videosaver.free.videodownloader.statussaver.model.CommonModel;
import com.allinone.videosaver.free.videodownloader.statussaver.helper.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import cz.msebera.android.httpclient.Header;
import videodownload.com.newmusically.BuildConfig;
import videodownload.com.newmusically.R;

import static com.allinone.videosaver.free.videodownloader.statussaver.helper.ServiceHandler.getmojvideourl;
import static com.allinone.videosaver.free.videodownloader.statussaver.helper.ServiceHandler.getmxvideourl;
import static com.allinone.videosaver.free.videodownloader.statussaver.utility.Utility.USER_AGENT_1;
import static com.allinone.videosaver.free.videodownloader.statussaver.utility.Utility.USER_AGENT_2;
import static com.allinone.videosaver.free.videodownloader.statussaver.utility.Utility.tryagain;
import static com.allinone.videosaver.free.videodownloader.statussaver.utility.Utility.setToast;
import static com.allinone.videosaver.free.videodownloader.statussaver.utility.Utility.tryagain_login;

public class VideoDownload {
    private static final String TAG = "VideoDownload";
    private static volatile VideoDownload _instance;
    private String exten = "";
    private String videoUniquePath = "";
    private String videoUrl = "";
    private String videoTitle = "";
    private String asdx;
    ModelMitron modelMitron;
    private boolean isfirsttry = true;
    Pattern pattern = Pattern.compile("window\\.data \\s*=\\s*(\\{.+?\\});");

    public interface MusicallyDelegate {
        void OnFailure(String str);

        void OnSuccess(CommonModel musicallyModel);
    }

    public static VideoDownload Instance() {
        if (_instance == null) {
            synchronized (VideoDownload.class) {
                _instance = new VideoDownload();
            }
        }
        return _instance;
    }

    public static String tiktokhardening(String str, String str2, String str3) {
        String str4 = "ISO-8859-1";
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(str2.getBytes(str4));
            SecretKeySpec secretKeySpec = new SecretKeySpec(str.getBytes(str4), "AES");
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            instance.init(1, secretKeySpec, ivParameterSpec);
            return Base64.encodeToString(instance.doFinal(str3.getBytes()), 0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String getmxid(String str) {
        try {
            return Uri.parse(mxmatcher(str).get(0)).getQueryParameter("id");
        } catch (Exception unused) {
            return "";
        }
    }

    public static ArrayList<String> mxmatcher(String str) {
        ArrayList<String> arrayList = new ArrayList<String>();
        Matcher matcher = Pattern.compile("\\(?\\b(mxtakatak://|www[.])[-A-Za-z0-9+&amp;@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&amp;@#/%=~_()|]").matcher(str);
        while (matcher.find()) {
            String group = matcher.group();
            if (group.startsWith("(") && group.endsWith(")")) {
                group = group.substring(1, group.length() - 1);
            }
            arrayList.add(group);
        }
        return arrayList;
    }

    private String getUrlWithoutParameters(String url) {
        try {
            URI uri = new URI(url);
            return new URI(uri.getScheme(),
                    uri.getAuthority(),
                    uri.getPath(),

                    uri.getFragment()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            setToast(App.getContext(), App.getContext().getResources().getString(R.string.enter_valid_url));
            return "";
        }
    }

    private Long getTweetId(String s) {
        try {
            String[] split = s.split("\\/");
            String id = split[5].split("\\?")[0];
            return Long.parseLong(id);
        } catch (Exception e) {
            Log.d("TAG", "getTweetId: " + e.getLocalizedMessage());
            return null;
        }
    }

    Context context;

    @SuppressLint({"StaticFieldLeak", "SetJavaScriptEnabled"})
    public void getMusicallyUrl(final String str, final MusicallyDelegate musicallyDelegate) {
        this.context = App.getContext();
        Log.e(TAG, "getMusicallyUrl: str:" + str);

        if (str.contains(DashboardFragment.forLikees)) {
            downloadLikee(str, musicallyDelegate);

        } else if (str.contains(DashboardFragment.formoj)) {
            downloadMoj(str, musicallyDelegate);

        } else if (str.contains(DashboardFragment.forzili)) {
            downloadzili(str, musicallyDelegate);

        } else if (str.contains(DashboardFragment.forMitron)) {
            downloadmitron(str, musicallyDelegate);


        } else if (str.contains(DashboardFragment.forTwitter)) {
            downloadtwitter(str, musicallyDelegate);


        } else if (str.contains(DashboardFragment.forfacebook)) {
            downloadfb(str, musicallyDelegate);

        } else if (str.contains(DashboardFragment.forinstagram)) {
            downloadinsta(str, musicallyDelegate);


        } else if (str.contains(DashboardFragment.forSNACK)) {
            snackdownload(str, musicallyDelegate);


        } else if (str.contains(DashboardFragment.formx)) {
            downloadmx(str, musicallyDelegate);

        } else if (str.contains(DashboardFragment.forsharechat) || str.contains(DashboardFragment.forroposo) || str.contains(DashboardFragment.forChingari)) {
            downloadsharechat_roposo_chingari(str, musicallyDelegate);


        } else if (str.contains(DashboardFragment.fortiktok)) {
            downloadtiktok(str, musicallyDelegate);

        } else if (str.contains(DashboardFragment.forjosh)) {
            downloadjosh(str, musicallyDelegate);

        }


    }

    private void downloadjosh(String str, MusicallyDelegate musicallyDelegate) {
        String s = str;
        try {

            s = new URI(s).getPath();
            s = s.substring(s.lastIndexOf(47) + 1);
            ServiceHandler.get("http://api.coolfie.io/share/content/" + s, null, new TextHttpResponseHandler1() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String response) {
                    try {
                        if (response != null) {
                            Log.e(TAG, "onSuccess call: " + response.toString());
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            Log.e(TAG, "onSuccess: download_url:" + jsonObject1.getString("mp4_url"));


                            String str = "{resolution}";
                            String str2 = "{quality}";

                            String url = jsonObject1.has("mp4_url") ? jsonObject1.getString("mp4_url") : "";
                            String thumbnail = jsonObject1.has("thumbnail_url") ? jsonObject1.getString("thumbnail_url") : "";
                            CommonModel igtvModel = new CommonModel();
                            igtvModel.setImagePath(thumbnail.replace(str2, "60").replace(str, "720").replace("{thumbnail_type}", "still"));
                            igtvModel.setVideoPath(url.replace(str2, "vh").replace(str, "orig"));
                            igtvModel.setTitle(String.valueOf(System.currentTimeMillis()));
                            String stringBuilder2 = videoUniquePath + ".mp4";

                            igtvModel.setVideoUniquePath(stringBuilder2);
                            if (musicallyDelegate != null) {
                                musicallyDelegate.OnSuccess(igtvModel);
                            }

                        }
                    } catch (Exception e) {
                        if (musicallyDelegate != null) {
                            Log.e(TAG, "onSuccess: 2.0" + musicallyDelegate);
                            musicallyDelegate.OnFailure(tryagain);
                        }
                    }

                }
            });
        } catch (Exception unused) {
            s = "";
        }
    }

    private void downloadtiktok(String str, MusicallyDelegate musicallyDelegate) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("tiktokurl", tiktokhardening("ZLDXDo2Bc4VPrWht", "q2u5ilxHSGYzPm5o", str));
        ServiceHandler.post(ServiceHandler.gettiktokvideourl, requestParams, new TextHttpResponseHandler1() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                try {
                    if (response != null) {
                        Log.e(TAG, "onSuccess call: " + response.toString());
                        JSONObject jsonObject = new JSONObject(response);
                        String url = jsonObject.has("url") ? jsonObject.getString("url") : "";
                        String thumbnail = jsonObject.has("thumbnail") ? jsonObject.getString("thumbnail") : "";
                        CommonModel igtvModel = new CommonModel();
                        igtvModel.setImagePath(thumbnail);
                        igtvModel.setVideoPath(url);
                        igtvModel.setTitle(String.valueOf(System.currentTimeMillis()));
                        String stringBuilder2 = videoUniquePath + ".mp4";

                        igtvModel.setVideoUniquePath(stringBuilder2);
                        if (musicallyDelegate != null) {
                            musicallyDelegate.OnSuccess(igtvModel);
                        }

                    }
                } catch (Exception e) {
                    if (musicallyDelegate != null) {
                        Log.e(TAG, "onSuccess: 2.0" + musicallyDelegate);
                        musicallyDelegate.OnFailure(tryagain);
                    }
                }

            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private void downloadsharechat_roposo_chingari(String str, MusicallyDelegate musicallyDelegate) {
        new AsyncTask<Void, Void, Void>() {

            private String img;
            private String url;

            private boolean iss = false;


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Document document = Jsoup.connect(str).followRedirects(true).get();
                    img = document.select("meta[property=og:image]").first().attr("content");
                    url = document.select("meta[property=og:video]").first().attr("content");
                    Log.e(TAG, "getMusicallyUrl: img:" + img);
                    Log.e(TAG, "getMusicallyUrl: str22:" + url);
                    iss = true;
                } catch (Exception e) {
                    iss = false;
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (iss) {
                    CommonModel igtvModel = new CommonModel();
                    igtvModel.setImagePath(img);
                    igtvModel.setVideoPath(url);
                    igtvModel.setTitle(String.valueOf(System.currentTimeMillis()));
                    String stringBuilder2 = videoUniquePath + ".mp4";

                    igtvModel.setVideoUniquePath(stringBuilder2);
                    if (musicallyDelegate != null) {
                        musicallyDelegate.OnSuccess(igtvModel);
                    }
                } else {
                    if (musicallyDelegate != null) {
                        Log.e(TAG, "onSuccess: 2.0" + musicallyDelegate);
                        musicallyDelegate.OnFailure(tryagain);
                    }

                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @SuppressLint("StaticFieldLeak")
    private void downloadmx(String str, MusicallyDelegate musicallyDelegate) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("url", str);
        ServiceHandler.mxpost(getmxvideourl, requestParams, new TextHttpResponseHandler1() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                if (musicallyDelegate != null) {
                    Log.e(TAG, "onSuccess: 2.0" + musicallyDelegate);
                    musicallyDelegate.OnFailure(tryagain);
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                try {
                    if (response != null) {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.has("status") && jsonObject.getString("status").equalsIgnoreCase("success")) {
                            CommonModel igtvModel = new CommonModel();
                            String thumbnail = jsonObject.has("videourl") ? jsonObject.getString("videourl") : "";
                            String url = jsonObject.has("videourl") ? jsonObject.getString("videourl") : "";
                            igtvModel.setImagePath(thumbnail);
                            igtvModel.setVideoPath(url);
                            igtvModel.setTitle(String.valueOf(System.currentTimeMillis()));
                            String stringBuilder2 = videoUniquePath + ".mp4";
                            igtvModel.setVideoUniquePath(stringBuilder2);
                            if (musicallyDelegate != null) {
                                musicallyDelegate.OnSuccess(igtvModel);
                            }
                        } else {
                            if (musicallyDelegate != null) {
                                musicallyDelegate.OnFailure(tryagain);
                            }
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (musicallyDelegate != null) {
                        Log.e(TAG, "onSuccess: 2.0" + musicallyDelegate);
                        musicallyDelegate.OnFailure(tryagain);
                    }
                }

            }
        });
        /*
         * deprecated
         * */
        /*new AsyncTask<Void, Void, Void>() {
            private boolean iss = false;

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Document document = Jsoup.connect(str).userAgent(USER_AGENT_1).followRedirects(true).get();
                    Log.e(TAG, "doInBackground:body: " + document.body().text());
                    Log.e(TAG, "doInBackground:toString: " + document.toString());
                    asdx = getmxid(document.toString());
                    iss = true;
                } catch (Exception e) {
                    iss = false;
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (iss) {
                    ServiceHandler.get("https://mxshorts.mxplay.com/v1/feed?id=" + asdx + "&content=r_shortv&cur_time=" + System.currentTimeMillis(), null, new TextHttpResponseHandler1() {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String response) {
                            try {
                                if (response != null) {
                                    Log.e(TAG, "onSuccess: headers:" + Arrays.toString(headers));
                                    Log.e(TAG, "onSuccess call: " + response.toString());


                                    JSONObject jsonObject = new JSONObject(response);
                                    String url = jsonObject.has("content_url") ? jsonObject.getString("content_url") : "";
                                    String thumbnail = jsonObject.has("thumbnail") ? jsonObject.getString("thumbnail") : "";
                                    CommonModel igtvModel = new CommonModel();
                                    igtvModel.setImagePath(thumbnail);
                                    igtvModel.setVideoPath(url);
                                    igtvModel.setTitle(String.valueOf(System.currentTimeMillis()));
                                    String stringBuilder2 = videoUniquePath + ".mp4";

                                    igtvModel.setVideoUniquePath(stringBuilder2);
                                    if (musicallyDelegate != null) {
                                        musicallyDelegate.OnSuccess(igtvModel);
                                    }

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                if (musicallyDelegate != null) {
                                    Log.e(TAG, "onSuccess: 2.0" + musicallyDelegate);
                                    musicallyDelegate.OnFailure(tryagain);
                                }
                            }

                        }
                    });
                } else {
                    if (musicallyDelegate != null) {
                        Log.e(TAG, "onSuccess: 2.0" + musicallyDelegate);
                        musicallyDelegate.OnFailure(tryagain);
                    }

                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);*/
    }

    @SuppressLint("StaticFieldLeak")
    private void downloadMoj(String str, MusicallyDelegate musicallyDelegate) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("url", str);
        ServiceHandler.mxpost(getmojvideourl, requestParams, new TextHttpResponseHandler1() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                if (musicallyDelegate != null) {
                    Log.e(TAG, "onSuccess: 2.0" + musicallyDelegate);
                    musicallyDelegate.OnFailure(tryagain);
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                try {
                    if (response != null) {
                        if (BuildConfig.DEBUG) {
                            Log.e(TAG, "onSuccess: res:" + response);
                        }
                        JSONObject jsonObject = new JSONObject(response);
                        if ((jsonObject.has("Status")) && jsonObject.getString("Status").equalsIgnoreCase("success")) {
                            CommonModel igtvModel = new CommonModel();
                            JSONArray jsonArray = jsonObject.getJSONArray("VideoResult");
                            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                            String thumbnail = jsonObject1.has("thumbnil") ? jsonObject1.getString("thumbnil") : "";
                            String url = jsonObject1.has("VideoUrl") ? jsonObject1.getString("VideoUrl") : "";
                            igtvModel.setImagePath(thumbnail);
                            igtvModel.setVideoPath(url);
                            igtvModel.setTitle(String.valueOf(System.currentTimeMillis()));
                            String stringBuilder2 = videoUniquePath + ".mp4";
                            igtvModel.setVideoUniquePath(stringBuilder2);
                            if (musicallyDelegate != null) {
                                musicallyDelegate.OnSuccess(igtvModel);
                            }


                        } else {
                            if (musicallyDelegate != null) {
                                musicallyDelegate.OnFailure(tryagain);
                            }
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (musicallyDelegate != null) {
                        Log.e(TAG, "onSuccess: 2.0" + musicallyDelegate);
                        musicallyDelegate.OnFailure(tryagain);
                    }
                }

            }
        });

    }

    private void snackdownload(String str, MusicallyDelegate musicallyDelegate) {
        ServiceHandler.get(getsnackurl(str), null, new TextHttpResponseHandler1() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                try {
                    if (response != null) {
                        JSONObject jsonObject = new JSONObject(response).getJSONObject("photo");
                        JSONObject jsforimage = jsonObject.getJSONObject("music");
                        JSONArray jaforimg = jsforimage.getJSONArray("imageUrls");
                        JSONObject joforiii = jaforimg.getJSONObject(0);
                        JSONArray jsonArray = jsonObject.getJSONArray("main_mv_urls");
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                        String url = jsonObject1.has("url") ? jsonObject1.getString("url") : "";
                        String thumbnail = joforiii.has("url") ? joforiii.getString("url") : "";
                        CommonModel igtvModel = new CommonModel();
                        igtvModel.setImagePath(thumbnail);
                        igtvModel.setVideoPath(url);
                        igtvModel.setTitle(String.valueOf(System.currentTimeMillis()));
                        String stringBuilder2 = videoUniquePath + ".mp4";
                        igtvModel.setVideoUniquePath(stringBuilder2);
                        if (musicallyDelegate != null) {
                            musicallyDelegate.OnSuccess(igtvModel);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (musicallyDelegate != null) {
                        Log.e(TAG, "onSuccess: 2.0" + musicallyDelegate);
                        musicallyDelegate.OnFailure(tryagain);
                    }
                }

            }
        });
    }

    private void downloadinsta(String str, MusicallyDelegate musicallyDelegate) {
        String UrlWithoutQP = getUrlWithoutParameters(str);
        UrlWithoutQP = UrlWithoutQP + "?__a=1";
        String coockie = "ds_user_id=" + PrefManager.getInstance(context).getString(PrefManager.USERID)
                + "; sessionid=" + PrefManager.getInstance(context).getString(PrefManager.SESSIONID);
        Log.e(TAG, "downloadinsta: " + UrlWithoutQP);
        ServiceHandler.get(UrlWithoutQP, null, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e(TAG, "onFailure:statusCode: " + statusCode);
                Log.e(TAG, "onFailure:throwable: " + throwable.getMessage());
                if (statusCode == 404) {
                    if (musicallyDelegate != null) {
                        musicallyDelegate.OnFailure(tryagain_login);
                    }
                } else {
                    if (musicallyDelegate != null) {
                        musicallyDelegate.OnFailure(tryagain);
                    }
                }

                // Log.e(TAG, "onFailure:errorResponse: "+errorResponse );
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e(TAG, "onSuccess: JSONObject" + response.toString());
                Type listType = new TypeToken<ModelResponse>() {
                }.getType();
                ModelResponse modelResponse = new Gson().fromJson(response.toString(), listType);
                if (modelResponse != null) {
                    if (modelResponse.getModelGraphql() != null) {
                        if (modelResponse.getModelGraphql().getShortcode_media() != null) {
                            ModelEdgeSidecarToChildren modelEdgeSidecarToChildren = modelResponse.getModelGraphql().getShortcode_media().getEdge_sidecar_to_children();
                            String VideoUrl = "";
                            String PhotoUrl = "";
                            if (modelEdgeSidecarToChildren != null) {
                                List<ModelEdge> modelEdgeArrayList = modelEdgeSidecarToChildren.getModelEdges();
                                for (int i = 0; i < modelEdgeArrayList.size(); i++) {
                                    if (modelEdgeArrayList.get(i).getModelNode().isIs_video()) {
                                        VideoUrl = modelEdgeArrayList.get(i).getModelNode().getVideo_url();

                                    } else {
                                        PhotoUrl = modelEdgeArrayList.get(i).getModelNode().getDisplay_resources().get(modelEdgeArrayList.get(i).getModelNode().getDisplay_resources().size() - 1).getSrc();

                                    }
                                }
                            } else {
                                boolean isVideo = modelResponse.getModelGraphql().getShortcode_media().isIs_video();
                                if (isVideo) {
                                    VideoUrl = modelResponse.getModelGraphql().getShortcode_media().getVideo_url();
                                    //new DownloadFileFromURL().execute(VideoUrl,getFilenameFromURL(VideoUrl));

                                } else {
                                    PhotoUrl = modelResponse.getModelGraphql().getShortcode_media().getDisplay_resources()
                                            .get(modelResponse.getModelGraphql().getShortcode_media().getDisplay_resources().size() - 1).getSrc();


                                }
                            }
                            CommonModel igtvModel = new CommonModel();
                            igtvModel.setImagePath(PhotoUrl);
                            igtvModel.setVideoPath(VideoUrl);
                            igtvModel.setTitle(String.valueOf(System.currentTimeMillis()));
                            String stringBuilder2 = videoUniquePath + (!VideoUrl.isEmpty() ? ".mp4" : ".jpg");
                            igtvModel.setVideoUniquePath(stringBuilder2);
                            if (musicallyDelegate != null) {
                                musicallyDelegate.OnSuccess(igtvModel);
                            }
                        } else {
                            if (musicallyDelegate != null) {
                                Log.e(TAG, "onSuccess: 2.0" + musicallyDelegate);
                                musicallyDelegate.OnFailure(tryagain);
                            }
                            // error
                        }
                    } else {
                        if (musicallyDelegate != null) {
                            Log.e(TAG, "onSuccess: 2.0" + musicallyDelegate);
                            musicallyDelegate.OnFailure(tryagain);
                        }
                        //error
                    }

                } else {
                    if (musicallyDelegate != null) {
                        Log.e(TAG, "onSuccess: 2.0" + musicallyDelegate);
                        musicallyDelegate.OnFailure(tryagain);
                    }
                    //error
                }


            }


        }, true, coockie);
    }

    @SuppressLint("StaticFieldLeak")
    private void downloadfb(String str, MusicallyDelegate musicallyDelegate) {
        new AsyncTask<String, Void, Document>() {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Document doInBackground(String... urls) {
                try {
                    Document document;
                    if (isfirsttry) {
                        document = Jsoup.connect(str).userAgent(USER_AGENT_1).followRedirects(true).get();
                    } else {
                        document = Jsoup.connect(str).userAgent(USER_AGENT_2).followRedirects(true).get();
                    }

                    try {
                        Log.e(TAG, "doInBackground: document:" + document.html());
                        videoUrl = document.select("meta[property=og:video]").last().attr("content");
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }

                    try {
                        //Log.e(TAG, "doInBackground: document:" + document.html());
                        videoTitle = document.select("meta[property=og:title]").last().attr("content");
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        videoTitle = "";
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "doInBackground: Error");
                }
                return null;
            }

            protected void onPostExecute(Document result) {
                try {

                    Log.e("onPostExecute: ", videoUrl);
                    if (!videoUrl.equals("")) {
                        isfirsttry = true;
                        CommonModel igtvModel = new CommonModel();
                        igtvModel.setImagePath(videoUrl);
                        igtvModel.setVideoPath(videoUrl);
                        igtvModel.setTitle((videoTitle == null || videoTitle.equalsIgnoreCase("") ? String.valueOf(System.currentTimeMillis()) : videoTitle));
                        String stringBuilder2 = videoUniquePath + (!videoUrl.isEmpty() ? ".mp4" : ".jpg");
                        igtvModel.setVideoUniquePath(stringBuilder2);
                        if (musicallyDelegate != null) {
                            musicallyDelegate.OnSuccess(igtvModel);
                        }
                    } else {
                        if (isfirsttry) {
                            isfirsttry = false;
                            downloadfb(str, musicallyDelegate);
                            return;
                        }
                        if (musicallyDelegate != null) {
                            Log.e(TAG, "onSuccess: 2.0" + musicallyDelegate);
                            musicallyDelegate.OnFailure(tryagain);
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    if (musicallyDelegate != null) {
                        Log.e(TAG, "onSuccess: 2.0" + musicallyDelegate);
                        musicallyDelegate.OnFailure(tryagain);
                    }
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void downloadtwitter(String str, MusicallyDelegate musicallyDelegate) {
        Long id = getTweetId(str);
        if (id != null) {

            RequestParams requestParams = new RequestParams();
            requestParams.put("id", id);
            ServiceHandler.post("https://twittervideodownloaderpro.com/twittervideodownloadv2/index.php", requestParams, new TextHttpResponseHandler1() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    if (musicallyDelegate != null) {
                        Log.e(TAG, "onSuccess: 2.0" + musicallyDelegate);
                        musicallyDelegate.OnFailure(tryagain);
                    }
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {

                    try {
                        Type listType = new TypeToken<TwitterResponse>() {
                        }.getType();
                        TwitterResponse responseModel = new Gson().fromJson(responseString, listType);
                        videoUrl = responseModel.getVideos().get(0).getUrl();
                        if (responseModel.getVideos().get(0).getType().equals("image")) {

                        } else {
                            videoUrl = responseModel.getVideos().get(responseModel.getVideos().size() - 1).getUrl();

                        }
                        CommonModel igtvModel = new CommonModel();
                        igtvModel.setImagePath(videoUrl);
                        igtvModel.setVideoPath(videoUrl);
                        igtvModel.setTitle(String.valueOf(System.currentTimeMillis()));
                        String stringBuilder2 = videoUniquePath + ".mp4";
                        igtvModel.setVideoUniquePath(stringBuilder2);
                        if (musicallyDelegate != null) {
                            musicallyDelegate.OnSuccess(igtvModel);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        if (musicallyDelegate != null) {
                            Log.e(TAG, "onSuccess: 2.0" + musicallyDelegate);
                            musicallyDelegate.OnFailure(tryagain);
                        }
                    }
                }
            });
        } else {
            if (musicallyDelegate != null) {
                Log.e(TAG, "onSuccess: 2.0" + musicallyDelegate);
                musicallyDelegate.OnFailure(tryagain);
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void downloadmitron(String str, MusicallyDelegate musicallyDelegate) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    String[] separated = str.split("=");
                    String str1 = separated[separated.length - 1];
                    Log.e(TAG, "doInBackground: str1:" + str1);
                    Document document = Jsoup.connect("https://web.mitron.tv/video/" + str1).userAgent(USER_AGENT_1).followRedirects(true).get();
                    String A = document.select("script[id=__NEXT_DATA__]").last().data();
                    Log.e(TAG, "doInBackground: A:" + A);
                    modelMitron = new Gson().fromJson(A, ModelMitron.class);


                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (modelMitron != null) {
                    videoUrl = modelMitron.getProps().getPageProps().getVideo().getVideoUrl();
                    Log.e(TAG, "onPostExecute: VideoUrl:" + videoUrl);
                    if (videoUrl == null || videoUrl.equals("")) {
                        if (musicallyDelegate != null) {
                            Log.e(TAG, "onSuccess: 2.0" + musicallyDelegate);
                            musicallyDelegate.OnFailure(tryagain);
                        }
                        return;

                    }
                    CommonModel igtvModel = new CommonModel();
                    igtvModel.setImagePath(modelMitron.getProps().getPageProps().getVideo().getThumbUrl());
                    igtvModel.setVideoPath(modelMitron.getProps().getPageProps().getVideo().getVideoUrl());
                    igtvModel.setTitle(String.valueOf(System.currentTimeMillis()));
                    String stringBuilder2 = videoUniquePath + ".mp4";
                    igtvModel.setVideoUniquePath(stringBuilder2);
                    if (musicallyDelegate != null) {
                        musicallyDelegate.OnSuccess(igtvModel);
                    }
                } else {
                    if (musicallyDelegate != null) {
                        Log.e(TAG, "onSuccess: 2.0" + musicallyDelegate);
                        musicallyDelegate.OnFailure(tryagain);
                    }
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void downloadzili(String str, MusicallyDelegate musicallyDelegate) {
        try {
            WebView g = new WebView(App.getContext());
            g.getSettings().setJavaScriptEnabled(true);
            g.setWebChromeClient(new WebChromeClient() {
                @Override
                public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                    Log.e(TAG, "onConsoleMessage: " + consoleMessage.message());
                    if (!consoleMessage.message().startsWith("MAGIC")) {
                        return false;
                    }
                    videoUrl = Jsoup.parse(consoleMessage.message().substring(5)).getElementsByTag("video").eq(0).attr("src");
                    Log.e(TAG, "onConsoleMessage: " + consoleMessage.message().substring(5));
                    Log.e(TAG, "onConsoleMessage: my:" + videoUrl);

                    CommonModel igtvModel = new CommonModel();
                    igtvModel.setImagePath(videoUrl);
                    igtvModel.setVideoPath(videoUrl);
                    igtvModel.setTitle(String.valueOf(System.currentTimeMillis()));
                    String stringBuilder2 = videoUniquePath + ".mp4";
                    igtvModel.setVideoUniquePath(stringBuilder2);
                    if (musicallyDelegate != null) {
                        musicallyDelegate.OnSuccess(igtvModel);
                    }
                    return true;
                }
            });
            g.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            view.loadUrl("javascript:console.log('MAGIC'+document.getElementsByTagName('html')[0].innerHTML);");

                        }
                    }, 1000);
                }
            });
            g.loadUrl(str);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void downloadmoj(MusicallyDelegate musicallyDelegate) {
        try {
            String path = "";
            path = Environment.getExternalStorageDirectory() + "/Moj-Media/video/";


            File file = new File(path);
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

                    for (File file3 : listFiles) {
                        if (!file3.isDirectory() && !file3.getAbsolutePath().contains(".nomedia")) {
                            Log.e(TAG, "getMusicallyUrl 1: " + file3.getAbsolutePath());
                            Log.e(TAG, "getMusicallyUrl 2: " + file3.getName());
                            videoUrl = "https://cdn3.sharechat.com/" + file3.getName().replace("_watermarked", "_compressed_fast");
                            Log.e(TAG, "getMusicallyUrl 3: " + videoUrl);
                            CommonModel igtvModel = new CommonModel();
                            igtvModel.setImagePath(videoUrl);
                            igtvModel.setVideoPath(videoUrl);
                            igtvModel.setTitle(String.valueOf(System.currentTimeMillis()));
                            String stringBuilder2 = videoUniquePath + ".mp4";
                            igtvModel.setVideoUniquePath(stringBuilder2);
                            if (musicallyDelegate != null) {
                                musicallyDelegate.OnSuccess(igtvModel);
                            }
                            break;
                        }

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void downloadLikee(String str, MusicallyDelegate musicallyDelegate) {
        new AsyncTask<String, Void, Document>() {
            Document likeeDoc;


            @Override
            protected void onPostExecute(Document result) {
                super.onPostExecute(result);
                try {
                    videoUrl = result.select("a.without_watermark").last().attr("href");
                    if (videoUrl.equals("") || videoUrl == null) {
                        if (musicallyDelegate != null) {
                            Log.e(TAG, "onSuccess: 2.0" + musicallyDelegate);
                            musicallyDelegate.OnFailure(tryagain);
                        }
                    } else {
                        CommonModel igtvModel = new CommonModel();
                        igtvModel.setImagePath(videoUrl);
                        igtvModel.setVideoPath(videoUrl);
                        igtvModel.setTitle(String.valueOf(System.currentTimeMillis()));
                        String stringBuilder2 = videoUniquePath + ".mp4";
                        igtvModel.setVideoUniquePath(stringBuilder2);
                        if (musicallyDelegate != null) {
                            musicallyDelegate.OnSuccess(igtvModel);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    if (musicallyDelegate != null) {
                        musicallyDelegate.OnFailure(tryagain);
                    }
                }
            }

            @Override
            protected Document doInBackground(String... strings) {
                try {
                    String str1 = "com";
                    String str2 = str;
                    if (str2.contains(str1)) {
                        str2 = str2.replace(str1, "video");
                    }
                    likeeDoc = Jsoup.connect("https://likeedownloader.com/results").data("id", str2).post();

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, "doInBackground: Error");
                }
                return likeeDoc;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private String getsnackurl(String str) {
        String sortkey = str.substring(str.lastIndexOf('/') + 1);

        String str3 = "android";
        String str4 = "8c46a905";
        StringBuilder stringBuilder = new StringBuilder("ANDROID_");
        stringBuilder.append(Settings.Secure.getString(App.getContext().getContentResolver(), "android_id"));
        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayList<String> arrayList2 = new ArrayList<String>();
        arrayList.add("mod=OnePlus(ONEPLUS A5000)");
        arrayList.add("lon=0");
        arrayList.add("country_code=in");
        String stringBuilder2 = "did=" +
                stringBuilder;
        arrayList.add(stringBuilder2);
        arrayList.add("app=1");
        arrayList.add("oc=UNKNOWN");
        arrayList.add("egid=");
        arrayList.add("ud=0");
        arrayList.add("c=GOOGLE_PLAY");
        arrayList.add("sys=KWAI_BULLDOG_ANDROID_9");
        arrayList.add("appver=2.7.1.153");
        arrayList.add("mcc=0");
        arrayList.add("language=en-in");
        arrayList.add("lat=0");
        arrayList.add("ver=2.7");
        arrayList2.addAll(arrayList);
        stringBuilder = new StringBuilder();
        stringBuilder.append("shortKey=");
        stringBuilder.append(sortkey);
        arrayList2.add(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("os=");
        stringBuilder.append(str3);
        arrayList2.add(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("client_key=");
        stringBuilder.append(str4);
        arrayList2.add(stringBuilder.toString());
        try {
            Collections.sort(arrayList2);
        } catch (Exception e2) {
            e2.printStackTrace();
        }

        String m39025a = CPU.m39025a(App.getContext(), TextUtils.join("", arrayList2).getBytes(Charset.forName("UTF-8")), 0);
        String fullname = "mod=OnePlus(ONEPLUS A5000)&lon=0&country_code=in&did=ANDROID_" + Settings.Secure.getString(App.getContext().getContentResolver(), "android_id") + "&app=1&oc=UNKNOWN&egid=&ud=0&c=GOOGLE_PLAY&sys=KWAI_BULLDOG_ANDROID_9&appver=2.7.1.153&mcc=0&language=en-in&lat=0&ver=2.7&shortKey=" + sortkey + "&os=android&client_key=8c46a905&sig=" + m39025a;


        return "https://g-api.snackvideo.com/rest/bulldog/share/get?" + fullname;
    }


}
