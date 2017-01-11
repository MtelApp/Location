package com.mtel.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.mtel.location.base.BaseActivity;
import com.mtel.location.bean.GoogleBean;
import com.mtel.location.bean.GoogleResult;

import java.util.List;

import butterknife.Bind;

/**
 * Created by action on 17/1/11.
 */

public class LocationActivity extends BaseActivity implements MainView.view,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener  {

    @Bind(R.id.location)
    TextView mLocation;
    @Bind(R.id.latlot)
    TextView mLatlot;

    private MainPersent mMainPersent;
    private LocationManager locationManager;
    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private String longitude;
    private String latitude;
    private Location mLastLocation;
    private String locationProvider;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_location;
    }

    @Override
    protected void setUpData() {
        mMainPersent = new MainPersent(this);
        if (checkPlayServices()) {
            // Building the GoogleApi client
            buildGoogleApiClient();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        checkPlayServices();
    }
    /**
     * Creating google api client object
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    /**
     * Method to verify google play services on the device
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(this,
                        "This device is not supported GooglePlayServices.", Toast.LENGTH_LONG)
                        .show();
            }
            return false;
        }
        return true;
    }

    private void getLocation() {
        try {
            locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
            //获取所有可用的位置提供器
            List<String> providers = locationManager.getProviders(true);
            if (providers.contains(LocationManager.GPS_PROVIDER)) {
                //如果是GPS
                locationProvider = LocationManager.GPS_PROVIDER;
            } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
                //如果是Network
                locationProvider = LocationManager.NETWORK_PROVIDER;
            } else {

                ToastUtils.showToast("没有可用的位置提供器");
                return;
            }

            Location mLocation = locationManager.getLastKnownLocation(locationProvider);

            if (null != mLocation) {
                getGeocoder(mLocation);
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, myLocationListener);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void getGeocoder(Location location){
        Log.e("getLongitude",""+location.getLatitude());
        Log.e("getLongitude",""+location.getLongitude());
        mLatlot.setText("" + (location.getLatitude() + "," + location.getLongitude()));

        // 正式的
        if (location != null) {
            mMainPersent.getGoogleGeocode(location.getLatitude() + "," + location.getLongitude(),"true", "zh-HK");
        }
        else {
            ToastUtils.showToast("还没获取定位信息");
        }



        // 某个香港地方的经纬度，供测试
//        mMainPersent.getGoogleGeocode("22.2860609970,114.1466776259","true", "zh-HK");

    }

    private LocationListener myLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            mLastLocation = location;

            getGeocoder(mLastLocation);
//            locationManager.removeUpdates(myLocationListener);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @Override
    public void onGoogleSuccess(Object result) {
        GoogleResult<List<GoogleBean>> list = (GoogleResult<List<GoogleBean>>) result;
        if (list.getStatus().equals("OK")) {
            mLocation.setText(list.getResults().get(0).getFormatted_address().toString());
        }

    }

    /**
     * Method to display the location on UI
     */
    private void displayLocation() {

        try {
            mLastLocation = LocationServices.FusedLocationApi
                    .getLastLocation(mGoogleApiClient);

            if (mLastLocation != null) {

                getGeocoder(mLastLocation);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        displayLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        ToastUtils.showToast(connectionResult.getErrorMessage());
    }
    @Override
    public void onGoogleFail(String result) {
        ToastUtils.showToast(result);
    }

    @Override
    public void showLoading() {
        showProgressDialog();
    }

    @Override
    public void hideLoading() {
        hideProgressDialog();
    }
}
