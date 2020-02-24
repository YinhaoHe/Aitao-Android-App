package com.xmrion.tnm.aitao.application;

import android.app.Application;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.xmrion.tnm.aitao.bmob.User_Xmarion;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 郑艳达 on 2017/7/8.
 * 初始化全局变量
 */

public class MyApplication_Xmarion extends Application {

    private User_Xmarion user;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            User_Xmarion u = new User_Xmarion();
            u.setWalletId((String) msg.obj);
            u.update(user.getObjectId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        Log.e("钱包id绑定:", "成功");
                    }
                }
            });
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "edf90402072093522cd9e1ac525d3c00");
        user = BmobUser.getCurrentUser(User_Xmarion.class);
        loadCurrentUser();
    }

    /**
     * 在app进程开始之前运行此方法初始化本地用户信息
     * 如果没有登陆记录user为null
     */
    public void loadCurrentUser() {
        //获取user信息
        if (user != null) {
            BmobUser.fetchUserInfo(new FetchUserInfoListener<User_Xmarion>() {
                @Override
                public void done(User_Xmarion user_Xmarion, BmobException e) {
                    if (e == null) {
                        user = user_Xmarion;
                    }
                }
            });
        }
    }


    public void resetUserInfo() {
        if (user != null) {
            user = null;
        }
    }

    public User_Xmarion getUser() {
        return user;
    }

    public void setUser(User_Xmarion user) {
        this.user = user;
    }
}
