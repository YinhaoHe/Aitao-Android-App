package com.xmrion.tnm.aitao.activity.seecomment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.xmrion.tnm.aitao.activity.root.AiTaoActivityWithSingleFragment_Xmarion;

/**
 * Created by 郑艳达 on 2017/7/12.
 * 查看评论
 */

public class SeeCommentActivity_Xmarion extends AiTaoActivityWithSingleFragment_Xmarion {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.initialView(new SeeCommentFragment_Xmarion());
    }
}
