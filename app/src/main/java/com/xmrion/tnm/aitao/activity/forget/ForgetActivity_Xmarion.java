package com.xmrion.tnm.aitao.activity.forget;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.xmrion.tnm.aitao.activity.root.AiTaoActivityWithSingleFragment_Xmarion;

/**
 * Created by 郑艳达 on 2017/7/7.
 * 忘记密码Activity
 */

public class ForgetActivity_Xmarion extends AiTaoActivityWithSingleFragment_Xmarion {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.initialView(new ForgetFragment_Xmarion());
    }
}
