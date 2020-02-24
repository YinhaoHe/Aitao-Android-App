package com.xmrion.tnm.aitao.activity.register;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.xmrion.tnm.aitao.activity.root.AiTaoActivityWithSingleFragment_Xmarion;

/**
 * Created by 郑艳达 on 2017/7/7.
 * 注册Activity
 */

public class RegisterActivity_Xmarion extends AiTaoActivityWithSingleFragment_Xmarion {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.initialView(new RegisterFragment_Xmarion());
    }
}
