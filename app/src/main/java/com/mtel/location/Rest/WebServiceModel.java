package com.mtel.location.Rest;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mtel.location.bean.GoogleBean;
import com.mtel.location.bean.GoogleResult;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Action on 16/7/29.
 */

public class WebServiceModel {

    private static final int DEFAULT_TIMEOUT = 30;
    private static WebServiceModel mWebServiceModel;
    private GoogleApiService mGoogleApiService;
    private Retrofit retrofit;

    private Context mContext;

    public static WebServiceModel getInstance() {
        if (mWebServiceModel == null) {
            synchronized (WebServiceModel.class) {
                if (mWebServiceModel == null) {
                    mWebServiceModel = new WebServiceModel();
                }
            }
        }
        return mWebServiceModel;
    }

    public WebServiceModel() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient = builder
                .addInterceptor(interceptor)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();


        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(Api.GOOGLE_GEOCODE_API)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mGoogleApiService = retrofit2.create(GoogleApiService.class);

    }


    /**
     * 用于请求google的api返回相应的地理位置
     * @param resultCallBack
     * @param latlng
     * @param sensor
     * @param language
     */
    public void getGoogleGeocode(CallBack<GoogleResult<List<GoogleBean>>> resultCallBack, String latlng, String sensor, String language){
        doObservable(mGoogleApiService.getLocationGeocode(latlng, sensor,language),resultCallBack);
    }




    private void doObservable(Observable mObservable, CallBack result) {
        mObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new UIObserver<>(result));
    }



    private <T> void toSubscribe(Observable<T> o, Subscriber<T> s){
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

}
