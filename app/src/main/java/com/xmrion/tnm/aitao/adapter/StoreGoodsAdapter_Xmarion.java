package com.xmrion.tnm.aitao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xmrion.tnm.aitao.R;
import com.xmrion.tnm.aitao.bmob.Goods_Xmarion;

import java.util.List;

/**
 * Created by 郑艳达 on 2017/7/9.
 * StoreGoodsAdapter
 */

public class StoreGoodsAdapter_Xmarion extends BaseAdapter {

    private List<Goods_Xmarion> goodsData;

    private LayoutInflater inflater;

    private Context context;

    public StoreGoodsAdapter_Xmarion(List<Goods_Xmarion> goodsData, Context context) {
        this.goodsData = goodsData;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return goodsData.size();
    }

    @Override
    public Object getItem(int position) {
        return goodsData.get(position);
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
            convertView = inflater.inflate(R.layout.item_storegoods, null);
            holder.headImg =
                    (ImageView) convertView.findViewById(R.id.id_img_item_storegoods_head);
            holder.nameText =
                    (TextView) convertView.findViewById(R.id.id_tv_item_storegoods_name);
            holder.priceText =
                    (TextView) convertView.findViewById(R.id.id_tv_item_storegoods_price);
            holder.salenumText =
                    (TextView) convertView.findViewById(R.id.id_tv_item_storegoods_salenum);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String url = goodsData.get(position).getGoodsImgUrl();
        if (url != null) {
            Glide.with(context)
                    .load(url)
                    .placeholder(R.drawable.img_defaultavatar)
                    .crossFade()
                    .into(holder.headImg);
        }
        holder.nameText.setText(goodsData.get(position).getName());
        String price = "￥" + goodsData.get(position).getPrice();
        holder.priceText.setText(price);
        String salenum = "销量:" + goodsData.get(position).getSellNum();
        holder.salenumText.setText(salenum);
        return convertView;
    }

    private class ViewHolder {
        ImageView headImg;
        TextView nameText;
        TextView priceText;
        TextView salenumText;
    }
}
