package com.xmrion.tnm.aitao.javabean;

import android.app.Activity;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by 郑艳达 on 2017/7/7.
 * 用于主界面tabbar的图片及点击处理
 */

public class TabButtonWithFragment {

    private Fragment fragment;

    private RelativeLayout layout;

    private int layoutId;

    private ImageView image;

    private int imageId;

    private int normalImgId;

    private Activity activity;

    private int selectedImgId;

    public TabButtonWithFragment(int layoutId, int imageId,
                                 int normalImgId, int selectedImgId,
                                 Activity activity, Fragment fragment) {
        this.layoutId = layoutId;
        this.imageId = imageId;
        this.normalImgId = normalImgId;
        this.selectedImgId = selectedImgId;
        this.activity = activity;
        this.fragment = fragment;
        layout = (RelativeLayout) activity.findViewById(layoutId);
        image = (ImageView) activity.findViewById(imageId);
    }

    public void setOnClick() {
        image.setImageDrawable(ContextCompat.getDrawable(activity, selectedImgId));
    }

    public void setNotOnClick() {
        image.setImageDrawable(ContextCompat.getDrawable(activity, normalImgId));
    }

    public int getLayoutId() {
        return layoutId;
    }


    public void setLayout(RelativeLayout layout) {
        this.layout = layout;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setNormalImgId(int normalImgId) {
        this.normalImgId = normalImgId;
    }

    public void setSelectedImgId(int selectedImgId) {
        this.selectedImgId = selectedImgId;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public RelativeLayout getLayout() {
        return layout;
    }

    public ImageView getImage() {
        return image;
    }

    public int getImageId() {
        return imageId;
    }

    public int getNormalImgId() {
        return normalImgId;
    }

    public Activity getActivity() {
        return activity;
    }

    public int getSelectedImgId() {
        return selectedImgId;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
