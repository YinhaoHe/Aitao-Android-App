package com.xmrion.tnm.aitao.fragment.index;

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

import com.mingle.widget.LoadingView;
import com.xmrion.tnm.aitao.activity.editgoods.EditGoodsActivity_Xmarion;
import com.xmrion.tnm.aitao.activity.storeinfor.StoreInformationFragment_Xmaion;
import com.xmrion.tnm.aitao.adapter.StoreGoodsAdapter_Xmarion;
import com.xmrion.tnm.aitao.bmob.Goods_Xmarion;
import com.xmrion.tnm.aitao.bmob.Store_Xmarion;
import com.xmrion.tnm.aitao.R;
import com.xmrion.tnm.aitao.activity.goodsinfor.GoodsInformationActivity_Xmarion;
import com.xmrion.tnm.aitao.activity.storeinfor.StoreInformationActivity_Xmaion;
import com.xmrion.tnm.aitao.application.MyApplication_Xmarion;
import com.xmrion.tnm.aitao.javabean.Comment_Xmarion;
import com.xmrion.tnm.aitao.model.LoadingView_Xmaion;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by 郑艳达 on 2017/7/7.
 * 主页MystoreFragment
 */

public class MystoreFragment_Xmarion extends Fragment
        implements LoadingView_Xmaion {

    public static List<Comment_Xmarion> comments;

    private MyApplication_Xmarion app;

    private Store_Xmarion store;

    private Handler handler = new Handler();

    private List<Goods_Xmarion> goodsData;

    private StoreGoodsAdapter_Xmarion adapter;

    @OnClick(R.id.id_btn_mystore_add)
    public void addGoods() {
        Intent i = new Intent(getActivity(), EditGoodsActivity_Xmarion.class);
        i.putExtra("type", 1);
        startActivity(i);
    }

    @OnClick(R.id.id_btn_mystore_edit)
    public void editStore() {
        Intent i = new Intent();
        i.setClass(getActivity(), StoreInformationActivity_Xmaion.class);
        i.putExtra("type", 3);
        i.putExtra("name", store.getName());
        i.putExtra("information", store.getInformation());
        i.putExtra("introduction", store.getIntroduction());
        if (store.getHeadImgURL() != null) {
            if (!store.getHeadImgURL().equals("")) {
                i.putExtra("headImgUrl", store.getHeadImgURL());
            }
        } else {
            i.putExtra("headImgUrl", "");
        }
        startActivity(i);
    }

    @BindView(R.id.id_rl_mystore_nogoods)
    public RelativeLayout nogoodsLayout;

    @BindView(R.id.id_btn_mystore_add)
    public FloatingActionButton addBtn;

    @BindView(R.id.id_btn_mystore_edit)
    public FloatingActionButton editBtn;

    @BindView(R.id.id_btn_mystore_refresh)
    public ImageView refreshBtn;

    @OnClick(R.id.id_btn_mystore_refresh)
    public void refresh() {
        initialView();
    }

    @BindView(R.id.id_rl_mystore_nostore)
    public RelativeLayout nostoreLayout;

    @BindView(R.id.id_load_mystore)
    public LoadingView loadingView;

    @BindView(R.id.id_lv_mystore)
    public ListView goodsList;

    @OnClick(R.id.id_btn_mystore_create)
    public void createNewStore() {
        Intent i = new Intent(getActivity(), StoreInformationActivity_Xmaion.class);
        i.putExtra("type", 1);
        startActivity(i);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mystore, container, false);
        ButterKnife.bind(this, v);

        app = (MyApplication_Xmarion) getActivity().getApplication();
        initialView();
        return v;
    }

    @Override
    public void initialView() {
//        Log.e("店铺id:", app.getUser().getStoreId());
        addBtn.setVisibility(View.GONE);
        editBtn.setVisibility(View.GONE);
        nogoodsLayout.setVisibility(View.GONE);
        if (app.getUser().getStoreId() == null) {
            loadingView.setVisibility(View.GONE);
            goodsList.setVisibility(View.GONE);
            nostoreLayout.setVisibility(View.VISIBLE);
            refreshBtn.setVisibility(View.GONE);
            return;
        }
        refreshBtn.setVisibility(View.GONE);
        nostoreLayout.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
        goodsList.setVisibility(View.GONE);

        setData();
    }

    @Override
    public void setData() {
        String storeId = app.getUser().getStoreId();
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
                    BmobQuery<Store_Xmarion> s = new BmobQuery<>();
                    s.getObject(app.getUser().getStoreId(), new QueryListener<Store_Xmarion>() {
                        @Override
                        public void done(Store_Xmarion store_xmarion, BmobException e) {
                            if (e == null) {
                                store = store_xmarion;
                                reSetView();
                            } else {
                                Log.e("查找店铺失败:", e.toString());
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void reSetView() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                addBtn.setVisibility(View.VISIBLE);
                editBtn.setVisibility(View.VISIBLE);
                loadingView.setVisibility(View.GONE);
                refreshBtn.setVisibility(View.VISIBLE);
                if (getActivity() == null) {
                    return;
                }
                if (goodsData.size() == 0) {
                    goodsList.setVisibility(View.GONE);
                    nogoodsLayout.setVisibility(View.VISIBLE);
                    return;
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
                        //1卖家查看自己的店铺
                        intent.putExtra("style", 1);
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
        if (StoreInformationFragment_Xmaion.isCreateStore) {
            initialView();
            StoreInformationFragment_Xmaion.isCreateStore = false;
        }
        super.onStart();
    }
}
