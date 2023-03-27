package com.example.dowloadprinter.retrofit;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public class ApiHttp {
    public interface ItfApiHttpDone {
        void onComplete(ResponseBody data, Throwable throwable);

    }

    public interface ItfApiHttpUrl {
        void onComplete(String realUrl, Throwable throwable);

    }

    public interface ItfApiCheckInternet {
        void onResult(Boolean isHas);

    }

    private interface ItfAPIHttp {
        String url = "";


        @GET("/{path}")
        Observable<ResponseBody> get(@Path(value = "path", encoded = true) String path);

        @GET
        Observable<ResponseBody> getUrl(@Url String url);

        @GET
        Observable<Response> getUrlReal(@Url String url);


        @GET("/{path}")
        Observable<ResponseBody> get(@Path(value = "path", encoded = true) String path, @QueryMap Map<String, String> query);

    }

    public static void getApi(String url, String path, Map<String, String> headers, ItfApiHttpDone itf) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (headers != null) {
            builder.addInterceptor(new Interceptor() {

                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    Request newRequest;

                    newRequest = request.newBuilder()
                            .headers(Headers.of(headers))
                            .build();
                    return chain.proceed(newRequest);
                }
            });
        }

        int HTTP_TIMEOUT = 30;
        OkHttpClient okHttpClient = builder.connectTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        retrofit.create(ItfAPIHttp.class).get(path)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    itf.onComplete(data, null);

                }, throwable -> {
                    itf.onComplete(null, throwable);

                }).isDisposed();

    }

    public static void getApi(String url, Map<String, String> headers, ItfApiHttpDone itf) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (headers != null) {
            builder.addInterceptor(new Interceptor() {

                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    Request newRequest;

                    newRequest = request.newBuilder()
                            .headers(Headers.of(headers))
                            .build();
                    return chain.proceed(newRequest);
                }
            });
        }


        OkHttpClient okHttpClient = builder.connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS).build();

        String urlSubHttp = url.replace("//", "..");

        String baseUrl = url;
        int idxPathFirst = urlSubHttp.indexOf("/");
        int idxQuestion = urlSubHttp.indexOf("?");

        /** TH
         *      path has '/'
         * */

        if (idxPathFirst > 0) {
            baseUrl = url.substring(0, idxPathFirst + 1);
        }
        /** TH
         *      path has '?' && (path hos not '/')
         * */
        if (idxQuestion > 0 && (idxPathFirst < 0 || idxPathFirst > idxQuestion)) {
            baseUrl = url.substring(0, idxQuestion + 1);
        }

        if (!baseUrl.startsWith("https://") && !baseUrl.startsWith("http://")) {
            baseUrl = "https://" + baseUrl;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        retrofit.create(ItfAPIHttp.class).getUrl(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    itf.onComplete(data, null);

                }, throwable -> {
                    itf.onComplete(null, throwable);

                }).isDisposed();

    }

    private static int REQUEST_TIMEOUT = 2;

    public static void getApi(String url, String path, Map<String, String> queryMap, Map<String, String> headers, ItfApiHttpDone itf) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (headers != null) {
            builder.addInterceptor(new Interceptor() {

                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    Request newRequest;

                    newRequest = request.newBuilder()
                            .headers(Headers.of(headers))
                            .build();
                    return chain.proceed(newRequest);
                }
            });
        }


        OkHttpClient okHttpClient = builder.connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        retrofit.create(ItfAPIHttp.class).get(path, queryMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    itf.onComplete(data, null);

                }, throwable -> {
                    itf.onComplete(null, throwable);

                }).isDisposed();

    }

    public static void getRealUrlAffterRender(String url, Map<String, String> headers, ItfApiHttpUrl itf) {
        String urlSubHttp = url.replace("//", "..");
        String baseUrl = url;
        int idxPathFirst = urlSubHttp.indexOf("/");
        int idxQusetion = urlSubHttp.indexOf("?");
        if (idxPathFirst > 0) {
            baseUrl = url.substring(0, idxPathFirst + 1);
        }
        if (idxQusetion > 0 && (idxPathFirst < 0 || idxPathFirst > idxQusetion)) {
            baseUrl = url.substring(0, idxQusetion + 1);
        }
        if (!baseUrl.startsWith("https://") && !baseUrl.startsWith("http://")) {
            baseUrl = "https://" + baseUrl;
        }

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (headers != null) {
            builder.addInterceptor(new Interceptor() {

                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    Request newRequest;

                    newRequest = request.newBuilder()
                            .headers(Headers.of(headers))
                            .build();
                    return chain.proceed(newRequest);
                }
            });
        }


        OkHttpClient okHttpClient = builder.connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        retrofit.create(ItfAPIHttp.class).getUrlReal(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    ;
                    itf.onComplete(data.networkResponse().request().url().toString(), null);

                }, throwable -> {
                    itf.onComplete(null, throwable);

                }).isDisposed();
    }
}
