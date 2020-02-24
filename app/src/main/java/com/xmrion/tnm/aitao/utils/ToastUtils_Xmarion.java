package com.xmrion.tnm.aitao.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 郑艳达 on 2017/7/6.
 * Toast方法
 */

public class ToastUtils_Xmarion {

    public static void showToast(String s, Context context){
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}
