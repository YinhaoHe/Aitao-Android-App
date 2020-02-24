package com.xmrion.tnm.aitao.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 郑艳达 on 2017/7/10.
 * 获取时间
 */

public class DateUtils_Xmarion {
    public static String getDate_YYYYMMDD() {
        Date now = new Date();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(now);
    }
}
