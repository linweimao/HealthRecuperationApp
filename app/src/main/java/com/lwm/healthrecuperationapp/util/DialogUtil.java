package com.lwm.healthrecuperationapp.util;


import androidx.appcompat.app.AlertDialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.lwm.healthrecuperationapp.R;

public class DialogUtil {

    private volatile static DialogUtil sDialogUtil;
    private String mPhoneNumber;
    private TextView mTvDialogMessage, mTvDialogNegative, mTvDialogPositive;
    private DialogOnClick mDialogOnClick;

    private DialogUtil() {

    }

    public static DialogUtil getInstance() {
        if (sDialogUtil == null) {
            synchronized (DialogUtil.class) {
                if (sDialogUtil == null) {
                    sDialogUtil = new DialogUtil();
                }
            }
        }
        return sDialogUtil;
    }

    /**
     * 初始化构建 Dialog
     */
    public void createDialog(Context context, View view, String phoneNumber, DialogOnClick dialogOnClick) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        this.mPhoneNumber = phoneNumber;
        this.mDialogOnClick = dialogOnClick;
        showDialog(dialog, view);
    }

    private void showDialog(AlertDialog.Builder dialog, View view) {
        AlertDialog callPhoneDialog = dialog.setView(view).setCancelable(true).create();
        callPhoneDialog.show();
        mTvDialogMessage = view.findViewById(R.id.tv_contact_us_dialog_message);
        mTvDialogNegative = view.findViewById(R.id.tv_dialog_negative);
        mTvDialogPositive = view.findViewById(R.id.tv_dialog_positive);
        mTvDialogMessage.setText(String.format(view.getResources().getString(R.string.my_contact_us_dialog_message), mPhoneNumber));
        mTvDialogNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhoneDialog.dismiss();
            }
        });
        mTvDialogPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhoneDialog.dismiss();
                mDialogOnClick.PositiveOnClick();
            }
        });
    }

    public interface DialogOnClick {
        void PositiveOnClick();

        void NegativeOnClick();
    }
}