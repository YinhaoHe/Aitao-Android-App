package com.xmrion.tnm.aitao.bmob;

import com.xmrion.tnm.aitao.javabean.Comment_Xmarion;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by 郑艳达 on 2017/7/6.
 * 货品类
 */

public class Goods_Xmarion extends BmobObject {

    private String name;//货品名称

    private String introduction;//货品介绍

    private Double price;//货品价格

    private Integer number;//货品数量

    private List<Comment_Xmarion> comments;//货品评论内容

    private String goodsImgUrl;//货品图片URL

    private Integer sellNum;//销量

    private String storeId;//店铺Id

    @Override
    public String toString() {
        return "name='" + name + '\n' +
                "\nintroduction='" + introduction +
                "\nprice=" + price +
                "\nnumber=" + number +
                "\nsellNum=" + sellNum +
                "\nstoreId='" + storeId;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getSellNum() {
        return sellNum;
    }

    public void setSellNum(Integer sellNum) {
        this.sellNum = sellNum;
    }

    public String getGoodsImgUrl() {
        return goodsImgUrl;
    }

    public void setGoodsImgUrl(String goodsImgUrl) {
        this.goodsImgUrl = goodsImgUrl;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public List<Comment_Xmarion> getComments() {
        return comments;
    }

    public void setComments(List<Comment_Xmarion> comments) {
        this.comments = comments;
    }
}
