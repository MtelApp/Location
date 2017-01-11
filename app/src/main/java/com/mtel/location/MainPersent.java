package com.mtel.location;

import com.mtel.location.Rest.CallBack;
import com.mtel.location.Rest.WebServiceModel;
import com.mtel.location.bean.GoogleBean;
import com.mtel.location.bean.GoogleResult;

import java.util.List;

/**
 * Created by action on 17/1/6.
 */

public class MainPersent implements MainView.presenter {

    MainView.view mView;

    public MainPersent(MainView.view view) {
        this.mView = view;
    }

    @Override
    public void getGoogleGeocode(String latlng, String sensor, String language) {
        mView.showLoading();
        CallBack<GoogleResult<List<GoogleBean>>> callBack = new CallBack<GoogleResult<List<GoogleBean>>>() {
            @Override
            public void success(GoogleResult<List<GoogleBean>> result) {
                mView.hideLoading();
                mView.onGoogleSuccess(result);

            }

            @Override
            public void fail(String message) {
                if (null==mView) return;
                mView.hideLoading();
                ToastUtils.showToast(message);
            }
        };
        WebServiceModel.getInstance().getGoogleGeocode(callBack,latlng, sensor, language);
    }

}
