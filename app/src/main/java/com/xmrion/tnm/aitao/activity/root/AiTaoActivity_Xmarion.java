package com.xmrion.tnm.aitao.activity.root;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;

/**
 * Created by 郑艳达 on 2017/7/8.
 * app基础Activity
 * 完成app中所有Activity需要进行的相同的工作
 */

public class AiTaoActivity_Xmarion extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "edf90402072093522cd9e1ac525d3c00");
        ButterKnife.bind(this);
    }
}
