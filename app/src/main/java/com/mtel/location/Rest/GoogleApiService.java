package com.mtel.location.Rest;


import com.mtel.location.bean.GoogleBean;
import com.mtel.location.bean.GoogleResult;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Action on 16/7/29.
 */
public interface GoogleApiService {
    String header = "Content-Type: application/json";

    @GET("geocode/json")
    Observable<GoogleResult<List<GoogleBean>>> getLocationGeocode(@Query("latlng") String latlng, @Query("sensor") String sensor, @Query("language") String language);
}
