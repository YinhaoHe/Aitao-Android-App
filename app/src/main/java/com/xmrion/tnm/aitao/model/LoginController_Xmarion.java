package com.xmrion.tnm.aitao.model;

import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

import com.xmrion.tnm.aitao.activity.index.MainActivity_Xmarion;
import com.xmrion.tnm.aitao.application.MyApplication_Xmarion;
import com.xmrion.tnm.aitao.bmob.User_Xmarion;
import com.xmrion.tnm.aitao.bmob.Wallet_Xmarion;
import com.xmrion.tnm.aitao.utils.ToastUtils_Xmarion;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 郑艳达 on 2017/7/7.
 * login控制
 */

public class LoginController_Xmarion implements LoginModel_Xmarion {

    private String password;

    private String phoneNum;

    private String verCode;

    private Activity activity;

    public LoginController_Xmarion(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void login(final TextView tv) {
        tv.setClickable(false);
        BmobUser.loginByAccount(phoneNum, password, new LogInListener<User_Xmarion>() {
            @Override
            public void done(User_Xmarion user, BmobException e) {

                if (e == null) {
                    MyApplication_Xmarion app = (MyApplication_Xmarion) activity.getApplication();
                    app.setUser(user);
                    app.loadCurrentUser();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils_Xmarion.showToast("登陆成功", activity);
                            activity.finish();
                            activity.startActivity(new Intent(activity, MainActivity_Xmarion.class));
                        }
                    });
                } else {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setClickable(true);
                            ToastUtils_Xmarion.showToast("登陆失败", activity);
                        }
                    });
                }

            }
        });
    }

    @Override
    public void register() {
        Wallet_Xmarion wallet = new Wallet_Xmarion();
        wallet.setMoney((double) 0);
        wallet.save(new SaveListener<String>() {
            @Override
            public void done(String id, BmobException e) {
                User_Xmarion user = new User_Xmarion();
                user.setPassword(password);
                user.setMobilePhoneNumber(phoneNum);
                user.setUsername(phoneNum);
                user.setWalletId(id);
                user.signOrLogin(verCode, new SaveListener<User_Xmarion>() {
                    @Override
                    public void done(final User_Xmarion user, BmobException e) {
                        final String s = (e == null) ? "注册成功" : "注册失败";
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils_Xmarion.showToast(s, activity);
                            }
                        });
                    }
                });
            }
        });

    }

    @Override
    public void sendVerCode() {
        BmobSMS.requestSMSCode(phoneNum, "taonimei", new QueryListener<Integer>() {
            @Override
            public void done(Integer smsId, BmobException e) {
                final String s = (e == null) ? "发送成功" : "发送失败";
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils_Xmarion.showToast(s, activity);
                    }
                });
            }
        });
    }

    @Override
    public void resetPassword() {
        BmobUser.resetPasswordBySMSCode(verCode, password, new UpdateListener() {

            @Override
            public void done(BmobException e) {
                final String s = (e == null) ? "重置成功" : "重置失败";
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils_Xmarion.showToast(s, activity);
                    }
                });
            }
        });
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getVerCode() {
        return verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode;
    }
}
