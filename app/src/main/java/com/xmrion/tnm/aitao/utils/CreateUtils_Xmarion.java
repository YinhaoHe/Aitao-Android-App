package com.xmrion.tnm.aitao.utils;

import com.xmrion.tnm.aitao.bmob.Goods_Xmarion;
import com.xmrion.tnm.aitao.bmob.Store_Xmarion;

/**
 * Created by 郑艳达 on 2017/7/6.
 * 用于创建新对象
 */

public class CreateUtils_Xmarion {

    // TODO: 2017/7/6 添加头像
    public static Store_Xmarion createNewStore(String name,
                                               String introduction,
                                               String information,
                                               String userId) {
        Store_Xmarion store = new Store_Xmarion();

        store.setName(name);
        store.setIntroduction(introduction);
        store.setInformation(information);
        store.setUserId(userId);
        //设置初始收入为0
        store.setIncome((double) 0);

        return store;
    }
    //// TODO: 2017/7/6 添加头像
    public static Goods_Xmarion createNewGoods(String name,
                                               String introduction,
                                               Double price,
                                               Integer number
                                               ) {
        Goods_Xmarion goods = new Goods_Xmarion();

        goods.setName(name);
        goods.setIntroduction(introduction);
        goods.setPrice(price);
        goods.setNumber(number);
        //设置初始销量为0
        goods.setSellNum(0);
        return goods;
    }
}
