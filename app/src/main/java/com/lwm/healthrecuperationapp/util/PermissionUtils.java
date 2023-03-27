package com.lwm.healthrecuperationapp.util;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

// 权限工具类
public class PermissionUtils {

    /**
     * 判断权限集合
     * true：有该权限
     * false：无该权限
     */
    public static boolean checkPermissions(Context context, String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(context, permission)) {
                return true;
            }
        }
        return false;
    }

    // 判断是否缺少权限
    private static boolean lacksPermission(Context mContext, String permission) {
        return ContextCompat.checkSelfPermission(mContext, permission) ==
                PackageManager.PERMISSION_DENIED;
    }
}
