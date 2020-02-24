package com.xmrion.tnm.aitao.activity.order;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mingle.widget.LoadingView;
import com.xmrion.tnm.aitao.bmob.Goods_Xmarion;
import com.xmrion.tnm.aitao.bmob.Store_Xmarion;
import com.xmrion.tnm.aitao.R;
import com.xmrion.tnm.aitao.application.MyApplication_Xmarion;
import com.xmrion.tnm.aitao.bmob.User_Xmarion;
import com.xmrion.tnm.aitao.bmob.Wallet_Xmarion;
import com.xmrion.tnm.aitao.fragment.dialog.SuccessFragment;
import com.xmrion.tnm.aitao.javabean.PurchaseRecord_Xmarion;
import com.xmrion.tnm.aitao.model.Purchase_Xmairon;
import com.xmrion.tnm.aitao.utils.DateUtils_Xmarion;
import com.xmrion.tnm.aitao.utils.ToastUtils_Xmarion;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 郑艳达 on 2017/7/10.
 * 订单
 */

public class OrderFragment_Xmairon extends Fragment implements Purchase_Xmairon {

    private String name;

    private String storeId;

    private PurchaseRecord_Xmarion pr;

    private String goodsId;

    private int number;

    private String url;

    private MyApplication_Xmarion app;

    private Wallet_Xmarion wallet;

    private Handler handler = new Handler();

    private double price;

    private SuccessFragment sf;

    @BindView(R.id.id_img_order_img)
    public ImageView headImg;

    @BindView(R.id.id_load_order)
    public LoadingView loadingView;

    @BindView(R.id.id_et_order)
    public EditText et;

    @BindView(R.id.id_tv_order_price)
    public TextView priceText;

    @BindView(R.id.id_tv_order_name)
    public TextView nameText;

    @BindView(R.id.id_tv_order_total)
    public TextView totalText;

    @OnClick(R.id.id_btn_order_back)
    public void back() {
        getActivity().finish();
    }

    @OnClick(R.id.id_btn_order_confirm)
    public void confirm() {
        String address = et.getText().toString();
        if (address.equals("")) {
            ToastUtils_Xmarion.showToast("请输入收货地址！", getActivity());
            return;
        } else if (number == 0) {
            ToastUtils_Xmarion.showToast("库存不足，无法购买！", getActivity());
            return;
        }
        loadingView.setVisibility(View.VISIBLE);
        // TODO: 2017/7/10 确认订单
        app = (MyApplication_Xmarion) getActivity().getApplication();

        //查找钱包
        findWallet(app.getUser().getWalletId());
        // TODO: 2017/7/12 添加img url
        //初始化PurchaseRecord
        pr = new PurchaseRecord_Xmarion(name, goodsId, url,
                DateUtils_Xmarion.getDate_YYYYMMDD(), price,
                address, app.getUser().getUsername(), storeId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_order, container, false);

        ButterKnife.bind(this, v);

        initialView();
        return v;
    }

    private void initialView() {
        Intent i = getActivity().getIntent();
        storeId = i.getStringExtra("storeId");
        name = i.getStringExtra("name");
        number = i.getIntExtra("number", 0);
        price = i.getDoubleExtra("price", (double) 0);
        url = i.getStringExtra("headImgUrl");
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.img_defaultavatar)
                .crossFade()
                .into(headImg);

        goodsId = i.getStringExtra("goodsId");

        String totalString = "￥" + price;
        String priceString = totalString + "×1";
        nameText.setText(name);
        priceText.setText(priceString);
        totalText.setText(totalString);
    }

    @Override
    public void reduceUserMoney() {
        Wallet_Xmarion w = new Wallet_Xmarion();
        w.increment("money", price * -1);
        w.add("purchaseRecords", pr);
        w.update(wallet.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.e("钱少了？", "是的");
                }
            }
        });
    }

    @Override
    public void addStoreMoney() {
        //通过storeId 找到store 再获取user
        BmobQuery<Store_Xmarion> bq = new BmobQuery<>();
        bq.getObject(storeId, new QueryListener<Store_Xmarion>() {
            @Override
            public void done(Store_Xmarion store, BmobException e) {
                if (e == null) {
                    addSaleUserMoney(store);
                }
            }
        });
        //原子计数器
        Store_Xmarion s = new Store_Xmarion();
        s.increment("income", price);
        s.update(storeId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.e("店铺挣钱了?", "是的");
                } else {
                    Log.e("店铺挣钱了?", "想多了");
                }
            }
        });

        Goods_Xmarion goods = new Goods_Xmarion();
        goods.increment("sellNum");
        goods.increment("number", -1);
        goods.update(goodsId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.e("销量加1", "成功");
                }
            }
        });
    }

    @Override
    public void addSaleUserMoney(Store_Xmarion store) {
        BmobQuery<User_Xmarion> bq = new BmobQuery<>();
        bq.getObject(store.getUserId(), new QueryListener<User_Xmarion>() {
            @Override
            public void done(User_Xmarion user_xmarion, BmobException e) {
                if (e == null) {
                    Log.e("卖家钱包id:", user_xmarion.getWalletId());
                    Wallet_Xmarion w = new Wallet_Xmarion();
                    w.increment("money", price);
                    w.add("saleRecords", pr);
                    w.update(user_xmarion.getWalletId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Log.e("卖家钱包收钱:", "成功");
                            } else {
                                Log.e("卖家钱包收钱失败", e.toString());
                            }
                        }
                    });
                } else {
                    Log.e("查找用户失败", e.toString());
                }
            }
        });
    }

    private void findWallet(String id) {
        BmobQuery<Wallet_Xmarion> bq = new BmobQuery<>();
        bq.getObject(id, new QueryListener<Wallet_Xmarion>() {
            @Override
            public void done(Wallet_Xmarion wallet_xmarion, BmobException e) {
                if (e == null) {
                    wallet = wallet_xmarion;
                    reSetView();
                }
            }
        });
    }

    private void reSetView() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                loadingView.setVisibility(View.GONE);
                if (wallet.getMoney() < price) {
                    ToastUtils_Xmarion.showToast("购买失败！余额不足请充值后购买！", getActivity());
                    back();
                } else {
                    reduceUserMoney();
                    addStoreMoney();
                    sf = new SuccessFragment();
                    sf.setCancelable(false);
                    sf.show(getFragmentManager(), "success");
                }
            }
        });
    }
}
