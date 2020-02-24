package com.xmrion.tnm.aitao.javabean;

/**
 * Created by 郑艳达 on 2017/7/6.
 * 购买记录
 */

public class PurchaseRecord_Xmarion {

    private String goodsName;//购买货物的名字

    private String goodsId;//购买的货物的Id

    private String imgUrl;//购买货物的图片url

    private String date;//购买日期

    private Double price;//购买价格

    private String address;//收获地址

    private String userName;//用户姓名

    private String storeId;//店铺Id

    public PurchaseRecord_Xmarion(String goodsName, String goodsId,
                                  String imgUrl, String date,
                                  Double price, String address,
                                  String userName, String storeId) {
        this.goodsName = goodsName;
        this.goodsId = goodsId;
        this.imgUrl = imgUrl;
        this.date = date;
        this.price = price;
        this.address = address;
        this.userName = userName;
        this.storeId = storeId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
