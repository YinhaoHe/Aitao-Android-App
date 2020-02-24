package com.xmrion.tnm.aitao.activity.storegoods;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mingle.widget.LoadingView;
import com.xmrion.tnm.aitao.R;
import com.xmrion.tnm.aitao.activity.goodsinfor.GoodsInformationActivity_Xmarion;
import com.xmrion.tnm.aitao.activity.storeinfor.StoreInformationActivity_Xmaion;
import com.xmrion.tnm.aitao.adapter.StoreGoodsAdapter_Xmarion;
import com.xmrion.tnm.aitao.bmob.Goods_Xmarion;
import com.xmrion.tnm.aitao.javabean.Comment_Xmarion;
import com.xmrion.tnm.aitao.model.LoadingView_Xmaion;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 郑艳达 on 2017/7/9.
 * 货品查看
 */

public class StoreGoodsFragment_Xmarion extends Fragment implements LoadingView_Xmaion {

    private String storeName;
    private String storeId;

    private String url;

    //偷个懒,用个静态List实现评论的传递
    public static List<Comment_Xmarion> comments;

    private List<Goods_Xmarion> goodsData;

    private Handler handler = new Handler();

    private StoreGoodsAdapter_Xmarion adapter;

    @BindView(R.id.id_rl_storegoods_nogoods)
    public RelativeLayout nogoodsLayout;

    @BindView(R.id.id_btn_storegoods_refresh)
    public ImageView refreshBtn;

    @BindView(R.id.id_btn_storegoods_info)
    public FloatingActionButton inforBtn;

    @BindView(R.id.id_load_storegoods)
    public LoadingView loadingView;

    @BindView(R.id.id_lv_storegoods)
    public ListView goodsList;

    @BindView(R.id.id_tv_storegoods_title)
    public TextView title;

    @OnClick(R.id.id_btn_storegoods_back)
    public void back() {
        getActivity().finish();
    }

    @OnClick(R.id.id_btn_storegoods_refresh)
    public void refresh() {
        initialView();
    }

    @OnClick(R.id.id_btn_storegoods_info)
    public void getInfo() {
        Intent intent = getActivity().getIntent();
        Intent i = new Intent(getActivity(), StoreInformationActivity_Xmaion.class);
        i.putExtra("information", intent.getStringExtra("information"));
        i.putExtra("introduction", intent.getStringExtra("introduction"));
        if (url != null) {
            i.putExtra("headImgUrl", url);
        }
        i.putExtra("name", storeName);
        i.putExtra("type", 2);
        startActivity(i);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_storegoods, container, false);

        ButterKnife.bind(this, v);

        initialView();
        return v;
    }

    @Override
    public void initialView() {
        nogoodsLayout.setVisibility(View.GONE);
        Intent intent = getActivity().getIntent();
        url = intent.getStringExtra("headImgUrl");
        storeName = intent.getStringExtra("name");
        storeId = intent.getStringExtra("storeId");
        title.setText(storeName);
        loadingView.setVisibility(View.VISIBLE);
        goodsList.setVisibility(View.GONE);
        inforBtn.setVisibility(View.GONE);
        refreshBtn.setVisibility(View.GONE);
        setData();
    }

    @Override
    public void setData() {
        BmobQuery<Goods_Xmarion> bq = new BmobQuery<>();
        bq.setLimit(50);
        Log.e("storeId", storeId);
        bq.addWhereEqualTo("storeId", storeId);
        bq.findObjects(new FindListener<Goods_Xmarion>() {
            @Override
            public void done(List<Goods_Xmarion> list, BmobException e) {
                if (e == null) {
                    goodsData = list;
                    for (Goods_Xmarion g : goodsData) {
                        Log.e("货品信息加载:", g.toString());
                    }
                    reSetView();
                }
            }
        });
    }

    @Override
    public void reSetView() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                loadingView.setVisibility(View.GONE);
                inforBtn.setVisibility(View.VISIBLE);
                refreshBtn.setVisibility(View.VISIBLE);
                if (getActivity() == null) {
                    return;
                }
                if (goodsData.size() == 0) {
                    nogoodsLayout.setVisibility(View.VISIBLE);
                    goodsList.setVisibility(View.GONE);
                }
                adapter = new StoreGoodsAdapter_Xmarion(goodsData, getActivity());
                goodsList.setAdapter(adapter);
                goodsList.setVisibility(View.VISIBLE);
                goodsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getActivity(), GoodsInformationActivity_Xmarion.class);
                        intent.putExtra("name", goodsData.get(position).getName());
                        intent.putExtra("introduction", goodsData.get(position).getIntroduction());
                        intent.putExtra("number", goodsData.get(position).getNumber());
                        intent.putExtra("price", goodsData.get(position).getPrice());
                        intent.putExtra("storeId", goodsData.get(position).getStoreId());
                        intent.putExtra("goodsId", goodsData.get(position).getObjectId());
                        intent.putExtra("style", 2);
                        intent.putExtra("headImgUrl", goodsData.get(position).getGoodsImgUrl());
                        comments = goodsData.get(position).getComments();
                        startActivity(intent);
                    }
                });
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
