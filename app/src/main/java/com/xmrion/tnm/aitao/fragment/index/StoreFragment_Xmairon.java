package com.xmrion.tnm.aitao.fragment.index;

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
import android.widget.ImageView;
import android.widget.ListView;

import com.mingle.widget.LoadingView;
import com.xmrion.tnm.aitao.activity.hot.HotActivity_Xmarion;
import com.xmrion.tnm.aitao.adapter.StoreAdapter_Xmarion;
import com.xmrion.tnm.aitao.application.MyApplication_Xmarion;
import com.xmrion.tnm.aitao.bmob.Store_Xmarion;
import com.xmrion.tnm.aitao.model.LoadingView_Xmaion;
import com.xmrion.tnm.aitao.R;
import com.xmrion.tnm.aitao.activity.storegoods.StoreGoodsActivity_Xmarion;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 郑艳达 on 2017/7/7.
 * 主页StoreFragment
 */

public class StoreFragment_Xmairon extends Fragment implements LoadingView_Xmaion {

    @BindView(R.id.id_load_store)
    public LoadingView loadingView;

    @BindView(R.id.id_lv_store)
    public ListView storeList;

    @BindView(R.id.id_btn_store_refresh)
    public ImageView refreshBtn;

    @OnClick(R.id.id_btn_store_hot)
    public void hot() {
        Intent i = new Intent(getActivity(), HotActivity_Xmarion.class);
        startActivity(i);
    }

    @OnClick(R.id.id_btn_store_refresh)
    public void refresh() {
        initialView();
    }

    private List<Store_Xmarion> stores;

    private StoreAdapter_Xmarion adapter;

    private MyApplication_Xmarion app;

    private Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_store, container, false);
        ButterKnife.bind(this, v);

        initialView();
        return v;
    }

    @Override
    public void initialView() {
        loadingView.setVisibility(View.VISIBLE);
        storeList.setVisibility(View.GONE);
        refreshBtn.setVisibility(View.GONE);
        setData();
    }

    @Override
    public void setData() {

        BmobQuery<Store_Xmarion> bq = new BmobQuery<>();
        //设置限制50条
        bq.setLimit(50);
        app = (MyApplication_Xmarion) getActivity().getApplication();
        bq.addWhereNotEqualTo("userId", app.getUser().getObjectId());
        bq.findObjects(new FindListener<Store_Xmarion>() {
            @Override
            public void done(List<Store_Xmarion> list, BmobException e) {
                if (e == null) {
                    stores = list;
                    for (Store_Xmarion s : list) {
                        Log.e("店铺信息加载:", s.toString());
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
                refreshBtn.setVisibility(View.VISIBLE);
                if (getActivity() == null) {
                    return;
                }
                adapter = new StoreAdapter_Xmarion(stores, getActivity());
                storeList.setAdapter(adapter);
                storeList.setVisibility(View.VISIBLE);
                storeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getActivity(), StoreGoodsActivity_Xmarion.class);
                        intent.putExtra("name", stores.get(position).getName());
                        intent.putExtra("storeId", stores.get(position).getObjectId());
                        intent.putExtra("information", stores.get(position).getInformation());
                        intent.putExtra("introduction", stores.get(position).getIntroduction());
                        intent.putExtra("headImgUrl", stores.get(position).getHeadImgURL());
                        intent.putExtra("img", stores.get(position).getHeadImgURL());
                        startActivity(intent);
                    }
                });
            }
        });
    }
}
