package com.xmrion.tnm.aitao.bmob;

import com.xmrion.tnm.aitao.javabean.PurchaseRecord_Xmarion;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by 郑艳达 on 2017/7/11.
 * 钱包
 * 管理购买记录、销售记录、充值记录
 */

public class Wallet_Xmarion  extends BmobObject{

    private Double money;

    private List<PurchaseRecord_Xmarion> purchaseRecords;//购买记录

    private List<PurchaseRecord_Xmarion> saleRecords;//销售记录

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public List<PurchaseRecord_Xmarion> getPurchaseRecords() {
        return purchaseRecords;
    }

    public void setPurchaseRecords(List<PurchaseRecord_Xmarion> purchaseRecords) {
        this.purchaseRecords = purchaseRecords;
    }

    public List<PurchaseRecord_Xmarion> getSaleRecords() {
        return saleRecords;
    }

    public void setSaleRecords(List<PurchaseRecord_Xmarion> saleRecords) {
        this.saleRecords = saleRecords;
    }
}
