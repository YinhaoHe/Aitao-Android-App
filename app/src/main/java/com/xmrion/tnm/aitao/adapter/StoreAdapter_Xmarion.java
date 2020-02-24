package com.xmrion.tnm.aitao.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xmrion.tnm.aitao.bmob.Store_Xmarion;
import com.xmrion.tnm.aitao.R;

import java.util.List;

/**
 * Created by 郑艳达 on 2017/7/9.
 * StoreAdapter
 */

public class StoreAdapter_Xmarion extends BaseAdapter {

    private List<Store_Xmarion> stores;

    private LayoutInflater inflater;

    private Context context;

    private static String DEFAULT_IMG_URL = "http://bmob-cdn-12770.b0.upaiyun.com/2017/07/13/cc4bac5b40593fef801b2d64ee3fd7e3.png";


    public StoreAdapter_Xmarion(List<Store_Xmarion> stores, Context context) {
        this.stores = stores;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return stores.size();
    }

    @Override
    public Object getItem(int position) {
        return stores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_store, null);

            holder.headImg =
                    (ImageView) convertView.findViewById(R.id.id_img_item_store);
            holder.nameText =
                    (TextView) convertView.findViewById(R.id.id_tv_item_store_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String url = stores.get(position).getHeadImgURL();
        if (url != null) {
            Log.e(stores.get(position).getName() + "获取店铺的头像url:", url);
            Glide.with(context).
                    load(url).
                    placeholder(R.drawable.img_defaultavatar).
                    crossFade().
                    into(holder.headImg);
        }else {
            Glide.with(context).
                    load(DEFAULT_IMG_URL).
                    placeholder(R.drawable.img_defaultavatar).
                    crossFade().
                    into(holder.headImg);
        }

        holder.nameText.setText(stores.get(position).getName());
        return convertView;
    }

    private class ViewHolder {
        ImageView headImg;
        TextView nameText;
    }
}
