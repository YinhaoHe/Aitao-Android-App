package com.xmrion.tnm.aitao.fragment.dialog;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.xmrion.tnm.aitao.activity.comment.CommentActivity_Xmarion;
import com.xmrion.tnm.aitao.activity.hot.HotActivity_Xmarion;
import com.xmrion.tnm.aitao.activity.hot.HotFragment_Xmarion;
import com.xmrion.tnm.aitao.R;
import com.xmrion.tnm.aitao.activity.storegoods.StoreGoodsActivity_Xmarion;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郑艳达 on 2017/7/10.
 * 购买成功后弹出
 */

public class SuccessFragment extends DialogFragment {


    @OnClick(R.id.id_btn_success)
    public void comment() {
        String goodsId = getActivity().getIntent().getStringExtra("goodsId");
        Intent intent = new Intent(getActivity(), CommentActivity_Xmarion.class);
        intent.putExtra("goodsId", goodsId);
        startActivity(intent);
    }

    @OnClick(R.id.id_btn_success_return)
    public void returnStore() {
        if (HotFragment_Xmarion.flag) {
            startActivity(new Intent(getActivity(), HotActivity_Xmarion.class));
            HotFragment_Xmarion.flag = false;
        } else {
            startActivity(new Intent(getActivity(), StoreGoodsActivity_Xmarion.class));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View v = inflater.inflate(R.layout.fragment_success, container, false);
        ButterKnife.bind(this, v);

        return v;
    }
}
