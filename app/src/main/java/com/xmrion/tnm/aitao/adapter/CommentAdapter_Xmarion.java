package com.xmrion.tnm.aitao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xmrion.tnm.aitao.R;
import com.xmrion.tnm.aitao.javabean.Comment_Xmarion;

import java.util.List;

/**
 * Created by 郑艳达 on 2017/7/10.
 * 用于评论的Adapter
 */

public class CommentAdapter_Xmarion extends BaseAdapter {

    private List<Comment_Xmarion> comments;

    private LayoutInflater inflater;

    public CommentAdapter_Xmarion(List<Comment_Xmarion> comments, Context context) {
        this.comments = comments;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
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
            convertView = inflater.inflate(R.layout.item_comment, null);
            holder.nameText =
                    (TextView) convertView.findViewById(R.id.id_tv_item_comment_name);
            holder.dateText =
                    (TextView) convertView.findViewById(R.id.id_tv_item_comment_date);
            holder.contentText =
                    (TextView) convertView.findViewById(R.id.id_tv_item_comment_content);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.nameText.setText(comments.get(position).getReviewerName());
        holder.contentText.setText(comments.get(position).getContent());
        holder.dateText.setText(comments.get(position).getDate());
        return convertView;
    }

    class ViewHolder {
        TextView nameText;
        TextView dateText;
        TextView contentText;
    }
}
