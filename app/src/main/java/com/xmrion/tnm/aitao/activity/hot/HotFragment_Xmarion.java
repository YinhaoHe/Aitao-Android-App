package com.xmrion.tnm.aitao.activity.hot;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mingle.widget.LoadingView;
import com.xmrion.tnm.aitao.adapter.StoreGoodsAdapter_Xmarion;
import com.xmrion.tnm.aitao.bmob.Goods_Xmarion;
import com.xmrion.tnm.aitao.R;
import com.xmrion.tnm.aitao.activity.goodsinfor.GoodsInformationActivity_Xmarion;
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
 * Created by 郑艳达 on 2017/7/12.
 * 热销商品
 */

public class HotFragment_Xmarion extends Fragment implements LoadingView_Xmaion {

    public static boolean flag = false;

    @OnClick(R.id.id_btn_hot_back)
    public void back() {
        getActivity().finish();
    }

    public static List<Comment_Xmarion> comments;

    private StoreGoodsAdapter_Xmarion adapter;

    @BindView(R.id.id_lv_hot)
    public ListView hotList;

    @BindView(R.id.id_load_hot)
    public LoadingView loadingView;

    private List<Goods_Xmarion> goods;

    private Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hot, container, false);
        ButterKnife.bind(this, v);
        initialView();
        return v;
    }

    @Override
    public void initialView() {
        loadingView.setVisibility(View.VISIBLE);
        hotList.setVisibility(View.GONE);
        setData();
    }

    @Override
    public void setData() {
        BmobQuery<Goods_Xmarion> bq = new BmobQuery<>();
        bq.setLimit(20);
        bq.order("-sellNum");
        bq.findObjects(new FindListener<Goods_Xmarion>() {
            @Override
            public void done(List<Goods_Xmarion> list, BmobException e) {
                if (e == null) {
                    goods = list;
                    for (Goods_Xmarion g : goods) {
                        Log.e("获取热销商品", g.toString());
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
                if (goods.size() == 0) {
                    return;
                }
                if (getActivity() == null) {
                    return;
                }
                adapter = new StoreGoodsAdapter_Xmarion(goods, getActivity());
                hotList.setAdapter(adapter);
                hotList.setVisibility(View.VISIBLE);
                hotList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getActivity(), GoodsInformationActivity_Xmarion.class);
                        intent.putExtra("name", goods.get(position).getName());
                        intent.putExtra("introduction", goods.get(position).getIntroduction());
                        intent.putExtra("number", goods.get(position).getNumber());
                        intent.putExtra("price", goods.get(position).getPrice());
                        intent.putExtra("storeId", goods.get(position).getStoreId());
                        intent.putExtra("goodsId", goods.get(position).getObjectId());
                        intent.putExtra("style", 3);
                        //intent.putExtra("headImgUrl");
                        comments = goods.get(position).getComments();
                        flag = true;
                        intent.putExtra("headImgUrl", goods.get(position).getGoodsImgUrl());
                        startActivity(intent);
                    }
                });
            }
        });
    }
}
