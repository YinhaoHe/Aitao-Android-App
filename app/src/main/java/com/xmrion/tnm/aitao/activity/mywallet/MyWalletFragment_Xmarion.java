package com.xmrion.tnm.aitao.activity.mywallet;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mingle.widget.LoadingView;
import com.xmrion.tnm.aitao.bmob.Store_Xmarion;
import com.xmrion.tnm.aitao.R;
import com.xmrion.tnm.aitao.application.MyApplication_Xmarion;
import com.xmrion.tnm.aitao.bmob.Wallet_Xmarion;
import com.xmrion.tnm.aitao.model.LoadingView_Xmaion;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by 郑艳达 on 2017/7/9.
 * 我的钱包Fragment
 */

public class MyWalletFragment_Xmarion extends Fragment implements LoadingView_Xmaion {

    @OnClick(R.id.id_btn_mywallet_back)
    public void back() {
        getActivity().finish();
    }

    @BindView(R.id.id_load_mywallet)
    public LoadingView loadingView;

    @BindView(R.id.id_ll_mywallet)
    public LinearLayout layout;

    private boolean isStoreNull = false;

    @OnClick(R.id.id_btn_mywallet_topup)
    public void topUp() {
        // TODO: 2017/7/9  添加充值功能


    }

    @BindView(R.id.id_tv_mywallet_balancenum)
    public TextView balanceNum;

    @BindView(R.id.id_tv_mywallet_storeincome)
    public TextView storeIncome;

    private Handler handler = new Handler();

    private Wallet_Xmarion wallet;

    private MyApplication_Xmarion app;

    private Store_Xmarion store;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_mywallet, container, false);

        ButterKnife.bind(this, v);
        initialView();
        return v;
    }

    @Override
    public void initialView() {
        app = (MyApplication_Xmarion) getActivity().getApplication();

        loadingView.setVisibility(View.VISIBLE);
        layout.setVisibility(View.GONE);
        setData();
    }

    @Override
    public void setData() {
        Log.e("我的钱包id：", app.getUser().getWalletId());
        BmobQuery<Wallet_Xmarion> bq = new BmobQuery<>();
        bq.getObject(app.getUser().getWalletId(), new QueryListener<Wallet_Xmarion>() {
            @Override
            public void done(Wallet_Xmarion wallet_xmarion, BmobException e) {
                if (e == null) {
                    wallet = wallet_xmarion;
                    Log.e("我的钱包", "钱包信息下载成功");
                    String storeId = app.getUser().getStoreId();
                    if (storeId == null) {
                        isStoreNull = true;
                        reSetView();
                    } else {
                        Log.e("storeId", storeId);
                        BmobQuery<Store_Xmarion> bq2 = new BmobQuery<>();
                        bq2.getObject(app.getUser().getStoreId(), new QueryListener<Store_Xmarion>() {
                            @Override
                            public void done(Store_Xmarion store_xmarion, BmobException e) {
                                if (e == null) {
                                    Log.e("我的钱包", "店铺信息下载成功");
                                    store = store_xmarion;
                                    reSetView();
                                }
                            }
                        });
                    }
                } else {
                    Log.e("我的钱包", e.toString());
                }
            }
        });
    }

    @Override
    public void reSetView() {
        handler.post(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                loadingView.setVisibility(View.GONE);
                layout.setVisibility(View.VISIBLE);
                //设置余额
                balanceNum.setText(wallet.getMoney().toString());
                //设置店铺收入
                if (isStoreNull) {
                    String zero = (double) 0 + "";
                    storeIncome.setText(zero);
                } else {
                    storeIncome.setText(store.getIncome().toString());
                }
            }
        });

    }
}
