package com.xmrion.tnm.aitao.fragment.index;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xmrion.tnm.aitao.activity.record.RecordActivity_Xmarion;
import com.xmrion.tnm.aitao.activity.set.SetActivity_Xmairon;
import com.xmrion.tnm.aitao.bmob.User_Xmarion;
import com.xmrion.tnm.aitao.utils.FileUtils_Xmarion;
import com.xmrion.tnm.aitao.R;
import com.xmrion.tnm.aitao.activity.login.LoginActivity_Xmarion;
import com.xmrion.tnm.aitao.activity.mywallet.MyWalletActivity_Xmarion;
import com.xmrion.tnm.aitao.activity.setname.SetNameActivity_Xmarion;
import com.xmrion.tnm.aitao.application.MyApplication_Xmarion;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by 郑艳达 on 2017/7/7.
 * 主页MineFragment
 */

public class MineFragment_Xmarion extends Fragment {

    private MyApplication_Xmarion app;

    private Bitmap mBitmap;
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;


    private String url;

    @BindView(R.id.id_img_mine_headimg)
    public ImageView headimg;

    @BindView(R.id.id_tv_mine_username)
    public TextView username;

    @OnClick(R.id.id_rl_mine_head)
    public void onClickHead() {
        showChoosePicDialog();
    }

    @OnClick(R.id.id_rl_mine_name)
    public void onClickName() {
        startActivity(new Intent(getActivity(), SetNameActivity_Xmarion.class));
    }

    @OnClick(R.id.id_rl_mine_pr)
    public void onClickPurchaseRecord() {
        Intent i = new Intent(getActivity(), RecordActivity_Xmarion.class);
        i.putExtra("type", 1);
        startActivity(i);
    }

    @OnClick(R.id.id_rl_mine_sr)
    public void onClickSaleRecord() {
        Intent i = new Intent(getActivity(), RecordActivity_Xmarion.class);
        i.putExtra("type", 2);
        startActivity(i);
    }


    @OnClick(R.id.id_rl_mine_mw)
    public void onClickMyWallet() {
        startActivity(new Intent(getActivity(), MyWalletActivity_Xmarion.class));
    }

    @OnClick(R.id.id_rl_mine_set)
    public void onClickSet() {
        startActivity(new Intent(getActivity(), SetActivity_Xmairon.class));
    }

    @OnClick(R.id.id_btn_mine_exit)
    public void exit() {
        getActivity().finish();
        BmobUser.logOut();
        MyApplication_Xmarion app = (MyApplication_Xmarion) getActivity().getApplication();
        app.resetUserInfo();
        startActivity(new Intent(getActivity(), LoginActivity_Xmarion.class));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mine, container, false);

        ButterKnife.bind(this, v);
        app = (MyApplication_Xmarion) getActivity().getApplication();
        resetUserInfo();

        return v;
    }

    @Override
    public void onStart() {
        username.setText(app.getUser().getUsername());
        super.onStart();
    }

    private void resetUserInfo() {
        url = app.getUser().getHeadImgUrl();
        //设置头像
        if (url != null) {
            if (!url.equals("")) {
                Log.e("头像url", url);
                Glide.with(this).
                        load(url).
                        placeholder(R.drawable.img_defaultavatar).
                        dontAnimate().
                        into(headimg);
            }
        }
        //设置姓名
        Log.e("username:", app.getUser().getUsername());
        username.setText(app.getUser().getUsername());
    }

    protected void showChoosePicDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("添加图片");
        String[] items = {"选择本地照片", "拍照"};
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image/*");
                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                        break;
                    case TAKE_PICTURE: // 拍照
                        Intent openCameraIntent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        tempUri = Uri.fromFile(new File(Environment
                                .getExternalStorageDirectory(), "temp_image.jpg"));
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                        startActivityForResult(openCameraIntent, TAKE_PICTURE);
                        break;
                }
            }
        });
        builder.show();
    }

    @Override

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE:
                    cutImage(tempUri); // 对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    cutImage(data.getData()); // 对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        try {
                            setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    }

    /**
     * 裁剪图片方法实现
     */
    protected void cutImage(Uri uri) {
        if (uri == null) {
            return;
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        //com.android.camera.action.CROP这个action是用来裁剪图片用的
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    /**
     * 保存裁剪之后的图片数据
     */
    protected void setImageToView(Intent data) throws IOException {
        Bundle extras = data.getExtras();
        if (extras != null) {
            mBitmap = extras.getParcelable("data");
            headimg.setImageBitmap(mBitmap);//显示图片
            File f = FileUtils_Xmarion.saveFile(mBitmap, Environment.getExternalStorageDirectory().getPath()
                    , app.getUser().getUsername() + "_head.img");
            final BmobFile bf = new BmobFile(f);
            //上传图片到服务器
            bf.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        Log.e("头像上传", "成功");
                        url = bf.getFileUrl();
                        Log.e("url:::::", url);
                        app.getUser().setHeadImgUrl(url);
                        User_Xmarion u = new User_Xmarion();
                        u.setHeadImgUrl(url);
                        u.update(app.getUser().getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Log.e("图片上传成功", "是的");
                                } else {
                                    Log.e("图片上传？？？", e.toString());
                                }
                            }
                        });
                    }
                }
            });
        } else {
            Log.e("删除原来成功", "失败");
        }
    }


}
