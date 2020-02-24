package com.xmrion.tnm.aitao.activity.comment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.xmrion.tnm.aitao.bmob.Goods_Xmarion;
import com.xmrion.tnm.aitao.R;
import com.xmrion.tnm.aitao.activity.storegoods.StoreGoodsActivity_Xmarion;
import com.xmrion.tnm.aitao.application.MyApplication_Xmarion;
import com.xmrion.tnm.aitao.javabean.Comment_Xmarion;
import com.xmrion.tnm.aitao.utils.DateUtils_Xmarion;
import com.xmrion.tnm.aitao.utils.ToastUtils_Xmarion;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 郑艳达 on 2017/7/10.
 * 评论界面
 */

public class CommentFragment_Xmarion extends Fragment {

    String goodsId;

    @BindView(R.id.id_et_edit)
    public EditText et;

    @BindView(R.id.id_tv_edit_title)
    public TextView titleText;

    @BindView(R.id.id_btn_edit_confirm)
    public TextView confirmText;

    @OnClick(R.id.id_btn_edit_confirm)
    public void confirm() {
        String content = et.getText().toString();
        if (content.equals("")) {
            ToastUtils_Xmarion.showToast("评论不能为空！", getActivity());
            return;
        }
        //获取goodsId
        goodsId = getActivity().getIntent().getStringExtra("goodsId");
        //获取用户信息
        MyApplication_Xmarion app = (MyApplication_Xmarion) getActivity().getApplication();
        Comment_Xmarion c = new Comment_Xmarion(content,
                app.getUser().getUsername(),
                DateUtils_Xmarion.getDate_YYYYMMDD());
        //更新评论内容
        Goods_Xmarion goods = new Goods_Xmarion();
        goods.add("comments", c);
        goods.update(goodsId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                final String s = e == null ? "评论成功!" : "评论失败!";
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils_Xmarion.showToast(s, getActivity());
                    }
                });
            }
        });
        back();
    }

    @OnClick(R.id.id_btn_edit_back)
    public void back() {
        startActivity(new Intent(getActivity(), StoreGoodsActivity_Xmarion.class));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit, container, false);
        ButterKnife.bind(this, v);
        initialView();
        return v;
    }

    private void initialView() {
        titleText.setText(getString(R.string.comment));
        confirmText.setText(getString(R.string.send));
        et.setHint(new SpannableString(getString(R.string.input_comment)));
    }
}
