package com.lzlmall.b2b.app.vendor.framework.http;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.lzlmall.b2b.app.vendor.framework.http.upload.EngineUploadCallBack;
import com.lzlmall.b2b.app.vendor.framework.http.upload.ProgressRequestListener;
import com.lzlmall.b2b.app.vendor.framework.http.upload.UploadProgressRequstBody;
import com.lzlmall.b2b.app.vendor.framework.log.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Administrator on 2018/4/18.
 * 该类封装了get post put delet 请求
 */

public class OkHttpEngine implements IHttpEngine {

    private Handler mHandler = new Handler();


    private static OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.i("HTTP_Interceptor", message);
                }
            }).setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(new HeadersIntercept())
//            .addNetworkInterceptor(new HttpResponseIntercept())
            .build();



    @Override
    public void get(Context context, String url, Map<String, Object> params, final HttpEngineCallBack callBack, boolean isCach) {
        //get请求
        //拼接url
        HttpUrl.Builder httpUrlBuilder = HttpUrl.parse(url).newBuilder();

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            httpUrlBuilder.addQueryParameter(entry.getKey(), entry.getValue() + "");
        }

        HttpUrl httpUrl = httpUrlBuilder.build();

        String finalUrl = httpUrl.toString();

//        LogUtils.e("Get请求路径：", finalUrl);
        //判断是否有缓存
        // TODO: 2018/4/18


        Request request = new Request.Builder()
                .get()
                .tag(context)
                .url(finalUrl)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                //失败   不在子线程中
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onError(e);
                    }
                });

            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                //成功
                //解析一response下
                if (response.isSuccessful()) {

                    try {
                        final String resultJson = response.body().string();
                        LogUtils.json("Get返回结果：", resultJson);
                        //成功
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onSuccess(resultJson);
                            }
                        });

                    } catch (IOException e) {
                        //失败
                        onFailure(call, e);
                        e.printStackTrace();
                    }

                } else {
                    //失败
                    onFailure(call, new IOException("网络异常！"));

                }

            }
        });

    }

    @Override
    public void post(Context context, String url, Map<String, Object> params, final HttpEngineCallBack callBack, boolean isCach) {
        //post请求
        final String finalUrl = HttpUtils.jointParams(url, params);  //打印
        LogUtils.e("Post请求路径：", finalUrl);
        // 加密参数
//        String encryptParam = AESEncrypt.encrypt(params);


        Request request = new Request.Builder()
                .tag(context)
                .url(url)
                .post(createPostBody(params))
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                //失败   不在子线程中
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onError(e);
                    }
                });
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                //解析一response下
//
                if (response.isSuccessful()) {

                    try {
                        final String resultJson = response.body().string();
                        LogUtils.json("Get返回结果：", resultJson);
                        //成功
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("onrepomnse: ", resultJson);
                                callBack.onSuccess(resultJson);
                            }
                        });

                    } catch (IOException e) {
                        //失败
                        onFailure(call, e);
                        e.printStackTrace();
                    }

                } else {
                    //失败
                    onFailure(call, new IOException("网络异常！"));

                }

            }
        });


    }

    private RequestBody createPostBody(Map<String, Object> params) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MediaType.parse("multipart/form-data"));
        if (!params.isEmpty()) {
            addParams(builder, params);
        }
        return builder.build();
    }

    private void addParams(MultipartBody.Builder builder, JSONObject params) {
        builder.addFormDataPart("data", params.toString());
    }

    //拼接参数
    private void addParams(MultipartBody.Builder builder, Map<String, Object> params) {
        JSONObject jsonObject = new JSONObject();;
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                Object value = params.get(key);
                if (value instanceof File) {
                    //是文件
                    File file = (File) value;
                    //可能是image  text  等 所以要猜测文件
                    builder.addFormDataPart(key
                            , file.getName()
                            , RequestBody.create(MediaType.parse(guessMimeType(file.getAbsolutePath())), file));

                } else if (value instanceof List) {
                    //提交的是文件集合
                    try {
                        List<File> listFiles = (List<File>) value;
                        for (File file : listFiles) {
                            builder.addFormDataPart(key, file.getName(), RequestBody
                                    .create(MediaType.parse(guessMimeType(file
                                            .getAbsolutePath())), file));
                        }
                    } catch (Exception e) {
                        //多文件上传失败
                        e.printStackTrace();
                    }

                } else {
                    //普通的post请求
                    String finalValue = String.valueOf(value);
//                    builder.addFormDataPart(key, finalValue + "");
                    // key+value

                    try {
                        jsonObject.put(key, finalValue);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            }

            builder.addFormDataPart("data", jsonObject.toString());
            Log.e( "addParams: ", jsonObject.toString());
        }
    }


    /**
     * 猜测文件类型
     *
     * @param path
     * @return
     */
    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }

        return contentTypeFor;
    }

    @Override
    public void put(Context context, String url, Map<String, Object> params, final HttpEngineCallBack callBack, boolean isCach) {

        RequestBody body = craetePostJsonBody(params);
        Request request = new Request.Builder()
                .tag(context)
                .url(url)
                .put(body)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                //失败   不在子线程中
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onError(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {

                    try {
                        final String resultJson = response.body().string();
                        LogUtils.json("put返回结果：", resultJson);
                        //成功
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("onrepomnse: ", resultJson);
                                callBack.onSuccess(resultJson);
                            }
                        });

                    } catch (IOException e) {
                        //失败
                        onFailure(call, e);
                        e.printStackTrace();
                    }

                } else {
                    //失败
                    onFailure(call, new IOException("网络异常！"));

                }

            }
        });
    }


    @Override
    public void delete(Context context, String url, Map<String, Object> params, final HttpEngineCallBack callBack, boolean isCach) {
//        RequestBody body = craetePostJsonBody(params);
        StringBuilder stringBuilder = new StringBuilder(url);
        for (Map.Entry<String, Object> en : params.entrySet()) {
            stringBuilder.append("/").append(en.getKey());
        }
        url = stringBuilder.toString();

        Request request = new Request.Builder()
                .tag(context)
                .url(url)
                .delete()
//                .delete(body)
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                //失败   不在子线程中
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onError(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {

                    try {
                        final String resultJson = response.body().string();
                        LogUtils.json("delete返回结果：", resultJson);
                        //成功
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("onrepomnse: ", resultJson);
                                callBack.onSuccess(resultJson);
                            }
                        });

                    } catch (IOException e) {
                        //失败
                        onFailure(call, e);
                        e.printStackTrace();
                    }

                } else {
                    //失败
                    onFailure(call, new IOException("网络异常！"));

                }

            }
        });


    }

    /**
     * post 提交json 格式 数据
     *
     * @param context
     * @param url
     * @param params
     * @param callBack
     * @param isCach
     */
    @Override
    public void postJson(Context context, String url, Map<String, Object> params, final HttpEngineCallBack callBack, boolean isCach) {
        RequestBody requestBody = craetePostJsonBody(params);
        Request request = new Request.Builder()
                .url(url)
                .tag(context)
                .post(requestBody)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onError(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {

                    try {
                        final String resultJson = response.body().string();
                        LogUtils.json("postJson返回结果：", resultJson);
                        //成功
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("onrepomnse: ", resultJson);
                                callBack.onSuccess(resultJson);
                            }
                        });

                    } catch (IOException e) {
                        //失败
                        onFailure(call, e);
                        e.printStackTrace();
                    }

                } else {
                    //失败
                    onFailure(call, new IOException("网络异常！"));

                }

            }
        });


    }

    /**
     * @param context
     * @param url
     * @param params
     * @param callBack
     */
    @Override
    public void upload(Context context, String url, Map<String, Object> params, final EngineUploadCallBack callBack) {
        final String jointUrl = HttpUtils.jointParams(url, params);  //打印
        LogUtils.e("上传路径：", jointUrl);

        RequestBody requestBody = createPostBody(params);


        Request request = new Request.Builder()
                .tag(context)
                .url(url)
                .post(new UploadProgressRequstBody(requestBody, new ProgressRequestListener() {
                    @Override
                    public void onRequestProgress(final long bytesWritten, final long contentLength, boolean done) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onResponse(bytesWritten, contentLength);
                                if (bytesWritten / contentLength == 1) {
                                    callBack.onComplete();
                                }
                            }
                        });

                    }
                }))
                .build();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(360, TimeUnit.SECONDS)
                .readTimeout(360, TimeUnit.SECONDS)
                .writeTimeout(360, TimeUnit.SECONDS)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onError(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String resultJson = response.body().string();
                LogUtils.e("json->", resultJson);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onSuccess(resultJson);
                    }
                });
            }
        });

    }

    @Override
    public void potsJsonJsonObject(Context context, String url, JSONObject params, final HttpEngineCallBack callBack, boolean isCach) {
        Request request = new Request.Builder()
                .tag(context)
                .url(url)
                .post(createPostjsonBody(params))
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                //失败   不在子线程中
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onError(e);
                    }
                });
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                //解析一response下
//
                if (response.isSuccessful()) {

                    try {
                        final String resultJson = response.body().string();
                        LogUtils.json("Get返回结果：", resultJson);
                        //成功
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("onrepomnse: ", resultJson);
                                callBack.onSuccess(resultJson);
                            }
                        });

                    } catch (IOException e) {
                        //失败
                        onFailure(call, e);
                        e.printStackTrace();
                    }

                } else {
                    //失败
                    onFailure(call, new IOException("网络异常！"));

                }

            }
        });

    }

    private RequestBody createPostjsonBody(JSONObject params) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MediaType.parse("multipart/form-data"));

            addParams(builder, params);

        return builder.build();
    }

    /**
     * 构建请求体
     *
     * @param params
     * @return
     */
    private RequestBody craetePostJsonBody(Map<String, Object> params) {
        //可以利用butterknife思想 自动生成一个jsonBean类
        String mode = null;
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            try {
                if ("accountAttribute".equals(entry.getKey())) {
                    // TODO: 2018/5/2  这里还有好多种
                    mode = entry.getKey();
                    continue;
                }

                jsonObject.put(entry.getKey(), entry.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String requestJson = jsonObject.toString();
        if (mode != null) {
            try {

                requestJson = jsonObject.put(mode, requestJson).toString();

                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    try {
                        if ("accountAttribute".equals(entry.getKey())) {
                            // TODO: 2018/5/2  这里还有好多种
                            continue;
                        }
                        jsonObject.remove(entry.getKey());
                        requestJson = jsonObject.toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        requestJson = parcejson(requestJson);
        LogUtils.json(requestJson);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestJson);
        return body;
    }


    private String parcejson(String requestJson) {
        String replace1 = requestJson.replace("\\", "");
        String replace2 = replace1.replace("\"{", "{");
        String replace3 = replace2.replace("}\"", "}");
        return replace3;
    }


    /**
     * 取消
     */
    public static void cancel(Context context) {
        Dispatcher dispatcher = mOkHttpClient.dispatcher();
        //
        List<Call> queuCalls = dispatcher.queuedCalls();
        synchronized (dispatcher) {
            for (Call call : queuCalls) {
                if (context.equals(call.request().tag())) {
                    call.cancel();
                }

            }

            List<Call> runCalls = dispatcher.runningCalls();
            for (Call runCall : runCalls) {
                if (context.equals(runCall.request().tag())) {
                    runCall.cancel();
                }
            }
        }
    }
}



