package com.xmrion.tnm.aitao.activity.index;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

import com.xmrion.tnm.aitao.fragment.index.MineFragment_Xmarion;
import com.xmrion.tnm.aitao.R;
import com.xmrion.tnm.aitao.activity.root.AiTaoActivity_Xmarion;
import com.xmrion.tnm.aitao.fragment.index.MystoreFragment_Xmarion;
import com.xmrion.tnm.aitao.fragment.index.StoreFragment_Xmairon;
import com.xmrion.tnm.aitao.javabean.TabButtonWithFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_Xmarion extends AiTaoActivity_Xmarion implements View.OnClickListener {

    private List<TabButtonWithFragment> tabButtons = new ArrayList<>();

    private int currentLayoutId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialView();
    }

    /**
     * 完成三个Fragment的初始化
     */
    private void initialView() {
        TabButtonWithFragment store = new TabButtonWithFragment(R.id.id_tab_store, R.id.id_img_store,
                R.drawable.icon_store, R.drawable.icon_store_selected,
                this, new StoreFragment_Xmairon());

        store.getLayout().setOnClickListener(this);
        TabButtonWithFragment mystore = new TabButtonWithFragment(R.id.id_tab_mystore, R.id.id_img_mystore,
                R.drawable.icon_mystore, R.drawable.icon_mystore_selected,
                this, new MystoreFragment_Xmarion());
        mystore.getLayout().setOnClickListener(this);

        TabButtonWithFragment mine = new TabButtonWithFragment(R.id.id_tab_mine, R.id.id_img_mine,
                R.drawable.icon_mine, R.drawable.icon_mine_selected,
                this, new MineFragment_Xmarion());
        mine.getLayout().setOnClickListener(this);

        tabButtons.add(store);
        tabButtons.add(mystore);
        tabButtons.add(mine);

        //初始化界面在商店界面
        setFragment(store.getFragment());
        store.setOnClick();
        currentLayoutId = store.getLayoutId();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == currentLayoutId){
            return;
        }
        for (TabButtonWithFragment button : tabButtons) {
            if (button.getLayoutId() == v.getId()) {
                button.setOnClick();
                setFragment(button.getFragment());
                currentLayoutId = v.getId();
            } else {
                button.setNotOnClick();
            }
        }
    }


    /**
     *设置需要显示的fragment
     * @param fragment:传入要显示的fragment
     */
    public void setFragment(Fragment fragment){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.id_fl_main, fragment);
        ft.commit();
    }
}
