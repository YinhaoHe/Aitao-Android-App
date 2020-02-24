package com.xmrion.tnm.aitao.activity.aboutus;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xmrion.tnm.aitao.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郑艳达 on 2017/7/9.
 * aboutUs
 */

public class AboutUsFragment_Xmairon extends Fragment {

    @OnClick(R.id.id_btn_us_back)
    public void back(){
        getActivity().finish();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_us, container, false);

        ButterKnife.bind(this,v);
        return v;
    }
}
