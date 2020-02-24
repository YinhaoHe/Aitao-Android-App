package com.xmrion.tnm.aitao.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xmrion.tnm.aitao.R;
import com.xmrion.tnm.aitao.javabean.PurchaseRecord_Xmarion;

import java.util.List;

/**
 * Created by 郑艳达 on 2017/7/12.
 * RecordAdapter
 */

public class RecordAdapter_Xmarion extends BaseAdapter {

    private List<PurchaseRecord_Xmarion> records;

    private LayoutInflater inflater;

    private Context context;

    public RecordAdapter_Xmarion(List<PurchaseRecord_Xmarion> records, Context context) {
        this.records = records;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return records.size();
    }

    @Override
    public Object getItem(int position) {
        return records.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_record, null);
            holder.headImg =
                    (ImageView) convertView.findViewById(R.id.id_img_record_head);
            holder.nameText =
                    (TextView) convertView.findViewById(R.id.id_tv_record_name);
            holder.priceText =
                    (TextView) convertView.findViewById(R.id.id_tv_record_price);
            holder.addressText =
                    (TextView) convertView.findViewById(R.id.id_tv_record_address);
            holder.dateText =
                    (TextView) convertView.findViewById(R.id.id_tv_record_date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String url = records.get(position).getImgUrl();
        if (url != null) {
            Glide.with(context)
                    .load(url)
                    .placeholder(R.drawable.img_defaultavatar)
                    .crossFade()
                    .into(holder.headImg);
        }
        String name = records.get(position).getGoodsName() + "×1";
        holder.nameText.setText(name);
        String price = "总计" + records.get(position).getPrice() + "元";
        holder.dateText.setText(records.get(position).getDate());
        holder.priceText.setText(price);
        String address = "收货地址：" + records.get(position).getAddress();
        holder.addressText.setText(address);
        return convertView;
    }

    private class ViewHolder {
        ImageView headImg;
        TextView nameText;
        TextView priceText;
        TextView dateText;
        TextView addressText;
    }
}
