# *Android 6.0运行时权限简单封装和定位（系统的和google play service的）*

## *这里简单讲解一下：*
### *首先来讲一下android6.0运行时权限的简单封装做法：*

#### 每个项目应该都有一个BaseActivity，我们把运行时权限封装在BaseActivity，代码如下：
`
public static void requestRuntimePermission(String[] permissions, PermissionListener listener) {
    Activity topActivity = ActivityCollector.getTopActivity();
    if (topActivity == null) {
        return;
    }
    mListener = listener;
    List<String> permissionList = new ArrayList<>();
    for (String permission : permissions) {
        if (ContextCompat.checkSelfPermission(topActivity, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(permission);
        }
    }
    if (!permissionList.isEmpty()) {
        ActivityCompat.requestPermissions(topActivity, permissionList.toArray(new String[permissionList.size()]), 1);
    } else {
        mListener.onGranted();
    }
}
`
#### 简单说下上面的代码，创建一个permissionList来把一个或者多个权限add进去，通过checkSelfPermission检测权限再进行add进去再去请求权限，如果是6.0以下的就直接回调我们写好的接口mListener.onGranted();进行下一步
     然后就是请求权限的回调了：
`
@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    switch (requestCode) {
        case 1:
            if (grantResults.length > 0) {
                List<String> deniedPermission = new ArrayList<>();
                for (int i = 0; i < grantResults.length; i++) {
                    int grantResult = grantResults[i];
                    String permission = permissions[i];
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        deniedPermission.add(permission);
                    }
                }
                if (deniedPermission.isEmpty()) {
                    mListener.onGranted();
                } else {
                    mListener.onDenied(deniedPermission);
                }
            }
        default:
            break;
    }
}
`
#### 如果grantResults>0代表有请求权限，然后新建一个deniedPermissionList来装上回调失败的权限，如果deniedPermission回调权限为空就直接下一步工作mListener.onGranted();，不然就回调所失败的权限mListener.onDenied(deniedPermission);
     在MainActivity中使用，代码：
`
requestRuntimePermission(new String[]{Manifest.permission.ACCESS_WIFI_STATE,Manifest.permission.ACCESS_FINE_LOCATION}, new PermissionListener() {
    @Override
    public void onGranted() {
        Toast.makeText(MainActivity.this,"所有权限都同意了",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDenied(List<String> deniedPermission) {
        for(String persmission:deniedPermission){
            Toast.makeText(MainActivity.this,"被拒绝的权限："+persmission,Toast.LENGTH_SHORT).show();
        }

    }
});
`
### *android自带定位和google play service定位：*
### *自带定位：*
#### 获取系统的定位功能
`
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
`
#### 这里需要位置提供器才能获取到定位，通过监听方法可以不断的请求定位：
`
locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, myLocationListener);
`
#### 这样就可以用系统的定位功能了，由于在室内GPS比较难定位，下面使用google play services里面的定位功能
### *google play service定位：*

1. 在build.gradle里面添加google play service依赖
    `
    compile 'com.google.android.gms:play-services-location:9.0.2'
    `
2. 在AndroidManifest.xml添加
    `
    <meta-data
        android:name="com.google.android.gms.version"
        android:value="@integer/google_play_services_version" />
    `
   #### 和需要的权限等
   `
   <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
   <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
   `
3. 在MainActivity实现接口ConnectionCallbacks, OnConnectionFailedListener，并有回调方法
   `
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
   `
4. 在 onResume()写上checkPlayServices()
   `
   @Override
   public void onResume() {
       super.onResume();
       checkPlayServices();
   }
   `
5. call mGoogleApiClient.connect()在onStart()方法
   `
   @Override
   public void onStart() {
       super.onStart();
       if (mGoogleApiClient != null) {
           mGoogleApiClient.connect();
       }
   }
   `
6. 当google api connected成功了，displayLocation()方法在onConnected()之后会获取当前的位置信息
   `
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
   `
7. 拿到当前的经纬度之后，去call google的api获取相对应的地理位置，api：
   [http://maps.google.cn/maps/api/geocode/json](http://maps.google.cn/maps/api/geocode/json)
8. 更多详情请看demo





