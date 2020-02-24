package com.xmrion.tnm.aitao.activity.storeinfor;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.xmrion.tnm.aitao.activity.root.AiTaoActivityWithSingleFragment_Xmarion;

/**
 * Created by 郑艳达 on 2017/7/10.
 * 查看商品详情
 */

public class StoreInformationActivity_Xmaion extends AiTaoActivityWithSingleFragment_Xmarion {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.initialView(new StoreInformationFragment_Xmaion());
    }
}
