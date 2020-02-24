package com.xmrion.tnm.aitao.bmob;

import cn.bmob.v3.BmobUser;

/**
 * Created by 郑艳达 on 2017/7/6.
 * 用于对接BmobUser的实体类
 */

public class User_Xmarion extends BmobUser {

    /*
     * BmobUser自带属性：
     * private String username;
     * private String password;
     * private String email;
     * private Boolean emailVerified;
     * private String sessionToken;
     * private String mobilePhoneNumber;
     * private Boolean mobilePhoneNumberVerified;
     *
     * 本应用使用手机号码进行登陆注册功能,只应用自带的password和mobilePhoneNumber
     * 属性
     */

//    api自带属性
//    private String username;  用户名 默认为手机号码
//    private String password;  用户密码
//    private String mobilePhoneNumber;  用户手机号码

    private String walletId;//钱包id

    private String headImgUrl;//用户头像URL

    private String storeId;//所属店铺id


    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }


    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }
}
