package com.xmrion.tnm.aitao.activity.seecomment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.xmrion.tnm.aitao.activity.goodsinfor.GoodsInformationFragment_Xmarion;
import com.xmrion.tnm.aitao.R;
import com.xmrion.tnm.aitao.adapter.CommentAdapter_Xmarion;
import com.xmrion.tnm.aitao.javabean.Comment_Xmarion;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郑艳达 on 2017/7/12.
 * 查看评论
 */

public class SeeCommentFragment_Xmarion extends Fragment {

    @BindView(R.id.id_rl_seecomment_nocomment)
    public RelativeLayout noCommentLayout;

    @BindView(R.id.id_lv_seecomment)
    public ListView commentList;

    private CommentAdapter_Xmarion adapter;

    private List<Comment_Xmarion> comments;

    @OnClick(R.id.id_btn_seecomment_back)
    public void back() {
        getActivity().finish();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_seecomment, container, false);
        ButterKnife.bind(this, v);
        initialView();
        return v;
    }

    private void initialView() {
        comments = GoodsInformationFragment_Xmarion.comments;
        if (comments == null) {
            noCommentLayout.setVisibility(View.VISIBLE);
            commentList.setVisibility(View.GONE);
        } else {
            if (getActivity() == null) {
                return;
            }
            adapter = new CommentAdapter_Xmarion(comments, getActivity());
            commentList.setAdapter(adapter);
        }
    }
}
