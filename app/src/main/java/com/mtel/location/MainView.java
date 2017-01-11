package com.mtel.location;

import com.mtel.location.base.BaseView;

/**
 * Created by action on 17/1/11.
 */

public class MainView {

    interface view<T> extends BaseView {
        void onGoogleSuccess(T result);
        void onGoogleFail(String result);
    }
    interface presenter{
        void getGoogleGeocode(String latlng, String sensor, String language);
    }
}
