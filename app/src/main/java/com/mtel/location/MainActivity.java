package com.mtel.location;

import android.Manifest;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.mtel.location.base.BaseActivity;

import java.util.List;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setUpData() {

    }

    @OnClick({ R.id.btn_location})
    public void submit(View view) {
        switch (view.getId()) {

            case R.id.btn_location:
                requestRuntimePermission(new String[]{Manifest.permission.ACCESS_WIFI_STATE,Manifest.permission.ACCESS_FINE_LOCATION}, new PermissionListener() {
                    @Override
                    public void onGranted() {
                        Toast.makeText(MainActivity.this,"所有权限都同意了",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, LocationActivity.class));
                    }

                    @Override
                    public void onDenied(List<String> deniedPermission) {
                        for(String persmission:deniedPermission){
                            Toast.makeText(MainActivity.this,"被拒绝的权限："+persmission,Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                break;
        }
    }


}
