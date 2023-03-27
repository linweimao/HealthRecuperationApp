package com.lwm.healthrecuperationapp.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.core.app.ActivityCompat;

public class CallPhoneUtil {

    private static String mPhoneNumber;

    public static void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    // 判断是否拥有此权限
    public static void checkPermission(Context context, String permission) {
        if (PermissionUtils.checkPermissions(context, permission)) {
            // 有此权限
            call(context);
        } else {
            ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, 1);
        }
    }

    // 拨打电话
    public static void call(Context context) {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + mPhoneNumber));
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}