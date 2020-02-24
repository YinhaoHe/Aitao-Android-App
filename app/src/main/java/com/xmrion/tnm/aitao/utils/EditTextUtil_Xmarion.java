package com.xmrion.tnm.aitao.utils;

import android.widget.EditText;

import java.util.List;

/**
 * Created by 郑艳达 on 2017/7/7.
 * EditText检测方法
 */

public class EditTextUtil_Xmarion {

    /**
     * 用于网络请求前，检测布局中所有EditText内容是否都已经被填满
     * @param editTexts:传入的editText集
     * @return : 未填满返回false，否则返回true;
     */
    public static boolean checkInput(List<EditText> editTexts){
        for (EditText et :editTexts){
            if (et.getText().toString().equals("")){
                return false;
            }
        }
        return true;
    }
}
