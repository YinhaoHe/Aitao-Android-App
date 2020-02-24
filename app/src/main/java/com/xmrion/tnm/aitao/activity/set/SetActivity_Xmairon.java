package com.xmrion.tnm.aitao.activity.set;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.xmrion.tnm.aitao.activity.root.AiTaoActivityWithSingleFragment_Xmarion;

/**
 * Created by 郑艳达 on 2017/7/9.
 * 设置
 */

public class SetActivity_Xmairon extends AiTaoActivityWithSingleFragment_Xmarion {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.initialView(new SetFragment_Xmarion());
    }
}
