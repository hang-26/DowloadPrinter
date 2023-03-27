package com.example.dowloadprinter.retrofit;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public class ApiHttpUrl {

    public interface ItfApiHttpUrl {
        void onComplete(String realUrl, Throwable throwable);
    }

    public interface ItfApiBody {
        void onComplete(String body, Throwable throwable);
    }


    private interface ItfAPIHttp {
        String url = "";

        @GET
        Observable<Response<ResponseBody>> getUrlReal(@Url String url);

        @GET
        Observable<Response<ResponseBody>> getUrl(@Url String url);
    }

    public static void getRealUrlAffterRender(String url, ItfApiHttpUrl itf) {
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

        OkHttpClient okHttpClient = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofit.create(ItfAPIHttp.class).getUrlReal(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    itf.onComplete(data.raw().request().url().toString(), null);

                }, throwable -> {
                    itf.onComplete(null, throwable);

                }).isDisposed();
    }

    public static void getApi(String url, ItfApiBody itf) {
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

        OkHttpClient okHttpClient = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.pinterest.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofit.create(ItfAPIHttp.class).getUrl(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    itf.onComplete(data.body().string(), null);

                }, throwable -> {
                    itf.onComplete(null, throwable);

                }).isDisposed();
    }

}
