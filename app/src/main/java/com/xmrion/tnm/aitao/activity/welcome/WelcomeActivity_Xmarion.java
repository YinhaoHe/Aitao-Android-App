package com.xmrion.tnm.aitao.activity.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.xmrion.tnm.aitao.R;
import com.xmrion.tnm.aitao.activity.root.AiTaoActivity_Xmarion;
import com.xmrion.tnm.aitao.activity.index.MainActivity_Xmarion;
import com.xmrion.tnm.aitao.activity.login.LoginActivity_Xmarion;
import com.xmrion.tnm.aitao.application.MyApplication_Xmarion;

/**
 * Created by 郑艳达 on 2017/7/8.
 * 欢迎界面
 */

public class WelcomeActivity_Xmarion extends AiTaoActivity_Xmarion {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        handler.sendEmptyMessageDelayed(0, 2000);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            startApp();
            super.handleMessage(msg);
        }
    };

    private void startApp(){
        Intent intent = new Intent();
        MyApplication_Xmarion app = (MyApplication_Xmarion) getApplication();
        if (app.getUser() == null){
            Log.e("加载用户信息:","没有用户");
            intent.setClass(this, LoginActivity_Xmarion.class);
        }else {
            Log.e("加载用户信息:", app.getUser().getUsername());
            intent.setClass(this, MainActivity_Xmarion.class);
        }
        this.finish();
        startActivity(intent);
    }
}
