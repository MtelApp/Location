package com.mtel.location;

import java.util.List;

/**
 * Created by action on 2017/1/11.
 */

public interface PermissionListener {
    void onGranted();

    void onDenied(List<String> deniedPermission);
}
