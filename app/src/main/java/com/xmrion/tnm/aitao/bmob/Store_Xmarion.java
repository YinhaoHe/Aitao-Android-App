package com.xmrion.tnm.aitao.bmob;

import cn.bmob.v3.BmobObject;

/**
 * Created by 郑艳达 on 2017/7/6.
 * 商店类
 */

public class Store_Xmarion extends BmobObject {

    private String name;//店铺名称

    private String introduction;//店铺简介

    private Double income;//营业额

    private String information;//负责人信息

    private String userId;//所有人Id

    private String headImgURL;//店铺头像URL

    @Override
    public String toString() {
        return "name:" + name
                + "\nintroduction:" + introduction
                + "\ninformation:" + information
                + "\nuserId:" + userId
                + "\nincome:" + income;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHeadImgURL() {
        return headImgURL;
    }

    public void setHeadImgURL(String headImgURL) {
        this.headImgURL = headImgURL;
    }
}
