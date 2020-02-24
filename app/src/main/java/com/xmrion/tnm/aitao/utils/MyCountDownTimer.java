package com.xmrion.tnm.aitao.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by 郑艳达 on 2017/7/13.
 * 验证码计时器
 */

public class MyCountDownTimer extends CountDownTimer {

    private TextView tv;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public MyCountDownTimer(long millisInFuture, long countDownInterval, TextView tv) {
        super(millisInFuture, countDownInterval);
        this.tv = tv;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        tv.setText("还有" + millisUntilFinished / 1000 + "秒可再次发送");
    }

    @Override
    public void onFinish() {
        tv.setText("点击发送");
        tv.setClickable(true);
    }
}
