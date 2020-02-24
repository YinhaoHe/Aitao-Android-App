package com.xmrion.tnm.aitao.activity.set;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xmrion.tnm.aitao.R;
import com.xmrion.tnm.aitao.activity.aboutus.AboutUsActivity_Xmairon;
import com.xmrion.tnm.aitao.activity.feedback.FeedbackActivity_Xmarion;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郑艳达 on 2017/7/9.
 * 设置Fragment
 */

public class SetFragment_Xmarion extends Fragment {


    @OnClick(R.id.id_btn_set_back)
    public void back() {
        getActivity().finish();
    }

    @OnClick(R.id.id_rl_set_developers)
    public void showDeveloper() {
        startActivity(new Intent(getActivity(), AboutUsActivity_Xmairon.class));
    }

    @OnClick(R.id.id_rl_set_feedback)
    public void feedBack() {
        startActivity(new Intent(getActivity(), FeedbackActivity_Xmarion.class));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_set, container, false);
        ButterKnife.bind(this, v);
        return v;
    }
}
