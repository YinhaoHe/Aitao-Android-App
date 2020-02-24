package com.xmrion.tnm.aitao.activity.root;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.xmrion.tnm.aitao.R;

/**
 * Created by 郑艳达 on 2017/7/9.
 * 带有Fragment的TnmActivity
 */

public class AiTaoActivityWithSingleFragment_Xmarion extends AiTaoActivity_Xmarion {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);
    }

    protected void initialView(Fragment singleFragment){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.id_fl_sf, singleFragment);
        ft.commit();
    }
}
