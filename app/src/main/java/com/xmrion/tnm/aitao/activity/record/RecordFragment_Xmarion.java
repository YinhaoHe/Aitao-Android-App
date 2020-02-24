package com.xmrion.tnm.aitao.activity.record;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mingle.widget.LoadingView;
import com.xmrion.tnm.aitao.adapter.RecordAdapter_Xmarion;
import com.xmrion.tnm.aitao.application.MyApplication_Xmarion;
import com.xmrion.tnm.aitao.bmob.Wallet_Xmarion;
import com.xmrion.tnm.aitao.javabean.PurchaseRecord_Xmarion;
import com.xmrion.tnm.aitao.R;
import com.xmrion.tnm.aitao.model.LoadingView_Xmaion;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by 郑艳达 on 2017/7/12.
 * 记录界面
 */

public class RecordFragment_Xmarion extends Fragment
        implements LoadingView_Xmaion {

    @OnClick(R.id.id_btn_record_back)
    public void back() {
        getActivity().finish();
    }

    @BindView(R.id.id_tv_record_title)
    public TextView titleText;

    @BindView(R.id.id_load_record)
    public LoadingView loadingView;

    @BindView(R.id.id_lv_record)
    public ListView recordList;

    @BindView(R.id.id_rl_record_no)
    public RelativeLayout noLayout;

    private Handler handler = new Handler();

    private List<PurchaseRecord_Xmarion> records;

    private RecordAdapter_Xmarion adapter;

    //1购买记录 2销售记录
    private int type;

    private MyApplication_Xmarion app;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_record, container, false);
        ButterKnife.bind(this, v);
        initialView();
        return v;
    }

    @Override
    public void initialView() {
        type = getActivity().getIntent().getIntExtra("type", 1);
        loadingView.setVisibility(View.VISIBLE);
        recordList.setVisibility(View.GONE);
        noLayout.setVisibility(View.GONE);
        app = (MyApplication_Xmarion) getActivity().getApplication();
        if (type == 1) {
            titleText.setText("购买记录");
        } else if (type == 2) {
            titleText.setText("销售记录");
        }
        setData();
    }

    @Override
    public void setData() {
        BmobQuery<Wallet_Xmarion> bq = new BmobQuery<>();
        bq.getObject(app.getUser().getWalletId(), new QueryListener<Wallet_Xmarion>() {
            @Override
            public void done(Wallet_Xmarion wallet_xmarion, BmobException e) {
                if (e == null) {
                    if (type == 1) {
                        records = wallet_xmarion.getPurchaseRecords();
                    } else {
                        records = wallet_xmarion.getSaleRecords();
                    }
                    reSetView();
                } else {
                    Log.e("记录加载失败？", e.toString());
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
                if (records == null) {
                    noLayout.setVisibility(View.VISIBLE);
                    return;
                } else if (records.size() == 0) {
                    noLayout.setVisibility(View.VISIBLE);
                    return;
                }
                adapter = new RecordAdapter_Xmarion(records, getActivity());
                recordList.setAdapter(adapter);
                recordList.setVisibility(View.VISIBLE);
            }
        });
    }
}
