package com.lwm.healthrecuperationapp.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.lwm.healthrecuperationapp.R;
import com.lwm.healthrecuperationapp.util.FlashUtils;

public class FlashActivity extends AppCompatActivity {

    private ImageButton mImageButtonCloseOpen, mImageButtonSos;
    public static final int OPEN_LIGHT = 0x0010;
    public static final int CLOSE_LIGHT = 0x0020;
    private FlightThread flightThread;
    FlashUtils utils;
    View sview;
    private boolean isIconChange = true, isSosIconChange = true;

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case OPEN_LIGHT:
                    utils.open();
                    sview.setBackgroundResource(R.drawable.fopen);
                    break;
                case CLOSE_LIGHT:
                    utils.close();
                    sview.setBackgroundResource(R.drawable.fclose);
                    break;
                case 1000:

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.flash_title);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowCustomEnabled(true);
        }
        utils = new FlashUtils(this);
        sview = findViewById(R.id.bckg);
        mImageButtonCloseOpen = findViewById(R.id.image_button_close_open); // 手电筒
        mImageButtonSos = findViewById(R.id.image_button_sos); // SOS
        flightThread = new FlightThread();
        flightThread.start();
        mImageButtonCloseOpen.setBackgroundResource(R.drawable.buttonclose);
        mImageButtonSos.setBackgroundResource(R.drawable.sosclose);

        mImageButtonCloseOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkFlashlight(view)) {
                    if (isIconChange) {
                        utils.open(); // 打开手电筒
                        sview.setBackgroundResource(R.drawable.fopen);
                        mImageButtonCloseOpen.setBackgroundResource(R.drawable.buttonopen);
                        isIconChange = false;
                        // 点击打开手电筒按钮时，关闭 SOS照明
                        isStart = false;
                        mImageButtonSos.setBackgroundResource(R.drawable.sosclose);
                        isSosIconChange = true;
                    } else {
                        utils.close();
                        sview.setBackgroundResource(R.drawable.fclose);
                        mImageButtonCloseOpen.setBackgroundResource(R.drawable.buttonclose);
                        isIconChange = true;
                    }
                }
            }
        });

        mImageButtonSos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkFlashlight(view)) {
                    if (isSosIconChange) {
                        // 开始循环
                        mImageButtonSos.setBackgroundResource(R.drawable.sosopen);
                        isStart = true;
                        isSosIconChange = false;
                        // 点击 SOS照明时，关闭手电筒照明
                        mImageButtonCloseOpen.setBackgroundResource(R.drawable.buttonclose);
                        isIconChange = true;
                    } else {
                        isStart = false;
                        utils.close();
                        sview.setBackgroundResource(R.drawable.fclose);
                        mImageButtonSos.setBackgroundResource(R.drawable.sosclose);
                        isSosIconChange = true;
                    }
                }
            }
        });
    }

    // 检测当前设备是否配置闪光灯
    private boolean checkFlashlight(View view) {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            Toast.makeText(this, "当前设备没有闪光灯", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    boolean isStart = false;
    int i = 0;

    class FlightThread extends Thread {
        @Override
        public void run() {
            while (true) {
                while (isStart) {
                    if (i++ % 2 == 0) {
                        mHandler.obtainMessage(OPEN_LIGHT).sendToTarget();
                    } else {
                        mHandler.obtainMessage(CLOSE_LIGHT).sendToTarget();
                    }
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: // 返回键的id
                this.finish();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}