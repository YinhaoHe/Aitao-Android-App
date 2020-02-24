package com.xmrion.tnm.aitao.activity.goodsinfor;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.xmrion.tnm.aitao.activity.root.AiTaoActivityWithSingleFragment_Xmarion;

/**
 * Created by 郑艳达 on 2017/7/10.
 * 买家查看货品信息
 */

public class GoodsInformationActivity_Xmarion extends AiTaoActivityWithSingleFragment_Xmarion {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.initialView(new GoodsInformationFragment_Xmarion());
    }
}
