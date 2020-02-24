package com.xmrion.tnm.aitao.activity.goodsinfor;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xmrion.tnm.aitao.activity.order.OrderActivity_Xmarion;
import com.xmrion.tnm.aitao.R;
import com.xmrion.tnm.aitao.activity.editgoods.EditGoodsActivity_Xmarion;
import com.xmrion.tnm.aitao.activity.hot.HotFragment_Xmarion;
import com.xmrion.tnm.aitao.activity.seecomment.SeeCommentActivity_Xmarion;
import com.xmrion.tnm.aitao.activity.storegoods.StoreGoodsFragment_Xmarion;
import com.xmrion.tnm.aitao.fragment.index.MystoreFragment_Xmarion;
import com.xmrion.tnm.aitao.javabean.Comment_Xmarion;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郑艳达 on 2017/7/10.
 * 买家查看货品信息
 */

public class GoodsInformationFragment_Xmarion extends Fragment {

    public static List<Comment_Xmarion> comments;

    private String headImgUrl;

    private String name;

    private String intro;

    private int number;

    private String numberString;

    private String priceString;

    private double price;

    private String storeId;

    private String goodsId;

    @BindView(R.id.id_btn_storegoods_edit)
    public TextView editBtn;

    @BindView(R.id.id_btn_goodsinfor_seecomment)
    public TextView commentText;

    @OnClick(R.id.id_btn_storegoods_edit)
    public void edit() {
        Intent i = new Intent(getActivity(), EditGoodsActivity_Xmarion.class);
        i.putExtra("type", 2);
        i.putExtra("name", name);
        i.putExtra("introduction", intro);
        i.putExtra("price", price);
        i.putExtra("number", number);
        i.putExtra("goodsId", goodsId);
        i.putExtra("headImgUrl", headImgUrl);
        startActivity(i);
    }

    @OnClick(R.id.id_btn_goodsinfor_seecomment)
    public void seeComment() {
        startActivity(new Intent(getActivity(), SeeCommentActivity_Xmarion.class));
    }

    @BindView(R.id.id_img_goodsinfor)
    public ImageView headImg;

    @BindView(R.id.id_tv_goodsinfor_name)
    public TextView nameText;

    @BindView(R.id.id_tv_goodsinfor_number)
    public TextView numberText;

    @BindView(R.id.id_tv_goodsinfor_intro)
    public TextView introText;

    @BindView(R.id.id_tv_goodsinfor_price)
    public TextView priceText;

    @OnClick(R.id.id_btn_goodsinfor_back)
    public void back() {
        getActivity().finish();
    }

    @BindView(R.id.id_btn_goodsinfor_purchase)
    public TextView purchaseBtn;

    @OnClick(R.id.id_btn_goodsinfor_purchase)
    public void purchase() {
        Intent intent = new Intent(getActivity(), OrderActivity_Xmarion.class);
        intent.putExtra("name", name);
        intent.putExtra("storeId", storeId);
        intent.putExtra("price", price);
        intent.putExtra("goodsId", goodsId);
        intent.putExtra("number", number);
        intent.putExtra("headImgUrl", headImgUrl);
        startActivity(intent);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_goodsinfor, container, false);

        ButterKnife.bind(this, v);

        initialView();
        return v;
    }

    private void initialView() {
        Intent intent = getActivity().getIntent();
        headImgUrl = intent.getStringExtra("headImgUrl");
        Glide.with(this)
                .load(headImgUrl)
                .placeholder(R.drawable.img_defaultavatar)
                .crossFade()
                .into(headImg);
        name = intent.getStringExtra("name");
        intro = intent.getStringExtra("introduction");
        number = intent.getIntExtra("number", 0);
        numberString = "库存:" + number;
        price = intent.getDoubleExtra("price", (double) 0);
        priceString = "￥" + price;
        storeId = intent.getStringExtra("storeId");
        goodsId = intent.getStringExtra("goodsId");

        int style = intent.getIntExtra("style", 1);

        if (style == 1) {
            editBtn.setVisibility(View.VISIBLE);
            purchaseBtn.setVisibility(View.GONE);
            RelativeLayout.LayoutParams p =
                    (RelativeLayout.LayoutParams) commentText.getLayoutParams();
            p.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            commentText.setLayoutParams(p);
            comments = MystoreFragment_Xmarion.comments;
        } else {
            purchaseBtn.setVisibility(View.VISIBLE);
            editBtn.setVisibility(View.GONE);
            if (style == 2) {
                comments = StoreGoodsFragment_Xmarion.comments;
            } else if (style == 3) {
                comments = HotFragment_Xmarion.comments;
            }
        }
        nameText.setText(name);
        introText.setText(intro);
        numberText.setText(numberString);
        priceText.setText(priceString);
    }
}
