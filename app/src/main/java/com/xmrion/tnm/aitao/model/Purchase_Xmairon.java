package com.xmrion.tnm.aitao.model;

import com.xmrion.tnm.aitao.bmob.Store_Xmarion;

/**
 * Created by 郑艳达 on 2017/7/10.
 * 交♂易接口
 */

public interface Purchase_Xmairon {
    void reduceUserMoney();

    void addStoreMoney();

    void addSaleUserMoney(Store_Xmarion store);
}
